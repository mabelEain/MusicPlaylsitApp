package com.mabel.collections.playlists.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabel.collections.playlists.Model.Playlist;
import com.mabel.collections.playlists.Model.Track;
import com.mabel.collections.playlists.R;
import com.mabel.collections.playlists.SearchTestActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MabelEain on 8/28/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder> {

    List<Track> mItems;
    Context context;

    public TracksAdapter(Context context, List<Track> playlists) {
        super();
        mItems = playlists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Track item = mItems.get(i);

        Log.d("Adapter", item.id);
        holder.title.setText(item.name);

        holder.artist_name.setText(item.artist_name);

        holder.album.setText(item.album_name);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView title;
        public final TextView artist_name;
        public final TextView album;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.track_name);
            artist_name = (TextView) itemView.findViewById(R.id.total_song);
            album = (TextView) itemView.findViewById(R.id.entity_album);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(getLayoutPosition());
            Intent intent = new Intent(v.getContext(), SearchTestActivity.class);
            v.getContext().startActivity(intent);
            // mListener.onItemSelected(v, mItems.get(getAdapterPosition()));
        }
    }



}