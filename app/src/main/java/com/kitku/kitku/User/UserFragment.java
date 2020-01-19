package com.kitku.kitku.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kitku.kitku.MainActivity;
import com.kitku.kitku.R;
import com.kitku.kitku.BackgroundProcess.*;
//import com.kitku.kitku.Login.LoginFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends Fragment {

    private RecyclerView recyclerViewMenu;
    private ArrayList<UserMenuListCardViewDataModel> menuListArrayList = new ArrayList<>();
    private UserMenuListCardViewAdapter userMenuListAdapter;
    private View menuListView;
    private ImageView profilePic;
    private static SharedPreferences userData;
    //private static FragmentManager fragmentManager;
    //private static int FragmentLayout;
    private static int RESULT_LOAD_IMAGE = 1;
    private ProgressBar progressBar;
    private String dirString;
    //private ImageView profilePic;

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        menuListView = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerViewMenu = menuListView.findViewById(R.id.recyclerviewUserMenu);
        recyclerViewMenu.setHasFixedSize(true);

        usernameText        = menuListView.findViewById(R.id.textviewUser_Username);
        loadingIndicator    = menuListView.findViewById(R.id.loadingIndicator);
        progressBar         = menuListView.findViewById(R.id.progressBar);
        profilePic          = menuListView.findViewById(R.id.circleimageviewUser_UserPhoto);
        loadingIndicator.setVisibility(View.INVISIBLE);

        // get userdata from app preferencemanager
        userData = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(getContext()).getApplicationContext());
        //fragmentManager = this.getFragmentManager();
        //FragmentLayout = R.id.frameFragmentContainerLoginGoToUserDetailFragment;

        // load data and image from server (if image cache exist, load it from memory instead)
        runAsync();
        sendData.execute(BackendPreProcessing.URL_UserData +
                userData.getString("ID_User", null), null);
        loadingIndicator.setVisibility(View.VISIBLE);
        profilePic.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        dirString = Objects.requireNonNull(
                this.getContext().getExternalFilesDir("UserPic")).getAbsolutePath().concat("/");
        loadImage();
        downloadImage.execute(
            dirString,
            userData.getString("ID_User", null),
            BackendPreProcessing.URL_UserPicLoc.concat(
                    Objects.requireNonNull(userData.getString("ID_User", null))),
            null
        );
        // show menu for user
        addMenuData();
        showMenuData();

        // set onclick of User Image to upload image and replace the existing one
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        menuListView.findViewById(R.id.buttonToolbarOrderTrackBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.default_home_button();
            }
        });

        return menuListView;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showMenuData() {
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        userMenuListAdapter = new UserMenuListCardViewAdapter(menuListArrayList);
        recyclerViewMenu.setAdapter(userMenuListAdapter);
    }

    private void addMenuData() {
        menuListArrayList = new ArrayList<>();
        String tag = "User";
        menuListArrayList.add(new UserMenuListCardViewDataModel("Blog", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Syarat dan Ketentuan", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("FAQ", menuListView, tag));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Logout", menuListView, tag));
    }

    private TextView usernameText;
    private ProgressBar loadingIndicator;

    // Inisiasi AsyncTask dari AsyncServerAccess supaya dapat mengakses Activity
    public static class backgroundTask extends AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    String[] userData = new BackendPreProcessing().readUserData(output);
                    usernameText.setText(userData[0]);
                    loadingIndicator.setVisibility(View.GONE);
                    //new downloadImage(UserFragment.this).execute();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    // used to logout user and redirect to login fragment
    public static void loggingOut(View menuListView) {
        SharedPreferences.Editor userDataEdit = userData.edit();
        userDataEdit.remove("ID_User");
        userDataEdit.apply();
        menuListView.setVisibility(View.GONE);
        //menuListView.setId(View.generateViewId());
        /*Fragment LoginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(FragmentLayout, LoginFragment);
        fragmentTransaction.commit();*/
        MainActivity.default_home_button();
    }

    static class backgroundImageDownloader extends ImageDownloader {
        backgroundImageDownloader(AsyncResponse delegate) { this.delegate = delegate; }
    }
    private backgroundImageDownloader downloadImage;

    private void loadImage() {
        downloadImage = new backgroundImageDownloader(new backgroundImageDownloader.AsyncResponse() {
            @Override
            public void processFinish(Bitmap output, Integer index) {
                if (output != null) {
                    // set and show image
                    profilePic.setImageBitmap(output);
                }
                progressBar.setVisibility(View.GONE);
                profilePic.setVisibility(View.VISIBLE);
            }
        });
    }

    // declare for uploading image
    public static class backgroundTask2 extends ImageUploader {
        backgroundTask2(AsyncResponse delegate) { this.delegate = delegate; }
    }

    // this procedure execute after user chose the image from gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};

            assert selectedImage != null;
            Cursor cursor = Objects.requireNonNull(
                    getContext()).getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // check for image chosen. if exceed 500kb, deny the procedure
            File file = new File(picturePath);
            long fileSize = file.length() / 1024;
            if (fileSize < 500) {
                // declare async for uploading image
                backgroundTask2 sendPicture = new backgroundTask2(new backgroundTask2.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        //loadingIndicator.setVisibility(View.GONE);
                        //progressDialog.dismiss();
                        if (!output.contains("Failed")) {
                            try {
                                Log.d("message", output);
                                //new downloadImage(UserFragment.this).execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // delete existing picture cache
                            File dir = new File(dirString.concat(
                                    Objects.requireNonNull(userData.getString("ID_User", null))));
                            boolean delete = dir.delete();

                            // download new image from server
                            loadImage();
                            downloadImage.execute(
                                    dirString,
                                    userData.getString("ID_User", null),
                                    BackendPreProcessing.URL_UserPicLoc
                                            .concat(
                                                    Objects.requireNonNull(
                                                            userData.getString(
                                                                    "ID_User", null))),
                                    null);
                        } else
                            // show warning
                            Toast.makeText(
                                    UserFragment.this.getContext(),
                                    "Upload gambar gagal. Periksa kembali koneksi anda.",
                                    Toast.LENGTH_LONG)
                                    .show();
                    }
                });
                profilePic.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                // upload image
                sendPicture.execute(BackendPreProcessing.URL_UserUploadImg +
                        userData.getString("ID_User", null), picturePath);
            }
            else {
                // error message if image exceed 500kb
                new AlertDialog.Builder(getContext())
                        .setTitle("Error")
                        .setMessage("Max file gambar adalah 500kb")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }
}