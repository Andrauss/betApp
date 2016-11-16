package com.fabio.work.betapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fabio on 13/11/2016.
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

        } catch (IOException e) { }
        mmSocket = tmp;

    }

    public void run(List<Jogo> jogos) {

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();

                throw connectException;
            } catch (IOException closeException) { }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
        OutputStream tmpOut = null;
        try {

            tmpOut = mmSocket.getOutputStream();
            String teste = "\n\n";
            tmpOut.write(teste.getBytes());

            Jogo jogo = jogos.get(0);

            teste = "                      " + jogo.FILIAL_DESC;
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "Autenticação: "+jogo.APOSTA_AUTENTICACAO+"\n";
            tmpOut.write(teste.getBytes());

            teste = "Apostador: "+ jogo.APOSTA_NOME_APOSTADOR;
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "Quantidade de Jogos: "+Integer.toString(jogos.size())+"\n";
            tmpOut.write(teste.getBytes());

            teste = "Valor apostado: "+jogo.APOSTA_VALOR+"\n";
            tmpOut.write(teste.getBytes());

            teste = "Retorno possivel: "+jogo.APOSTA_RETORNO_POSSIVEL+"\n";
            tmpOut.write(teste.getBytes());

            for(int i = 0; i < jogos.size(); i++){
                Jogo item = jogos.get(i);
                teste = "\n\n";
                tmpOut.write(teste.getBytes());

                teste = item.CASA + " X " + item.FORA + "\n";
                tmpOut.write(teste.getBytes());

                teste = item.DATA_HORA_FIM + "    " + item.TIPO_APOSTA + ":" + item.APOSTA_JOGO_TAXA;
                tmpOut.write(teste.getBytes());

            }
            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = jogo.APOSTA_DATA_HORA;
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "* O prazo para pagamento é de sete dias";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "* O prêmio máximo por bilhere é 15.000,00";
            tmpOut.write(teste.getBytes());

            teste = " (Quinze mil reais)";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "Regra 1 - Não seram pagos jogos já iniciados e por falha\n ";
            tmpOut.write(teste.getBytes());
            teste = "          continuem no sistema, sejá por erro de hora ou \n ";
            tmpOut.write(teste.getBytes());
            teste = "          por jogo antecipado.";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "Regra 2 - Não serão pagos jogos definidos após 90 minutos\n";
            tmpOut.write(teste.getBytes());
            teste = "          + acrescimos. Seja por prorrogação ou disputa de\n";
            tmpOut.write(teste.getBytes());
            teste = "          penaltis.";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "Regra 3 - Em caso de aposta com mais de um jogo(Casadinha)\n";
            tmpOut.write(teste.getBytes());
            teste = "          jogos que vialerem as regras 1 e 2 não serão pagos.";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

            teste = "\n\n";
            tmpOut.write(teste.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}