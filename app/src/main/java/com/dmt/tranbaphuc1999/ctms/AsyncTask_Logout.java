package com.dmt.tranbaphuc1999.ctms;

import android.app.Activity;
import android.os.AsyncTask;

public class AsyncTask_Logout extends AsyncTask<Void,String,String> {

    Activity context;

    public AsyncTask_Logout(Activity ctx)
    {
        context=ctx;
    }

    @Override
    protected String doInBackground(Void... voids) {

        Connection_CTMS connectionCtms = new Connection_CTMS(context);

        return connectionCtms.Logout();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
