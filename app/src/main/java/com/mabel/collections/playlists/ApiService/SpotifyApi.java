package com.mabel.collections.playlists.ApiService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.MainThreadExecutor;


public class SpotifyApi {

    /**
     * Main Spotify Web API endpoint
     */
    public static final String SPOTIFY_WEB_API_ENDPOINT = "https://api.spotify.com/v1";

    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    private class WebApiAuthenticator implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            if (mAccessToken != null) {
                request.addHeader("Authorization", "Bearer " + mAccessToken);
            }
        }
    }

    private final SpotifyService mSpotifyService;

    private String mAccessToken;

    /**
     * Create instance of SpotifyApi with given executors.
     *
     * @param httpExecutor executor for http request. Cannot be null.
     * @param callbackExecutor executor for callbacks. If null is passed than the same
     *                         thread that created the instance is used.
     */
    public SpotifyApi(Executor httpExecutor, Executor callbackExecutor) {
        mSpotifyService = init(httpExecutor, callbackExecutor);
    }

    private SpotifyService init(Executor httpExecutor, Executor callbackExecutor) {

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setExecutors(httpExecutor, callbackExecutor)
                .setEndpoint(SPOTIFY_WEB_API_ENDPOINT)
                .setRequestInterceptor(new WebApiAuthenticator())
                .build();

         return restAdapter.create(SpotifyService.class);
    }

    /**
     *  New instance of SpotifyApi,
     *  with single thread executor both for http and callbacks.
     */
    public SpotifyApi() {
        Executor httpExecutor = Executors.newSingleThreadExecutor();
        MainThreadExecutor callbackExecutor = new MainThreadExecutor();
        mSpotifyService = init(httpExecutor, callbackExecutor);
    }

    /**
     * Sets access token on the wrapper.
     * Use to set or update token with the new value.
     * If you want to remove token set it to null.
     *
     * @param accessToken The token to set on the wrapper.
     * @return The instance of the wrapper.
     */
    public SpotifyApi setAccessToken(String accessToken) {
        mAccessToken = accessToken;
        return this;
    }

    /**
     * @return The SpotifyApi instance
     */
    public SpotifyService getService() {
        return mSpotifyService;
    }
}
