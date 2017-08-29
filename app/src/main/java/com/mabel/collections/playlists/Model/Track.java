package com.mabel.collections.playlists.Model;

/**
 * Created by MabelEain on 8/28/2017.
 */

public class Track {

    public String id;
    public String name;
    public String href;
    public String artist_name;
    public String uri;
    public String album_name;

    public Track() {
    }

    public Track(String id, String name, String href, String artist_name, String uri, String album_name) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.artist_name = artist_name;
        this.uri = uri;
        this.album_name = album_name;
    }
}
