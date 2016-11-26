package com.fabio.work.betapp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fabio on 13/11/2016.
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    public enum LineType {
        ALIGN_CENTER, ALIGN_LEFT, ALIGN_RIGHT, UNDERLINE,
        DOTEDLINE, DASHEDLINE, BLANKLINE, ALIGN_JUSTIFY
    }

    private int colNum = 0;



    public ConnectThread(BluetoothDevice device, MainActivity.PRINTER_DEVICE pd) {
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

        switch (pd) {
            case PD_DPP_350:
                this.colNum = 48;
                break;
            case PD_DPP_250:
                this.colNum = 32;
                break;
        }


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

            Jogo jogo = jogos.get(0);
            if(this.colNum == 32){

                tmpOut.write(formataLinha("______________________________", LineType.ALIGN_CENTER));

                jogo = jogos.get(0);

                tmpOut.write(formataLinha("PREMIER BET", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("______________________________", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("Cambista: ", jogo.CAMBISTA_NOME,LineType.ALIGN_JUSTIFY));

                if(!jogo.CAMBISTA_FONE.trim().equals("")){
                    tmpOut.write(formataLinha("Cambista Fone: ", jogo.CAMBISTA_FONE,LineType.ALIGN_JUSTIFY));
                }
                tmpOut.write(formataLinha("Autenticação: ", jogo.APOSTA_AUTENTICACAO,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Apostador: ",  jogo.APOSTA_NOME_APOSTADOR,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Quantidade de Jogos: ", Integer.toString(jogos.size()) ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Valor apostado: ", jogo.APOSTA_VALOR ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Retorno possivel: ", jogo.APOSTA_RETORNO_POSSIVEL ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("______________________________", LineType.ALIGN_CENTER));
                for(int i = 0; i < jogos.size(); i++){
                    Jogo item = jogos.get(i);

                    tmpOut.write(formataLinha(item.CASA + " X " + item.FORA ,LineType.ALIGN_CENTER));

                    tmpOut.write(formataLinha(item.DATA_HORA_FIM.substring(0,16), item.TIPO_APOSTA + ": " + item.APOSTA_JOGO_TAXA ,LineType.ALIGN_JUSTIFY));

                    if((i+1 < jogos.size())){
                        tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                    }
                }

                tmpOut.write(formataLinha("______________________________", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha(jogo.APOSTA_DATA_HORA ,LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("Prêmios acima de 3.000,00 pagamento  em até 48h.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("Resultado válido dos 90minutos jogados, não será considerado prorrogação nem disputa por pênaltis.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("Limite de prêmios 30.000,00.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
                tmpOut.write(formataLinha("                              ", LineType.ALIGN_CENTER));
            }else { // DPP-350
                tmpOut.write(formataLinha("",LineType.BLANKLINE));

                jogo = jogos.get(0);

                tmpOut.write(formataLinha("PREMIER BET", LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("",LineType.BLANKLINE));
                tmpOut.write(formataLinha("",LineType.UNDERLINE));

                tmpOut.write(formataLinha("Cambista: ", jogo.CAMBISTA_NOME,LineType.ALIGN_JUSTIFY));

                if(!jogo.CAMBISTA_FONE.trim().equals("")){
                    tmpOut.write(formataLinha("Cambista Fone: ", jogo.CAMBISTA_FONE,LineType.ALIGN_JUSTIFY));
                }
                tmpOut.write(formataLinha("Autenticação: ", jogo.APOSTA_AUTENTICACAO,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Apostador: ",  jogo.APOSTA_NOME_APOSTADOR,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Quantidade de Jogos: ", Integer.toString(jogos.size()) ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Valor apostado: ", jogo.APOSTA_VALOR ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("Retorno possivel: ", jogo.APOSTA_RETORNO_POSSIVEL ,LineType.ALIGN_JUSTIFY));

                tmpOut.write(formataLinha("",LineType.UNDERLINE));
                for(int i = 0; i < jogos.size(); i++){
                    Jogo item = jogos.get(i);

                    tmpOut.write(formataLinha(item.CASA + " X " + item.FORA ,LineType.ALIGN_CENTER));

                    tmpOut.write(formataLinha(item.DATA_HORA_FIM.substring(0,16), item.TIPO_APOSTA + ": " + item.APOSTA_JOGO_TAXA ,LineType.ALIGN_JUSTIFY));

                    if((i+1 < jogos.size())){
                        tmpOut.write(formataLinha("",LineType.DASHEDLINE));
                    }
                }

                tmpOut.write(formataLinha("",LineType.UNDERLINE));
                tmpOut.write(formataLinha("",LineType.BLANKLINE));

                tmpOut.write(formataLinha(jogo.APOSTA_DATA_HORA ,LineType.ALIGN_CENTER));

                tmpOut.write(formataLinha("",LineType.BLANKLINE));
                tmpOut.write(formataLinha("",LineType.BLANKLINE));

                tmpOut.write(formataLinha("Prêmios acima de 3.000,00 pagamento  em até 48h.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("", LineType.BLANKLINE));

                tmpOut.write(formataLinha("Resultado válido dos 90minutos jogados, não será considerado prorrogação nem disputa por pênaltis.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("", LineType.BLANKLINE));

                tmpOut.write(formataLinha("Limite de prêmios 30.000,00.", LineType.ALIGN_LEFT));

                tmpOut.write(formataLinha("",LineType.BLANKLINE));
                tmpOut.write(formataLinha("",LineType.BLANKLINE));
                tmpOut.write(formataLinha("",LineType.BLANKLINE));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private byte[] formataLinha(String titulo, String valor, LineType lineType) {
        String linha = null;
        int tituloSize = titulo.length();
        int valorSize = valor.length();
        int size = 0;

        switch (lineType) {
            case ALIGN_CENTER:
                break;
            case ALIGN_LEFT:
                break;
            case ALIGN_RIGHT:
                break;
            case ALIGN_JUSTIFY:
                linha = titulo;
                for(size = this.colNum - (tituloSize + valorSize); size > 0; size--){
                    linha += " ";
                }
                linha += valor;
                break;
        }

        try {
            return linha.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    private byte[] formataLinha(String texto, LineType lineType) {
        String linha = "";
        int size;
        switch (lineType) {
            case ALIGN_CENTER:
                size = texto.trim().length();
                for (int i = (this.colNum - size) / 2; i > 0; i--) {
                    linha += " ";
                }
                linha += texto.trim();
                linha += "\n";
                break;
            case ALIGN_LEFT:
                linha = texto.trim();
                size = texto.trim().length();
                for (int i = this.colNum - size; i > 0; i--) {
                    linha += " ";
                }
                linha += "\n";
                break;
            case ALIGN_RIGHT:
                size = texto.trim().length();
                for (int i = this.colNum - size; i > 0; i--) {
                    linha += " ";
                }
                linha += texto.trim() + "\n";
                break;
            case UNDERLINE:
                linha = "________________________________________________\n";
                break;
            case DOTEDLINE:
                linha = "................................................\n";
                break;
            case DASHEDLINE:
                linha = "------------------------------------------------\n";
                break;
            case BLANKLINE:
                linha = "\n";
                break;
        }

        try {
            return linha.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

        /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}