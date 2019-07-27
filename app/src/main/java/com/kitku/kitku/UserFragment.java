package com.kitku.kitku;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class UserFragment extends Fragment {
    SharedPreferences userData;
    TextView tvTest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userData = PreferenceManager.getDefaultSharedPreferences(
            Objects.requireNonNull(getContext()).getApplicationContext());
        //Log.d("user", "" + userData.getString("ID",null));

        // for testing purpose
        tvTest = view.findViewById(R.id.tvtest);

        runAsync();
        sendData.execute(z_BackendPreProcessing.URL_DataPelanggan +
                userData.getString("ID", null), null);

        return view;
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }

    backgroundTask sendData;
    // Alur setelah AsyncTask selesai
    protected void runAsync() {
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                tvTest.setText(output);
            }
        });
    }
}
