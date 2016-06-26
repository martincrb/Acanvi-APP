package com.acanvi.studios.betta.acanvi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.acanvi.studios.betta.acanvi.R;
import com.acanvi.studios.betta.acanvi.activity.SearchResultActivity;
import com.acanvi.studios.betta.acanvi.adapter.BadgeAdapter;

import java.util.ArrayList;


public class SearchActivityFragment extends Fragment {


    RecyclerView quiero_list;
    RecyclerView tengo_list;
    ArrayList<String> badges;
    BadgeAdapter bAdapter;
    Button search_button;
    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchResultActivity();
            }
        });
        return view;
    }

    public void initViews(View view) {
        tengo_list = (RecyclerView) view.findViewById(R.id.tengo_list);
        quiero_list = (RecyclerView) view.findViewById(R.id.quiero_list);
        search_button =  (Button) view.findViewById(R.id.buscar_cambio_button);
        initRecyclerView(tengo_list);
        initRecyclerView(quiero_list);
       // tengo_list.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void initRecyclerView(final RecyclerView rv) {

        badges = new ArrayList<String>();

        badges.add("EUR");
        badges.add("USD");
        badges.add("GBP");
        bAdapter = new BadgeAdapter(getActivity(), badges);
        rv.setAdapter(bAdapter);


        ViewTreeObserver vtoObs = rv.getViewTreeObserver();
        vtoObs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rv.getViewTreeObserver().removeOnPreDrawListener(this);
                rv.scrollToPosition(Integer.MAX_VALUE/2);
                int finalWidth = rv.getMeasuredWidth();
                return true;
            }
        });
    }

    public void startSearchResultActivity() {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        startActivity(intent);
    }
}
