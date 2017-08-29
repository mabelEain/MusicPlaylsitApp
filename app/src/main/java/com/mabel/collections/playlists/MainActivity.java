package com.mabel.collections.playlists;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mabel.collections.playlists.Adapter.PlaylistAdapter;
import com.mabel.collections.playlists.ApiService.SpotifyApi;
import com.mabel.collections.playlists.ApiService.SpotifyCallback;
import com.mabel.collections.playlists.ApiService.SpotifyError;
import com.mabel.collections.playlists.ApiService.SpotifyService;
import com.mabel.collections.playlists.Model.Playlist;
import com.mabel.collections.playlists.Model.User;
import com.mabel.collections.playlists.Util.APIUtil;
import com.mabel.collections.playlists.Util.AppPreference;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import retrofit.client.Response;

/**
 * Created by MabelEain on 8/27/2017.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    static final String EXTRA_TOKEN = "EXTRA_TOKEN";

    RecyclerView mRecyclerView;
    private PlaylistAdapter mAdapter;
    List<Playlist> displayList;
    SpotifyService service;
    Context mContext;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        Intent intent = getIntent();
        String token = intent.getStringExtra(EXTRA_TOKEN);
        displayList = new ArrayList<>();

        AppPreference.setTokenKey(this, token);
        init(token);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaylistAdapter(getApplicationContext(), displayList);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void init(String accessToken) {
        logMessage("Api Client created");
        SpotifyApi spotifyApi = new SpotifyApi();

        if (accessToken != null) {
            spotifyApi.setAccessToken(accessToken);
        } else {
            logError("No valid access token");
        }

        service = spotifyApi.getService();
        service.getMe(new SpotifyCallback<JsonObject>() {
            @Override
            public void failure(SpotifyError error) {
                logError(error.getMessage());
            }

            @Override
            public void success(JsonObject jsonObject, Response response) {

                User user = new User();
                user.user_id = APIUtil.getGsonAsString(jsonObject, "id", "null");
                user.user_href = APIUtil.getGsonAsString(jsonObject, "href", "null");
                user.user_uri = APIUtil.getGsonAsString(jsonObject, "uri", "null");

                AppPreference.setUserObject(mContext, user);
            }
        });
        loadPlaylists(service);
    }

    private void loadPlaylists(SpotifyService service) {
        service.getMyPlaylists(new SpotifyCallback<JsonObject>() {
            @Override
            public void failure(SpotifyError error) {
                logError(error.getMessage());
            }

            @Override
            public void success(JsonObject jsonObject, Response response) {
                JsonArray json_array = jsonObject.get("items").getAsJsonArray();
                for (int i = 0; i < json_array.size(); i++) {

                    JsonObject json_Object = json_array.get(i)
                            .getAsJsonObject();

                    Playlist playlist = new Playlist();

                    playlist.id = APIUtil.getGsonAsString(json_Object, "id", " ");
                    playlist.name = APIUtil.getGsonAsString(json_Object, "name", " ");
                    playlist.href = APIUtil.getGsonAsString(json_Object, "href", " ");
                    JsonArray jsonArray = json_Object.getAsJsonArray("images");
                    if (jsonArray.size() != 0) {
                        playlist.images_url = APIUtil.getGsonAsString(jsonArray.get(0).getAsJsonObject(), "url", " ");
                    } else {
                        playlist.images_url = "";
                    }

                    JsonObject jobj_tracks = json_Object.get("tracks").getAsJsonObject();
                    playlist.tracks_href = APIUtil.getGsonAsString(jobj_tracks, "href", " ");
                    playlist.total_song = APIUtil.getGsonAsString(jobj_tracks, "total", " ");
                    playlist.uri = APIUtil.getGsonAsString(json_Object, "uri", " ");

                    displayList.add(playlist);
                }

                mAdapter = new PlaylistAdapter(getApplicationContext(), displayList);
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
}
