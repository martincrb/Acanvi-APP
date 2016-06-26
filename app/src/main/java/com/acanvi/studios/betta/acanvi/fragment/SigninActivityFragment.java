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

import com.acanvi.studios.betta.acanvi.R;
import com.acanvi.studios.betta.acanvi.activity.MainMenuActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class SigninActivityFragment extends Fragment {

    ImageButton signin_button;
    EditText username_field;
    EditText password_field;
    EditText mail_field;

    public SigninActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        initViews(view);
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    signInToApp();
                }
            }
        });
        return view;
    }

    public boolean checkEmpty() {
        boolean valid_email = !mail_field.getText().toString().isEmpty();
        boolean valid_password = !password_field.getText().toString().isEmpty();
        boolean valid_username = !username_field.getText().toString().isEmpty();
        if (!valid_email) {
            mail_field.setError("Invalid email adress");
        }
        if (!valid_password) {
            password_field.setError("Invalid password");
        }
        if (!valid_username) {
            username_field.setError("Invalid username");
        }
        return valid_email && valid_password && valid_username;
    }

    public void signInToApp() {
        ParseUser user = new ParseUser();
        user.setEmail(mail_field.getText().toString());
        user.setPassword(password_field.getText().toString());
        user.setUsername(username_field.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("SignIn", "Succesful");
                    startMainMenuActivity();
                }
                else {
                    Log.i("SignIn", "Failure");
                    mail_field.setError(e.getMessage());
                }
            }
        });
    }
    public void initViews(View view) {
        signin_button = (ImageButton) view.findViewById(R.id.signin_imageView3);
        username_field = (EditText) view.findViewById(R.id.signin_editText4);
        password_field = (EditText) view.findViewById(R.id.signin_editText3);
        mail_field = (EditText) view.findViewById(R.id.signin_editText);
    }

    public void startMainMenuActivity() {
        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
        startActivity(intent);
    }
}
