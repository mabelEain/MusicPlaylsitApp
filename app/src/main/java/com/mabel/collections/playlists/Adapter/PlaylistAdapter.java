package com.mabel.collections.playlists.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabel.collections.playlists.Model.Playlist;
import com.mabel.collections.playlists.R;
import com.mabel.collections.playlists.SearchTestActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MabelEain on 8/28/2017.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    List<Playlist> mItems;
    Context context;

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        super();
        mItems = playlists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playlist_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Playlist assign_object = mItems.get(i);
        Playlist item = mItems.get(i);

        holder.title.setText(item.name);

        holder.subtitle.setText(item.total_song + "songs");

        //Image image = item.images.get(0);
        if (item.images_url != "") {
            Picasso.with(context).load(item.images_url).into(holder.image);
        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView title;
        public final TextView subtitle;
        public final ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.entity_title);
            subtitle = (TextView) itemView.findViewById(R.id.total_song);
            image = (ImageView) itemView.findViewById(R.id.entity_image);
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