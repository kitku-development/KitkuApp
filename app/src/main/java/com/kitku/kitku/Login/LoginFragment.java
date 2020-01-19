package com.kitku.kitku.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kitku.kitku.Model.CustomerModel;
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
        final EditText emailBox = view.findViewById(R.id.edittextLoginEmail);
        final EditText passBox = view.findViewById(R.id.edittextLoginPassword);

        textviewLoginGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        final CustomerModel customer = new CustomerModel(Objects.requireNonNull(this.getContext()));

        /* Method yang akan mengarahkan pengguna ke Fragment User jika menekan tombol login pada Fragment Login */

        buttonfragmentlogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setEmail(emailBox.getText().toString());
                customer.setPassword(passBox.getText().toString());
                try {
                    new LoginProcedure().loginActivity(view, "User",
                            getChildFragmentManager(), customer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        if (!customer.getId().equals("")) {
            new LoginProcedure().gotoPage("User", getChildFragmentManager(), view);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}