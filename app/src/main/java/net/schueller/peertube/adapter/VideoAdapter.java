package net.schueller.peertube.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.schueller.peertube.R;
import net.schueller.peertube.activity.VideoPlayActivity;
import net.schueller.peertube.model.Video;

import java.util.ArrayList;

import static net.schueller.peertube.activity.VideoListActivity.EXTRA_VIDEOID;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {


    private ArrayList<Video> videoList;
    private Context context;

    public VideoAdapter(ArrayList<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        Picasso.with(this.context)
                .load("https://troll.tv" + videoList.get(position).getPreviewPath())
                .into(holder.thumb);

        holder.name.setText(videoList.get(position).getName());
        holder.videoMeta.setText(videoList.get(position).getAccountName()
                .concat("@")
                .concat(videoList.get(position).getServerHost()).concat(" - ")
                .concat(videoList.get(position).getViews()+" Views"));

        holder.mView.setOnClickListener(v -> {

            // Log.v("VideoAdapter", "click: " + videoList.get(position).getName());

            Intent intent = new Intent(context, VideoPlayActivity.class);
            intent.putExtra(EXTRA_VIDEOID, videoList.get(position).getUuid());
            context.startActivity(intent);

        });

    }

    public void setData(ArrayList<Video> data) {
        videoList.addAll(data);
        this.notifyDataSetChanged();
    }

    public void clearData() {
        videoList.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView name, videoMeta;
        ImageView thumb;
        View mView;

        VideoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            thumb = itemView.findViewById(R.id.thumb);
            videoMeta = itemView.findViewById(R.id.videoMeta);
            mView = itemView;
        }
    }

}