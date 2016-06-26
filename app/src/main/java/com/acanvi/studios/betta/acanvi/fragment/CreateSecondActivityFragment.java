package com.acanvi.studios.betta.acanvi.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acanvi.studios.betta.acanvi.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateSecondActivityFragment extends Fragment {

    public CreateSecondActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_second, container, false);
    }



    public void sentAdToServer(ParseObject ad) {
        ad.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });

    }
}
