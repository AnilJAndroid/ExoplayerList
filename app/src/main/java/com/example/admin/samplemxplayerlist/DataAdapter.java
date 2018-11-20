package com.example.admin.samplemxplayerlist;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

/**
 * Created by root on 7/9/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    Context context;
    public static final int CALLBACK_USER_PROFILE = 111;

    private LayoutInflater mLayoutInflater;
    private List<String> list;

    public DataAdapter(Context context, List<String> list) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.video_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.ahv_ivPlay.bringToFront();
        holder.ahv_ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
//                holder.simpleExoPlayer.setPlayWhenReady(true);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements SurfaceHolder.Callback {
//        PlayerView ahv_player;
        ImageView ahv_ivPlay;
        SurfaceView surface_view;
        private SurfaceHolder mSurfaceHolder;


        public SimpleExoPlayer getSimpleExoPlayer() {
            return simpleExoPlayer;
        }

        public void setSimpleExoPlayer(SimpleExoPlayer simpleExoPlayer) {
            this.simpleExoPlayer = simpleExoPlayer;
        }

        SimpleExoPlayer simpleExoPlayer;

        public MyViewHolder(View view) {
            super(view);
//            ahv_player = view.findViewById(R.id.ahv_player);
            surface_view = view.findViewById(R.id.surface_view);
            ahv_ivPlay = view.findViewById(R.id.ahv_ivPlay);
            mSurfaceHolder = surface_view.getHolder();
            mSurfaceHolder.addCallback(this);
        }


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(context),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());
            Uri uri = Uri.parse(list.get(getAdapterPosition()));
            MediaSource mediaSource = buildMediaSource(uri);
            simpleExoPlayer.prepare(mediaSource, false, false);
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.setVideoSurfaceView(surface_view);

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    public void initializePlayer(Context context, SurfaceView surfaceView, int pos) {
//        PlayerView playerView = new PlayerView(context);
        DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
        TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this.context),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());
        /*playerView.setPlayer(simpleExoPlayer);
        playerView.getPlayer().seekTo(100);*/
        Uri uri = Uri.parse(list.get(pos));
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, false, false);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.setVideoSurfaceView(surfaceView);

    }


}
