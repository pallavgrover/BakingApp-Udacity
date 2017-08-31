package pallavgrover.bakingapp.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import pallavgrover.bakingapp.R;
import pallavgrover.bakingapp.adapter.RecipeAdapter;
import pallavgrover.bakingapp.models.Recipe;
import pallavgrover.bakingapp.retrofit.ApiClient;
import pallavgrover.bakingapp.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link } representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ArrayList<Recipe> recipeArrayList;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recyclerView;
    private boolean isTablet;
    private int lastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.recipie_recyler);
        assert recyclerView != null;
        recipeArrayList = (ArrayList<Recipe>) (savedInstanceState != null
                ? savedInstanceState.getParcelableArrayList("recipe")
                : new ArrayList<>());
        isTablet = getResources().getBoolean(R.bool.isTab);
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        if(isTablet || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        if(recipeArrayList.size()>0) {
            recipeAdapter = new RecipeAdapter(recipeArrayList,this);
            recyclerView.setAdapter(recipeAdapter);
        }else{
            getRecipieList();
        }
    }
    public void getRecipieList(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Recipe>> call = apiService.getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipeArrayList = response.body();
                recipeAdapter = new RecipeAdapter(recipeArrayList,RecipeListActivity.this);
                recyclerView.setAdapter(recipeAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                // Log error here since request failed
                Snackbar.make(findViewById(R.id.recipie_recyler),
                        "Error fetching list", Snackbar.LENGTH_SHORT);
                Log.e("", t.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipe", recipeArrayList);

    }

}
