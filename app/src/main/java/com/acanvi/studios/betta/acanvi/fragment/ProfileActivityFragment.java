package com.acanvi.studios.betta.acanvi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acanvi.studios.betta.acanvi.R;
import com.acanvi.studios.betta.acanvi.transformations.CircleTransform;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ProfileActivityFragment extends Fragment {

    ImageView user_avatar;
    TextView user_name;
    TextView user_mail;

    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    void initViews(View view) {
        user_avatar = (ImageView) view.findViewById(R.id.profile_user_image);
        user_name = (TextView) view.findViewById(R.id.profile_user_name);
        user_mail = (TextView) view.findViewById(R.id.profile_user_mail);

        ParseFile image = (ParseFile) ParseUser.getCurrentUser().get("avatar");
        Picasso.with(getActivity()).load(image.getUrl()).transform(new CircleTransform()).into(user_avatar);

        user_name.setText(ParseUser.getCurrentUser().getUsername().toString());
        user_mail.setText(ParseUser.getCurrentUser().get("email").toString());
        user_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagePicker = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imagePicker, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(user_avatar);

            //Send image to server
            try {
                Bitmap bimage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bimage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();
                ParseFile file = new ParseFile("avatar.png", byteArray);
                ParseUser user = ParseUser.getCurrentUser();
                user.put("avatar", file);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                          //  Toast.makeText(getActivity(), "Image saved", Toast.LENGTH_LONG).show();
                        }
                        else {
                         //   Toast.makeText(getActivity(), "Image NOT saved", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
