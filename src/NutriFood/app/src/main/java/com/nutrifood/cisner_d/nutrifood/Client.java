package com.nutrifood.cisner_d.nutrifood;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.params.ClientPNames;

public class Client {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Context context = null;

    public static void setContext(Context context)
    {
        Client.context = context;
    }

    public static void addHeader(String header)
    {
        Log.d(context.getString(R.string.authorization_key), header);
        client.removeAllHeaders();
        client.addHeader(context.getString(R.string.authorization_key), header);
    }

    public static void get(String relativeURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(relativeURL), params, responseHandler);
//        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    public static void post(String relativeURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(relativeURL), params, responseHandler);
//        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d("URL=", context.getString(R.string.Base_URL) + relativeUrl);
        return context.getString(R.string.Base_URL) + relativeUrl;
    }
}
