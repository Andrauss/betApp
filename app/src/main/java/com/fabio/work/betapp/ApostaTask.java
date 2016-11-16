package com.fabio.work.betapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Fabio on 15/11/2016.
 */

public class ApostaTask extends AsyncTaskLoader {

    private List<Jogo> jogos;
    private int apostaId;

    public ApostaTask(Context context, int apostaId){
        super(context);
        this.apostaId = apostaId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (this.apostaId == 0) return;

        if (this.jogos == null) {
            forceLoad();
        } else {
            deliverResult(jogos);
        }
    }

    @Override
    public List<Jogo> loadInBackground() {
        try {
            jogos = ApostaParser.getApostaById( this.apostaId );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jogos;
    }
}
