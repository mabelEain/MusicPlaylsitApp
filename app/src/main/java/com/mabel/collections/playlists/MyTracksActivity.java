package com.mabel.collections.playlists;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mabel.collections.playlists.Adapter.PlaylistAdapter;
import com.mabel.collections.playlists.Adapter.TracksAdapter;
import com.mabel.collections.playlists.ApiService.SpotifyApi;
import com.mabel.collections.playlists.ApiService.SpotifyCallback;
import com.mabel.collections.playlists.ApiService.SpotifyError;
import com.mabel.collections.playlists.ApiService.SpotifyService;
import com.mabel.collections.playlists.Model.Playlist;
import com.mabel.collections.playlists.Model.Track;
import com.mabel.collections.playlists.Model.User;
import com.mabel.collections.playlists.Util.APIUtil;
import com.mabel.collections.playlists.Util.AppPreference;
import com.mabel.collections.playlists.Util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

public class MyTracksActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    private TracksAdapter mAdapter;
    List<Track> displayList;
    SpotifyService service;
    Context mContext;
    ProgressDialog progressDialog;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytracks);

        mContext = this;
        displayList = new ArrayList<>();
        progressDialog = DialogUtil.myProgressDialog(mContext);

        init(mContext);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TracksAdapter(getApplicationContext(), displayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void init(Context context) {

        String token = AppPreference.getTokenKey(mContext,"0");
        User user = AppPreference.getUserObject(mContext);
        String playlist_id = getIntent().getStringExtra("playlist_id");

        SpotifyApi spotifyApi = new SpotifyApi();

        if (token != null) {
            spotifyApi.setAccessToken(token);
        } else {
            logError("No valid access token");
        }

        service = spotifyApi.getService();
        loadTracks(service,user.user_id,playlist_id);
    }
    private void loadTracks(SpotifyService service,String user_id, String playlist_id) {
        progressDialog.show();
        service.getPlaylistTracks(user_id,playlist_id,new SpotifyCallback<JsonObject>() {
            @Override
            public void failure(SpotifyError error) {
                logError(error.getMessage());
            }

            @Override
            public void success(JsonObject jsonObject, Response response) {
                progressDialog.dismiss();
                JsonArray json_array = jsonObject.get("items").getAsJsonArray();
                for (int i = 0; i < json_array.size(); i++) {

                    JsonObject json_Object = json_array.get(i)
                            .getAsJsonObject();

                    Track track = new Track();
                    JsonObject jobj_track = json_Object.get("track").getAsJsonObject();
                    track.id = APIUtil.getGsonAsString(jobj_track, "id", " ");
                    track.name = APIUtil.getGsonAsString(jobj_track, "name", " ");
                    track.href = APIUtil.getGsonAsString(jobj_track, "href", " ");

                    JsonArray jsonArray = jobj_track.getAsJsonArray("artists");
                    if (jsonArray.size() != 0) {
                        track.artist_name = APIUtil.getGsonAsString(jsonArray.get(0).getAsJsonObject(), "name", " ");
                    } else {
                        track.artist_name = "";
                    }

                    JsonObject jobj_album = jobj_track.get("album").getAsJsonObject();
                    track.album_name = APIUtil.getGsonAsString(jobj_album, "name", " ");
                    track.uri = APIUtil.getGsonAsString(jobj_track, "uri", " ");

                    displayList.add(track);
                }

                AppPreference.setTrackObj(getApplicationContext(),displayList);
                mAdapter = new TracksAdapter(getApplicationContext(), displayList);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void logError(String msg) {
        Toast.makeText(this, "Error: " + msg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msg);
    }

    private void logMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
