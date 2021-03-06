package com.kitku.kitku.BackgroundProcess;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public abstract class AsyncServerAccess extends AsyncTask<String, Void, String> {

    // Usage :  url[0] is the hyperlink to download data
    //          url[1] is to determine whether we use GET or POST (define url[1] null if using GET)
    @Override
    protected String doInBackground(String...strings) {

        URL url;
        try {
            // Get URL from object 1st index
            url = new URL(strings[0]);
        } catch (Exception e) {
            return e.getMessage();
        }

        // Preparation (look back at PHP requirement for the RequestProperty)
        HttpsURLConnection con;
        try {
            con = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            return e.getMessage();
        }
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        // if no data are going to be sent set OutputStreamWriter and send JSON along it
        // else just get data for response from host
        try {
            // JSON are from objects 2nd index
            if (strings[1] != null) {
                // Use POST to send data and insert/update data to host
                con.setRequestMethod("POST");
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(strings[1]);
                wr.flush();
            } else {
                // Use GET only to read data on host
                con.setRequestMethod("GET");
                con.getOutputStream();
            }
        } catch (Exception e) {
            return e.getMessage();
        }


        // Get and read the response
        StringBuilder stringBuilder = new StringBuilder();
        int HttpResult;
        try {
            HttpResult = con.getResponseCode();
        } catch (Exception e) {
            return e.getMessage();
        }

        // Parse the response
        if (HttpResult != HttpsURLConnection.HTTP_INTERNAL_ERROR) {
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            } catch (Exception e) {return e.getMessage();}

            // Convert response to String
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                return e.getMessage();
            }

            // Return the response
            return stringBuilder.toString();
        } else {
            try {
                return con.getResponseMessage();
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    protected AsyncResponse delegate;

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
        //Log.d("result", result);
    }
}
