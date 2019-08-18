package com.kitku.kitku.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kitku.kitku.R;
import com.kitku.kitku.RegisterActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        Button buttonfragmentlogin_Login = view.findViewById(R.id.buttonfragmentlogin_Login);
        TextView textviewLoginGoToRegister = view.findViewById(R.id.textviewLoginGoToRegister);

        textviewLoginGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        /* Method yang akan mengarahkan pengguna ke Fragment User jika menekan tombol login pada Fragment Login */

        buttonfragmentlogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginProcedure().loginActivity(view, "User", getContext(), getChildFragmentManager());
            }
        });

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(
                Objects.requireNonNull(getContext()).getApplicationContext());
        if (data.contains("ID_User"))
            new LoginProcedure().gotoPage("User", getChildFragmentManager(), view);
        else if (data.contains("ID_Mitra"))
            new LoginProcedure().gotoPage("Mitra", getChildFragmentManager(), view);
        return view;
    }
}