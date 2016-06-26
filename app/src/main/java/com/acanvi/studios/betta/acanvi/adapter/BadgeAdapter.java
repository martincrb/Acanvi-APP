package com.acanvi.studios.betta.acanvi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acanvi.studios.betta.acanvi.R;

import java.util.List;

public class BadgeAdapter extends
        RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    private List<String> mBadges;
    private Context mContext;
    private static final int VIEW_TYPE_SELECTED = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    private int paddingWidthBadge = 0;

    private int selectedItem = -1;

    public BadgeAdapter(Context context, List<String> badges) {
        mBadges = badges;
        mContext = context;

    }
    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEW_TYPE_ITEM) {
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.badge_list_item, parent, false);
            // Return a new holder instance
            return new ViewHolder(contactView);
        }
        else {
            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.badge_list_item_selected, parent, false);
            // Return a new holder instance
            return new ViewHolder(contactView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Get the data model based on position
        String badge = mBadges.get(position%mBadges.size());

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(badge);

    }

    @Override
    public int getItemCount() {
        //return mBadges.size();
        return Integer.MAX_VALUE;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.badge_text);
        }
    }
}
