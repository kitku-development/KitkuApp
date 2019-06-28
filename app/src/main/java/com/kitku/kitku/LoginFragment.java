package com.kitku.kitku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginFragment extends Fragment {

    Button buttonfragmentlogin_Login;
    TextView textviewLoginGoToRegister;
    RelativeLayout layoutLoginFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        buttonfragmentlogin_Login = view.findViewById(R.id.buttonfragmentlogin_Login);
        textviewLoginGoToRegister = view.findViewById(R.id.textviewLoginGoToRegister);
        layoutLoginFragment = view.findViewById(R.id.layoutLoginFragment);

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
                layoutLoginFragment.setVisibility(View.GONE);

                Fragment userDetailFragment = new UserFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameFragmentContainerLoginGoToUserDetailFragment, userDetailFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
