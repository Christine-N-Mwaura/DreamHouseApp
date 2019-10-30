package com.christine.movieStore.userInterface.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.christine.movieStore.R;
import com.christine.movieStore.model.Movie;

import java.util.ArrayList;

public class FavoritesAdapter extends ArrayAdapter<Movie> {
    private ArrayList<Movie> favoritesDataSet;
    Context mContext;

    public static class ViewHolder{
        TextView mMovieTitle;
        TextView mMovieOverView;
        TextView mReleaseDate;
    }
    public FavoritesAdapter(ArrayList<Movie> favoritesData , Context context){

        super(context, R.layout.single_favorite_movie, favoritesData);

        this.favoritesDataSet = favoritesData;
        this.mContext=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_favorite_movie, parent, false);
            viewHolder.mMovieTitle = (TextView) convertView.findViewById(R.id.text_title_text_value);
            viewHolder.mReleaseDate = (TextView) convertView.findViewById(R.id.text_release_date_text_value);
            viewHolder.mMovieOverView = (TextView) convertView.findViewById(R.id.text_overview_text_value);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        String releaseDate = movie.getReleaseDate().substring(14);
        String title = movie.getTitle().substring(6);

        viewHolder.mMovieTitle.setText(title);
        viewHolder.mMovieOverView.setText(movie.getOverview());
        viewHolder.mReleaseDate.setText(releaseDate);

        // Return the completed view to render on screen
        return convertView;
    }

}
