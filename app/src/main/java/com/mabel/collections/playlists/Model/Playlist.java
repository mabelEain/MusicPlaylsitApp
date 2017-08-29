package com.mabel.collections.playlists.Model;

import android.media.Image;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MabelEain on 8/28/2017.
 */

public class Playlist{

    public String href;
    public String id;
    public String images_url;
    public String tracks_href;
    public String total_song;
    public String name;
    public Boolean is_public;
    public String snapshot_id;
    public String type;
    public String uri;

    public Playlist() {
    }

    public Playlist(String href, String id, String images_url,String tracks_href, String total_song, String name,
                    Boolean is_public, String snapshot_id, String type, String uri) {
        this.href = href;
        this.id = id;
        this.images_url = images_url;
        this.tracks_href = tracks_href;
        this.total_song = total_song;
        this.name = name;
        this.is_public = is_public;
        this.snapshot_id = snapshot_id;
        this.type = type;
        this.uri = uri;
    }
}
