package com.example.newfindwheather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetDataFromInternet extends AsyncTask <URL, Void, String> {

    protected String getResponceFromHttpGetUrl (URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            String result;

            if (hasInput) {
                result = scanner.next();
                return result;
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }


    }

    public interface AsyncResponce {
        void processFinish(String output);

    }
    public AsyncResponce delegate;

    public GetDataFromInternet (AsyncResponce delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL[] url) {

        String result = null;
        URL urlQuery = url[0];
        try {
            result = getResponceFromHttpGetUrl(urlQuery);
        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(o);
        delegate.processFinish(result);
        Log.d("he", result);
    }
}