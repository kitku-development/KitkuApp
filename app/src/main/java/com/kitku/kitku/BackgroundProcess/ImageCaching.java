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

    // parameter
    // 1. fullPath -> ImageName to save
    // 2. theBitmap -> the saved bitmap
    // 3. context -> to access getExternalDirectory()
    // 4. tag -> to choose whether to save product or userPic ("User" or else)
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
        else
            folder = c.getExternalFilesDir("Images");
        // if folder not exist, create one
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

        //Log.d("location",imageFile.getAbsolutePath());

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


    /*public String getSavedImagePath() {
        return lastImagePath;
    }*/
}
