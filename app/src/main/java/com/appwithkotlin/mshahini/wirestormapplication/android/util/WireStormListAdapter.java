package com.appwithkotlin.mshahini.wirestormapplication.android.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appwithkotlin.mshahini.wirestormapplication.R;
import com.appwithkotlin.mshahini.wirestormapplication.android.model.User;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by mshahini on 13.07.2015.
 */
public class WireStormListAdapter extends BaseAdapter {
    private Context mContext;
    private int mLayoutResourceId;
    private List<User> mUsersList = Collections.emptyList();

    public WireStormListAdapter(Context context, int layoutResourceId) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
    }

    public void setData(List<User> usersList) {
        this.mUsersList = usersList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new UserHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.position = (TextView) convertView.findViewById(R.id.position);
            holder.smallpic = (ImageView) convertView.findViewById(R.id.small_pic);

            convertView.setTag(holder);
        } else {
            holder = (UserHolder) convertView.getTag();
        }

        User user = mUsersList.get(position);
        holder.name.setText(user.name);
        holder.position.setText(user.position);
        Picasso.with(mContext).load(user.smallpic).into(holder.smallpic);

        return convertView;
    }

    static class UserHolder {
        TextView name;
        TextView position;
        ImageView smallpic;
    }
}