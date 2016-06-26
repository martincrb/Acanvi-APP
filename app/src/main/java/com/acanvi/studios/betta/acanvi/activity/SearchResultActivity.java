package com.acanvi.studios.betta.acanvi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.acanvi.studios.betta.acanvi.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

public class SearchResultActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, ParseObject> adverts;
    private View userView;
    private TextView username;
    private TextView tengo;
    private TextView quiero;
    private ImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        adverts = new HashMap<Marker, ParseObject>();
        userView = (View) findViewById(R.id.user_view_map);
        userView.setVisibility(View.GONE);

        username = (TextView) userView.findViewById(R.id.textView);
        tengo = (TextView) userView.findViewById(R.id.textView4);
        quiero = (TextView) userView.findViewById(R.id.textView5);
        userImage = (ImageView) userView.findViewById(R.id.imageView2);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (userView.getVisibility() == View.VISIBLE) { //Only hide if visible
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_down);
                    userView.setVisibility(View.GONE);
                    userView.startAnimation(slide_down);
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Show user
                ParseObject user = adverts.get(marker);
                username.setText(user.get("createdBy").toString());
                quiero.setText(user.get("quantity").toString() + user.get("originCoin").toString());
                tengo.setText(user.get("quantity").toString() + user.get("destinyCoin").toString());

                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("username", user.get("createdBy").toString());

                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                          //  ParseFile image = (ParseFile) objects.get(0).get("avatar");
                          //  Picasso.with(getApplicationContext()).load(image.getUrl()).transform(new CircleTransform()).into(userImage);
                        } else {

                        }
                    }
                });



                Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                userView.setVisibility(View.VISIBLE);
                userView.startAnimation(slide_up);
                return true;
            }
        });
        setMarkersFromCloud();
    }
    public void setMarkersFromCloud() {
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Search");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    adverts.clear();
                    for (ParseObject ad : objects) {
                        ParseGeoPoint place = ad.getParseGeoPoint("location");


                        Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())));
                        adverts.put(m, ad);
                        builder.include(new LatLng(place.getLatitude(), place.getLongitude()));
                    }

                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                    mMap.animateCamera(cu);
                } else {

                }
            }
        });
    }

}
