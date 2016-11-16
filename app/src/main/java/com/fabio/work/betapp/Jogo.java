package com.fabio.work.betapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabio on 15/11/2016.
 */
public class Jogo implements Parcelable{

    int APOSTA_ID;
    String APOSTA_AUTENTICACAO;
    String APOSTA_DATA_HORA;
    String APOSTA_VALOR;
    String APOSTA_RETORNO_POSSIVEL;
    String APOSTA_NOME_APOSTADOR;
    String TIPO_APOSTA;
    String APOSTA_JOGO_TAXA;
    String DATA_HORA_FIM;
    String CASA;
    String FORA;
    String FILIAL_DESC;

    public Jogo(Parcel in){
        super();
        this.APOSTA_ID = in.readInt();
        this.APOSTA_AUTENTICACAO = in.readString();
        this.APOSTA_DATA_HORA = in.readString();;
        this.APOSTA_VALOR = in.readString();;
        this.APOSTA_RETORNO_POSSIVEL = in.readString();;
        this.APOSTA_NOME_APOSTADOR = in.readString();;
        this.TIPO_APOSTA = in.readString();;
        this.APOSTA_JOGO_TAXA = in.readString();;
        this.DATA_HORA_FIM = in.readString();;
        this.CASA = in.readString();;
        this.FORA = in.readString();;
        ;
    }

    public static final Creator<Jogo> CREATOR = new Creator<Jogo>() {
        @Override
        public Jogo createFromParcel(Parcel in) {
            return new Jogo(in);
        }

        @Override
        public Jogo[] newArray(int size) {
            return new Jogo[size];
        }
    };



    @Override
    public String toString(){
        return  "JOGO[APOSTA_ID=" + Integer.toString(APOSTA_ID) +
                ",APOSTA_AUTENTICACAO=" + APOSTA_AUTENTICACAO +
                ",APOSTA_DATA_HORA=" + APOSTA_DATA_HORA +
                ",APOSTA_VALOR=" + APOSTA_VALOR +
                ",APOSTA_RETORNO_POSSIVEL=" + APOSTA_RETORNO_POSSIVEL +
                ",APOSTA_NOME_APOSTADOR=" + APOSTA_NOME_APOSTADOR +
                ",TIPO_APOSTA=" + TIPO_APOSTA +
                ",APOSTA_JOGO_TAXA="+ APOSTA_JOGO_TAXA+
                ",DATA_HORA_FIM="+ DATA_HORA_FIM +
                ",CASA="+ CASA +
                ",FORA="+ FORA +
                ",FILIAL_DESC="+ FILIAL_DESC + "]";

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(APOSTA_ID);
        dest.writeString(APOSTA_AUTENTICACAO);
        dest.writeString(APOSTA_DATA_HORA);
        dest.writeString(APOSTA_VALOR);
        dest.writeString(APOSTA_RETORNO_POSSIVEL);
        dest.writeString(APOSTA_NOME_APOSTADOR);
        dest.writeString(TIPO_APOSTA);
        dest.writeString(APOSTA_JOGO_TAXA);
        dest.writeString(DATA_HORA_FIM);
        dest.writeString(CASA);
        dest.writeString(FORA);
        dest.writeString(FILIAL_DESC);
    }

}
