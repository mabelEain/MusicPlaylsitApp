package com.mabel.collections.playlists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;

import com.mabel.collections.playlists.Adapter.AutoSyncSearchAdapter;
import com.mabel.collections.playlists.Model.Track;
import com.mabel.collections.playlists.UI.SearchAutocompleteView;

public class SearchTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);

        final SearchAutocompleteView trackname = (SearchAutocompleteView) findViewById(R.id.load_track);
        trackname.setThreshold(1);
        trackname.setAdapter(new AutoSyncSearchAdapter(this.getBaseContext(),R.layout.track_item)); // 'this' is Activity instance
        trackname.setLoadingIndicator(
                (android.widget.ProgressBar) findViewById(R.id.loading_indicator));
        trackname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Track track = (Track) adapterView.getItemAtPosition(position);
                trackname.setText(track.name);
            }
        });
    }
}
