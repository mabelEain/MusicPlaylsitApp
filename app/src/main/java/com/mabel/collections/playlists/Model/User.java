package com.mabel.collections.playlists.Model;

/**
 * Created by MabelEain on 8/27/2017.
 */

public class User {

    public String user_id = "";
    public String user_href = "";
    public String user_uri = "";

    public User() {
    }

    public User(String user_id, String user_href, String user_uri) {
        this.user_id = user_id;
        this.user_href = user_href;
        this.user_uri = user_uri;
    }
}
