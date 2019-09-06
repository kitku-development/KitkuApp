package com.kitku.kitku.BackgroundProcess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCaching {
    // code reference : https://github.com/kcochibili/TinyDB--Android-Shared-Preferences-Turbo/blob/master/TinyDB.java

    //private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    //private String lastImagePath = "";

    /*public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null)
            return null;

        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFullPath(theImageName);

        if (!mFullPath.equals("")) {
            lastImagePath = mFullPath;
            saveBitmap(mFullPath, theBitmap);
        }
        return mFullPath;
    }

    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGEDATA_DIRECTORY);

        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.e("ERROR", "Failed to setup folder");
                return "";
            }
        }
        return mFolder.getPath() + '/' + imageName;
    }

    private static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }*/

    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap, Context c) {
        return !(fullPath == null || theBitmap == null) && saveBitmap(fullPath, theBitmap, c);
    }

    private boolean saveBitmap(String fullPath, Bitmap bitmap, Context c) {
        if (fullPath == null || bitmap == null)
            return false;

        boolean fileCreated = false;
        boolean bitmapCompressed;
        boolean streamClosed = false;

        File folder = c.getExternalFilesDir("Images");// + "newfoldername";
        if (!folder.exists()) folder.mkdir();
        fullPath = folder.getAbsolutePath().concat("/").concat(fullPath);

        File imageFile = new File(fullPath);

        /*if (!imageFile.getParentFile().exists())
            imageFile.getParentFile().mkdirs();*/

        if (imageFile.exists())
            if (!imageFile.delete())
                return false;

        try {
            fileCreated = imageFile.createNewFile();
        } catch (IOException e) { e.printStackTrace(); }

        Log.d("location",imageFile.getAbsolutePath());

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmapCompressed = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
            bitmapCompressed = false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    streamClosed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    streamClosed = false;
                }
            }
        }

        return (fileCreated && bitmapCompressed && streamClosed);
    }

    public Bitmap getImage(String path) {
        //path = c.getExternalFilesDir("Images").getAbsolutePath() + path;
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return bitmapFromPath;
    }

    public boolean isExist(String path) {
        File imageFile = new File(path);
        return imageFile.exists();
    }


    /*public String getSavedImagePath() {
        return lastImagePath;
    }*/
}
