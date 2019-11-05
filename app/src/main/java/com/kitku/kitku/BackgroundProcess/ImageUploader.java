package com.kitku.kitku.BackgroundProcess;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public abstract class ImageUploader extends AsyncTask<String, Void, String> {
    // source https://stackoverflow.com/questions/25398200/uploading-file-in-php-server-from-android-device
    @Override
    protected String doInBackground(String...strings) {
        HttpsURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(strings[1]);
        try {
            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(
                    sourceFile);
            URL url = new URL(strings[0]);

            // Open a HTTP connection to the URL
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE",
                    "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("image", sourceFile.getCanonicalPath());

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""
                    + sourceFile.getCanonicalPath() + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math
                        .min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0,
                        bufferSize);
            }

            // send multipart form data necesssary after file
            // data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);
            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

            // Responses from the server (code and message)
            int serverResponseCode = conn.getResponseCode();
            StringBuilder stringBuilder = new StringBuilder();
            //String serverResponseMessage = conn.getResponseMessage();
            // Parse the response
            if (serverResponseCode != HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                BufferedReader bufferedReader;
                try {
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                } catch (Exception e) {return e.getMessage();}

                // Convert response to String
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                } catch (Exception e) { return e.getMessage(); }
                //Log.d("test",stringBuilder.toString());

                // Return the response
                return stringBuilder.toString();
            } else {
                try {
                    return conn.getResponseMessage();
                } catch (Exception e) { return e.getMessage(); }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return "...";
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