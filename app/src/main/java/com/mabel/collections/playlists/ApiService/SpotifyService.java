package com.mabel.collections.playlists.ApiService;


import com.google.gson.JsonObject;
import com.mabel.collections.playlists.Model.Playlist;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by MabelEain on 8/27/2017.
 */
public interface SpotifyService {


    String LIMIT = "limit";

    String OFFSET = "offset";

    /**
     * Get Profile
     */
    @GET("/me")
    void getMe(Callback<JsonObject> callback);

    /**
     * Get User's Playlists
     */
    @GET("/me/playlists")
    void getMyPlaylists(Callback<JsonObject> callback);

    /**
     * Search for Track
     */
    @GET("/search?type=track")
    void searchTracks(@Query("q") String q, @QueryMap Map<String, Object> options, Callback<JsonObject> callback);

    /**
     * Get Playlsit's Tracks
     */
    @GET("/users/{user_id}/playlists/{playlist_id}/tracks")
    void getPlaylistTracks(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> options, Callback<JsonObject> callback);

    /**
     * Add track to a Playlist
     */
    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body, Callback<JsonObject> callback);

    /**
     * Create a Playlist
     */
    @POST("/users/{user_id}/playlists")
    Playlist createPlaylist(@Path("user_id") String userId, @Body Map<String, Object> options);

}
