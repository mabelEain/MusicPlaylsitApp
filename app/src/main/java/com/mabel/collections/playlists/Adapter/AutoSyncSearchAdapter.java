package com.mabel.collections.playlists.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mabel.collections.playlists.ApiService.SpotifyApi;
import com.mabel.collections.playlists.ApiService.SpotifyCallback;
import com.mabel.collections.playlists.ApiService.SpotifyError;
import com.mabel.collections.playlists.ApiService.SpotifyService;
import com.mabel.collections.playlists.Model.Playlist;
import com.mabel.collections.playlists.Model.Track;
import com.mabel.collections.playlists.R;
import com.mabel.collections.playlists.Util.APIUtil;
import com.mabel.collections.playlists.Util.AppPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.client.Response;

/**
 * Created by MabelEain on 8/29/2017.
 */

public class AutoSyncSearchAdapter extends ArrayAdapter implements Filterable {

    private Context mContext;
    private List<Track> resultList = new ArrayList<>();
    private int viewResourceId;
    LayoutInflater inflater;

    public AutoSyncSearchAdapter(Context context,int viewResourceId) {
        super(context,viewResourceId);
        mContext = context;
        this.viewResourceId = viewResourceId;
        inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Track getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(viewResourceId, parent, false);
        }
        TextView txtname;
        TextView txtartist_name;
        TextView txtalbum_name;


        txtname = (TextView) convertView.findViewById(R.id.entity_title);
        txtartist_name = (TextView) convertView.findViewById(R.id.total_song);
        txtalbum_name = (TextView) convertView.findViewById(R.id.entity_album);

        txtname.setText(getItem(position).name);
        txtartist_name.setText(getItem(position).artist_name);
        txtalbum_name.setText(getItem(position).album_name);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Track> tracks = findTracks(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = tracks;
                    filterResults.count = tracks.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Track>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<Track> findTracks(Context context, String trackname) {
        String token = AppPreference.getTokenKey(context, "0");
        SpotifyApi spotifyApi = new SpotifyApi();

        if (token != null) {
            spotifyApi.setAccessToken(token);
        } else {
            Log.e("Token Error", "No valid access token");
        }

        SpotifyService service = spotifyApi.getService();
        Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, 10);

        service.searchTracks(trackname, options, new SpotifyCallback<JsonObject>() {
            @Override
            public void failure(SpotifyError error) {

            }

            @Override
            public void success(JsonObject jsonObject, Response response) {
                JsonObject jObj_tracks = jsonObject.get("tracks").getAsJsonObject();
                JsonArray json_array = jObj_tracks.get("items").getAsJsonArray();
                for (int i = 0; i < json_array.size(); i++) {

                    JsonObject json_Object = json_array.get(i)
                            .getAsJsonObject();

                    Track track = new Track();

                    track.id = APIUtil.getGsonAsString(json_Object, "id", " ");
                    track.name = APIUtil.getGsonAsString(json_Object, "name", " ");
                    track.href = APIUtil.getGsonAsString(json_Object, "href", " ");
                    JsonArray jsonArray = json_Object.getAsJsonArray("artists");
                    if (jsonArray.size() != 0) {
                        track.artist_name = APIUtil.getGsonAsString(jsonArray.get(0).getAsJsonObject(), "name", " ");
                    } else {
                        track.artist_name = "";
                    }

                    JsonObject jobj_album = json_Object.get("album").getAsJsonObject();
                    track.album_name = APIUtil.getGsonAsString(jobj_album, "name", " ");
                    track.uri = APIUtil.getGsonAsString(json_Object, "uri", " ");

                    resultList.add(track);
                }
            }
        });
        return resultList;
    }


}
