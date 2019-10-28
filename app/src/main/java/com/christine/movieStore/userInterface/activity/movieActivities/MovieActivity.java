package com.christine.movieStore.userInterface.activity.movieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.christine.movieStore.R;
import com.christine.movieStore.model.Movie;
import com.christine.movieStore.model.MovieTrailerResult;
import com.christine.movieStore.network.GetMovieTrailerService;
import com.christine.movieStore.network.RetrofitInstance;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.christine.movieStore.userInterface.activity.movieActivities.movieMainActivity.API_KEY;
import static com.christine.movieStore.userInterface.activity.movieActivities.movieMainActivity.movieImagePathBuilder;

public class MovieActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.movie_activity_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_activity_poster)
    ImageView mMoviePoster;
    @BindView(R.id.movie_activity_overview) TextView mMovieOverview;
    @BindView(R.id.movie_activity_release_date) TextView mMovieReleaseDate;
    @BindView(R.id.movie_activity_rating)
    TextView mMovieRating;
    @BindView(R.id.favoritesBtn)
    Button mFavorites;
    private Movie mMovies;
    public static final String TAG = MovieActivity.class.getSimpleName();

    long maxid = 0;

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);



        ButterKnife.bind(this);


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        databaseMovies = database.getReference("movie");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        com.christine.movieStore.model.Movie mMovie = (com.christine.movieStore.model.Movie) bundle.getSerializable("movie");

        getTrailer(mMovie.getId());
        populateActivity(mMovie);

        mFavorites.setOnClickListener(this);

    }

    private void populateActivity(com.christine.movieStore.model.Movie mMovie){
        final Picasso picasso = Picasso.get();
        picasso.load(movieImagePathBuilder(mMovie.getPosterPath())).into(mMoviePoster);
        mMovieTitle.setText("Title:  " + mMovie.getTitle());
        mMovieOverview.setText(mMovie.getOverview());
        mMovieReleaseDate.setText("Release Date:  " + mMovie.getReleaseDate());

        String userRatingText = "Rating:  " + String.valueOf(mMovie.getVoteAverage()) + "/10";
        mMovieRating.setText(userRatingText);
    }

    private void getTrailer(int movieId) {
        GetMovieTrailerService movieTrailerService = RetrofitInstance.getRetrofitInstance().create(GetMovieTrailerService.class);
        Call<MovieTrailerResult> call = movieTrailerService.getTrailers(movieId, API_KEY);


        call.enqueue(new Callback<MovieTrailerResult>() {
            @Override
            public void onResponse(Call<MovieTrailerResult> call, Response<MovieTrailerResult> response) {
                Log.wtf("MovieActivity", "https://youtube.com/watch?v=" + response.body().getTrailerResult().get(0).getKey());

            }

            @Override
            public void onFailure(Call<MovieTrailerResult> call, Throwable t) {
                Toast.makeText(MovieActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        addFavorites();
    }

    public void addFavorites(){

        String title = mMovieTitle.getText().toString();
        String overview = mMovieOverview.getText().toString();
        String releaseDate = mMovieReleaseDate.getText().toString();
        //mDatabaseReference.setValue(title);
        Movie movie = new Movie(title,overview,releaseDate );
        mDatabaseReference = mDatabase.getReference().child("favorites");
        String key = mDatabaseReference.push().getKey();
        mDatabaseReference.child(key).setValue(movie);

        Toast.makeText(this,"Movie added to favorites",Toast.LENGTH_SHORT).show();

    }
}
