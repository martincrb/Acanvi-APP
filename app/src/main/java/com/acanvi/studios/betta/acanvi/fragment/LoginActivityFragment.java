package com.acanvi.studios.betta.acanvi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.acanvi.studios.betta.acanvi.R;
import com.acanvi.studios.betta.acanvi.activity.MainMenuActivity;
import com.acanvi.studios.betta.acanvi.activity.SigninActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Unbinder;


public class LoginActivityFragment extends Fragment{
    private Unbinder unbinder;

    TextView signin_message;
    ImageButton login_button;
    EditText username_field;
    EditText password_field;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        signin_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInActivity();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToApp();
            }
        });
        //ButterKnife.setDebug(true);
        //unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SigninActivity.class);
        startActivity(intent);
    }

    public void loginToApp() {
        String email = username_field.getText().toString();
        String password = password_field.getText().toString();
        login_button.setVisibility(View.INVISIBLE);
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Log.i("LogIn", "Succesful");
                    startMainMenuActivity();
                    getActivity().finish();
                }
                else {
                    Log.i("LogIn", "Failure");
                    username_field.setError(e.getMessage());
                    login_button.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void initViews(View view) {
        signin_message = (TextView) view.findViewById(R.id.login_signintext);
        login_button = (ImageButton) view.findViewById(R.id.login_imageView3);
        username_field = (EditText) view.findViewById(R.id.login_editText);
        password_field = (EditText) view.findViewById(R.id.login_editText2);
    }
    public void startMainMenuActivity() {
        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
        startActivity(intent);
    }


}
