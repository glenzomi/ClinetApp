package com.ivanzomi.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {
    ArrayList<EpisodeModel> models = new ArrayList<>();
    Context context;
    FragmentManager fm;

    public EpisodeAdapter(ArrayList<EpisodeModel> models, Context context, FragmentManager fm) {
        this.models = models;
        this.context = context;
        this.fm = fm;
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodeitem,parent,false);
        EpisodeHolder holder = new EpisodeHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, final int position) {

        holder.epName.setText(models.get(position).episodeName);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EpisodeModel model = models.get(position);
                MovieModel movieModel = new MovieModel();
                movieModel.movieName = model.episodeName;
                movieModel.movieVideo = model.episodeVideo;
                goToNext(position, movieModel);
            }
        });

    }
    public void goToNext(int position,MovieModel movieModel)
    {
        MainActivity.preFrg = MainActivity.currentFrag;
        MainActivity.currentFrag = context.getString(R.string.movie_dat_frag);
        VideoDetailFragment detfrag = new VideoDetailFragment();
        detfrag.movieModel = movieModel;
        setFragment(detfrag);
        MovieFragment.setHeader(movieModel.movieName);
    }
    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class EpisodeHolder extends RecyclerView.ViewHolder{
        TextView epName;
        ImageView play,download;
        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);
            epName = itemView.findViewById(R.id.epname);
            play= itemView.findViewById(R.id.play_ep);
            download = itemView.findViewById(R.id.download_ep);

        }
    }
}
