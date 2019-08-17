package com.kitku.kitku.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kitku.kitku.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private RecyclerView recyclerViewMenu;
    private ArrayList<UserMenuListCardViewDataModel> menuListArrayList = new ArrayList<>();
    private UserMenuListCardViewAdapter  userMenuListAdapter;

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View menuListView = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerViewMenu = menuListView.findViewById(R.id.recyclerviewUserMenu);
        recyclerViewMenu.setHasFixedSize(true);

        return menuListView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addMenuData();
        showMenuData();

    }

    private void showMenuData() {
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        userMenuListAdapter = new UserMenuListCardViewAdapter(menuListArrayList);
        recyclerViewMenu.setAdapter(userMenuListAdapter);

    }

    private void addMenuData() {
        menuListArrayList = new ArrayList<>();

        menuListArrayList.add(new UserMenuListCardViewDataModel("Blog"));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Syarat dan Ketentuan"));
        menuListArrayList.add(new UserMenuListCardViewDataModel("FAQ"));
        menuListArrayList.add(new UserMenuListCardViewDataModel("Logout"));
    }


}


    /*
    private TextView usernameText;
    private ProgressBar loadingIndicator;
    private ImageView userPicture;
    private RelativeLayout userMenuLayout;
    */

    /*
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        view.setId(View.generateViewId());

        SharedPreferences userData = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(getContext()).getApplicationContext());

        usernameText        = view.findViewById(R.id.usernameText);
        loadingIndicator    = view.findViewById(R.id.loadingIndicator);
        userPicture         = view.findViewById(R.id.userPicture);
        userMenuLayout      = view.findViewById(R.id.userMenuLayout);

        loadingIndicator.setVisibility(View.VISIBLE);
        addUserMenu(view, inflater, container);

        runAsync();
        sendData.execute(z_BackendPreProcessing.URL_UserData +
                userData.getString("ID_User", null), null);
    */

    /*
    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    private backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    private void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    String[] userData = new z_BackendPreProcessing().readUserData(output);
                    usernameText.setText(userData[0]);
                    loadingIndicator.setVisibility(View.GONE);
                    new downloadImage(UserFragment.this).execute();
                } catch (Exception e) { e.printStackTrace(); }
            }
        });
    }

    static class downloadImage extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<UserFragment> mParentActivity;
        //int indexnum;

        downloadImage(UserFragment parentActivity) {
            mParentActivity = new WeakReference<>(parentActivity);
        }

        @Override
        protected Bitmap doInBackground(String...url) {
            //indexnum = Integer.parseInt(url[1]);
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL("https://kitku.id/productpic/SAYUR.jpeg")
                        .openStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                mIcon11 = BitmapFactory.decodeStream(in,null,options);
            } catch (Exception e) { e.printStackTrace(); }

            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            final UserFragment parentActivity = mParentActivity.get();
            parentActivity.userPicture.setImageBitmap(result);
        }
    }

    // Menu user disetting disini
    private void addUserMenu(View v, LayoutInflater inflater, ViewGroup container) {
        String[] menuList   = new String[] {"Blog", "Syarat dan Ketentuan", "FAQ", "Logout"};
        String[] pesanMenu  = new String[] {
                "Ini menu blog",
                "Ini menu S&K",
                "Ini menu FAQ",
                "Apakah anda ingin logout?"
        };
        int nextID = 0;
        for (int index = 0; index < menuList.length; index++) {
            UserMenuBuilder build = new UserMenuBuilder(
                    userMenuLayout,
                    getResources(),
                    menuList[index],
                    pesanMenu[index],
                    nextID,
                    v,
                    this,
                    getChildFragmentManager(),
                    "User");//,
            //new MainActivity());
            nextID = build.getNextID();
            //build.setAdditionalData(inflater, container);
            //build.addMenu(userMenuLayout, getResources(), s);
        }
    }
    */