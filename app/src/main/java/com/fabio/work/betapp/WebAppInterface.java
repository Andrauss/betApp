package com.fabio.work.betapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Work on 15/11/2016.
 */
public class WebAppInterface {
    Context mContext;

    public WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showToast(String toast){
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

    }
}
