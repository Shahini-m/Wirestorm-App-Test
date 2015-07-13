package com.appwithkotlin.mshahini.wirestormapplication.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.appwithkotlin.mshahini.wirestormapplication.R;

import com.squareup.picasso.Picasso;

/**
 * Created by mshahini on 13.07.2015.
 */
public class UserProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout);

        ImageView imageView = (ImageView) findViewById(R.id.lrgpic);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            String urlImage = args.getString("pic");
            if (!TextUtils.isEmpty(urlImage)) {
                Picasso.with(this).load(urlImage).into(imageView);
            }
        }
    }
}
