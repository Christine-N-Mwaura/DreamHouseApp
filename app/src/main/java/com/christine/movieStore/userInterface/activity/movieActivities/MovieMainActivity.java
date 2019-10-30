package com.christine.movieStore.userInterface.activity.movieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.christine.movieStore.R;
import com.christine.movieStore.model.Movie;
import com.christine.movieStore.model.MoviePageResult;
import com.christine.movieStore.network.GetMovieDataService;
import com.christine.movieStore.network.RetrofitInstance;
import com.christine.movieStore.userInterface.activity.MainActivity;
import com.christine.movieStore.userInterface.adapter.MovieAdapter;
import com.christine.movieStore.userInterface.utils.EndlessRecyclerViewScrollListener;
import com.christine.movieStore.userInterface.utils.MovieClickListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieMainActivity extends AppCompatActivity {

    public static final String API_KEY = "2ce794e7328d397fca26bfa0eb3f2c54";
    private static int totalPages;
    private static int currentSortMode = 1;
    private Call<MoviePageResult> call;
    private RecyclerView recyclerView;
    private List<Movie> movieResults;
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        recyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);





        loadPage(1);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((page + 1) <= totalPages) {
                    loadPage(page + 1);
                }
            }
        };


        recyclerView.addOnScrollListener(scrollListener);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        switch (item.getItemId()) {
            case R.id.sort_by_popularity:
                currentSortMode = 1;
                break;
            case R.id.sort_by_top:
                currentSortMode = 2;
                break;
            case R.id.action_logout:
                logout();
                return true;
            case R.id.sort_by_favorites:
                Intent intent = new Intent(MovieMainActivity.this, FavoritesActivity.class);
                startActivity(intent);
                finish();

        }
        loadPage(1);
        return super.onOptionsItemSelected(item);

    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MovieMainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

//    private void favorites(){
//        Intent intent = new Intent(MovieMainActivity.this,FavoritesActivity.class);
//        startActivity(intent);
//        finish();
//
//    }

    private void loadPage(final int page) {
        GetMovieDataService movieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);

        switch(currentSortMode){
            case 1:
                call = movieDataService.getPopularMovies(page, API_KEY);
                break;
            case 2:
                call = movieDataService.getTopRatedMovies(page, API_KEY);
                break;
        }


        call.enqueue(new Callback<MoviePageResult>() {
            @Override
            public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {

                if(page == 1) {
                    movieResults = response.body().getMovieResult();
                    totalPages = response.body().getTotalPages();

                    movieAdapter = new MovieAdapter(movieResults, new MovieClickListener() {
                        @Override
                        public void onMovieClick(Movie movie) {
                            Intent intent = new Intent(MovieMainActivity.this, MovieActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("movie", movie);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    List<Movie> movies = response.body().getMovieResult();
                    for(Movie movie : movies){
                        movieResults.add(movie);
                        movieAdapter.notifyItemInserted(movieResults.size() - 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<MoviePageResult> call, Throwable t) {
                Toast.makeText(MovieMainActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN
    |ItemTouchHelper.START |ItemTouchHelper.END,0){

        @Override
        public  boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target){
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(movieResults, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return  false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){

        }

    };
}
