package com.acanvi.studios.betta.acanvi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.acanvi.studios.betta.acanvi.R;
import com.acanvi.studios.betta.acanvi.activity.SearchActivity;
import com.parse.ParseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainMenuActivityFragment extends Fragment {

    TextView text;
    Button create_button;
    Button search_button;
    public MainMenuActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        initViews(view);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchActivity();
            }
        });
        text.setText("Hello "+ ParseUser.getCurrentUser().getUsername()+"!");
        return view;
    }


    public void initViews(View view) {
        text = (TextView) view.findViewById(R.id.menu_text);
        create_button = (Button) view.findViewById(R.id.button);
        search_button = (Button) view.findViewById(R.id.button2);
    }

    public void startSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
}
