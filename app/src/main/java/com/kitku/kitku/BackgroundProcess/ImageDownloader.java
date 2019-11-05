package com.kitku.kitku.BackgroundProcess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;

public abstract class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    // Usage :  url[0] is string of image location in local directory
    //          url[1] is the name of the file
    //          url[2] is the hyperlink to get image from server
    //          url[3] is the index number used in ListItemActivity only, otherwise fill it with null
    private int indexNum;
    @Override
    protected Bitmap doInBackground(String...url) {
        Bitmap mIcon11 = null;
        if (url[3] != null)
            indexNum = Integer.valueOf(url[3]);
        try {
            // replace this with raw string of location
            //File folder = Objects.requireNonNull(
            //        mParentActivity.get().getActivity()).getExternalFilesDir("Banner");
            //assert folder != null;
            //String dirLocation = folder.getAbsolutePath().concat("/");
            if (ImageCaching.isExist(url[0].concat(url[1])))
                mIcon11 = ImageCaching.getImageDirectly(url[0].concat(url[1]));
            else {
                InputStream in = new java.net.URL(url[2]).openStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                mIcon11 = BitmapFactory.decodeStream(in, null, options);
                ImageCaching.saveImageIn(url[0].concat(url[1]), mIcon11);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        //void processFinish(Bitmap output);
        void processFinish(Bitmap output, Integer index);
    }

    protected AsyncResponse delegate;

    @Override
    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result, indexNum);
    }
}
