package com.nizhal.lynkhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.markushi.ui.CircleButton;

public class UserLoginFragment extends Fragment {
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =   inflater.inflate(R.layout.fragment_user_login, container, false);
        CircleButton user_login = rootView.findViewById(R.id.user_login);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),UserHome.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return rootView;
    }
}
