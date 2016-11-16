package com.fabio.work.betapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Jogo>>{

    public static final String URL_BET = "http://bet24h.com.br/Bet24h/sistema/app_Login/";
    public static final String ARGS_ID = "id";
    private List<Jogo> jogos = null;
    private LoaderManager mLoaderManager;
    BluetoothDevice dppPrint = null;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebAppInterface webAppInterface = new WebAppInterface(this);

        WebView webview = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl(URL_BET);
        webview.addJavascriptInterface(webAppInterface, "Android");
        webview.addJavascriptInterface(this, "Android");

        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(0, null, this);

    }

    @JavascriptInterface
    public void getAposta(String apostaId) {
        Bundle args = new Bundle();
        args.putInt(ARGS_ID, Integer.parseInt(apostaId));
        mLoaderManager.restartLoader(0, args, this);
    }

    @Override
    public Loader<List<Jogo>> onCreateLoader(int id, Bundle args) {
        int newId = (args == null ? 0 : args.getInt(ARGS_ID));
        return new ApostaTask(this, newId);
    }

    @Override
    public void onLoadFinished(Loader<List<Jogo>> loader, List<Jogo> data) {
        jogos = data;
        conectarImpressora(jogos);
    }

    @Override
    public void onLoaderReset(Loader<List<Jogo>> loader) {
        jogos = null;
    }

    public void conectarImpressora(List<Jogo> jogos){

        if(dppPrint == null){

            // Ativar o bluetooth
            if(!bluetoothAdapter.isEnabled()){

                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 1;
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);

            }

            // Encontrar dispositivos

            // #1 listar os dispositivos pareados
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0){

                for(BluetoothDevice device : pairedDevices){

                    if(device.getName().equals("DPP-350")){

                        dppPrint = device;

                    }
                }
            }

        }

        // Cancel discovery because it will slow down the connection
        bluetoothAdapter.cancelDiscovery();

        ConnectThread connect = new ConnectThread(dppPrint);

        connect.run(jogos);

        connect.cancel();

    }
}
