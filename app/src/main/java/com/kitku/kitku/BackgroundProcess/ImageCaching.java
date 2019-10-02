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

    // parameter
    // 1. fullPath -> ImageName to save
    // 2. theBitmap -> the saved bitmap
    // 3. context -> to access getExternalDirectory()
    // 4. tag -> to choose whether to save product or userPic ("User", "Product", or "Location")
    public void putImageWithFullPath(String fullPath, Bitmap theBitmap, Context c, String tag) {
        if (!(fullPath == null || theBitmap == null)) {
            saveBitmap(fullPath, theBitmap, c, tag);
        }
    }

    private void saveBitmap(String fullPath, Bitmap bitmap, Context c, String tag) {
        if (fullPath == null || bitmap == null)
            return;

        //boolean fileCreated = false;
        //boolean bitmapCompressed;
        //boolean streamClosed = false;

        // Get the directory string
        File folder;
        if (tag.equals("User"))
            folder = c.getExternalFilesDir("UserPic");
        else if (tag.equals("Product"))
            folder = c.getExternalFilesDir("Images");
        else
            folder = c.getExternalFilesDir("Location");
        // if folder not exist, create one
        assert folder != null;
        if (!folder.exists()) folder.mkdir();
        fullPath = folder.getAbsolutePath().concat("/").concat(fullPath);

        // if image exist, delete it first
        File imageFile = new File(fullPath);
        if (imageFile.exists())
            if (!imageFile.delete())
                return;

        try {
            //fileCreated = imageFile.createNewFile();
            // create the cache file
            imageFile.createNewFile();
        } catch (IOException e) { /*e.printStackTrace();*/ }

        Log.d("location",imageFile.getAbsolutePath());

        // get bitmap to FileOutputStream, then convert image as PNG (even though
        // the file would have no extension later)
        FileOutputStream out = null;
        try {
            // from FileOutputStream, write the bitmap to file
            out = new FileOutputStream(imageFile);
            //bitmapCompressed = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            // compress and convert file as PNG type
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            //e.printStackTrace();
            //bitmapCompressed = false;
        } finally {
            if (out != null) {
                // dump all the resource used for FileOutputStream
                try {
                    out.flush();
                    out.close();
                    //streamClosed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    //streamClosed = false;
                }
            }
        }
    }

    // to get Image if exist as cache
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

    // to check if cache is exist
    public boolean isExist(String path) {
        File imageFile = new File(path);
        //Log.d("path", path);
        return imageFile.exists();
    }
}
