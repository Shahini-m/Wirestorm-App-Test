package com.appwithkotlin.mshahini.wirestormapplication.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appwithkotlin.mshahini.wirestormapplication.R;
import com.appwithkotlin.mshahini.wirestormapplication.android.model.User;
import com.appwithkotlin.mshahini.wirestormapplication.android.service.WirestormService;
import com.appwithkotlin.mshahini.wirestormapplication.android.util.WireStormListAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


/**
 * This class isn't completely. It is for test and show a list of users.
 * Created by mshahini on 13.07.2015.
 */
public class MainActivity extends Activity {

    private List<User> mUsersCached;
    private ListView mList;
    private ProgressBar mProgress;
    private TextView mError;
    private UserListLoader userListLoader;
    private WireStormListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.list);
        mProgress = (ProgressBar) findViewById(R.id.progress);

        userListLoader = new UserListLoader();

        mAdapter = new WireStormListAdapter(this, R.layout.user_item);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String picUrl = ((User) mUsersCached.get(position)).lrgpic;
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                intent.putExtra("pic", picUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userListLoader.setTarget(this);
        userListLoader.load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userListLoader.setTarget(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class UserListLoader {
        MainActivity target;

        public void setTarget(MainActivity target) {
            this.target = target;
        }

        public void load() {
            target.mProgress.setVisibility(View.VISIBLE);
            target.mList.setVisibility(View.GONE);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://s3-us-west-2.amazonaws.com")
                    .build();

            WirestormService service = restAdapter.create(WirestormService.class);

            service.userList(new Callback<List<User>>() {
                @Override
                public void success(List<User> users, Response response) {
                    if (target == null) return;

                    mUsersCached = users;

                    target.mProgress.setVisibility(View.GONE);
                    target.mList.setVisibility(View.VISIBLE);

                    target.mAdapter.setData(mUsersCached);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    if (target == null) return;

                    target.mError.setVisibility(View.VISIBLE);
                    target.mProgress.setVisibility(View.GONE);
                    target.mList.setVisibility(View.GONE);

                    target.mError.setText("Error: " + retrofitError.getMessage());
                }
            });
        }
    }
}
