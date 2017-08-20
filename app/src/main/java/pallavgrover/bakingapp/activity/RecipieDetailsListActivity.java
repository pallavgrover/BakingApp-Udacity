package pallavgrover.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import pallavgrover.bakingapp.R;
import pallavgrover.bakingapp.adapter.StepAdapter;
import pallavgrover.bakingapp.fragment.RecipieDetailsDetailFragment;
import pallavgrover.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * An activity representing a list of RecipieDetails. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipieDetailsDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipieDetailsListActivity extends AppCompatActivity {

    public static final int TYPE_HEADER = 0x2f;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe recipe;
    private ArrayList arrayList;
    private RecyclerView recyclerView;
    private static int lastFirstVisiblePosition;

    public static Intent newIntent(Context packageContext, Recipe recipe) {
        Intent intent = new Intent(packageContext, RecipieDetailsListActivity.class);
        intent.putExtra("recipe", recipe);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipiedetails_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = findViewById(R.id.recipiedetails_list);
        assert recyclerView != null;

        if (findViewById(R.id.recipiedetails_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        if(savedInstanceState != null){
            recipe = savedInstanceState.getParcelable("array");
        }else {
            recipe = getIntent().getParcelableExtra("recipe");
        }
        arrayList = new ArrayList();
        CustomData titleData = new CustomData(TYPE_HEADER, getString(R.string.ingrediants));
        arrayList.add(titleData);
        arrayList.addAll(recipe.getIngredients());
        CustomData stepTitle = new CustomData(TYPE_HEADER, getString(R.string.steps));
        arrayList.add(stepTitle);
        arrayList.addAll(recipe.getSteps());
//        }
        setupRecyclerView((RecyclerView) recyclerView);
        getSupportActionBar().setTitle(recipe.getName());
    }

    public static class CustomData {
        public int type;
        public Object data;

        CustomData(int type, Object data) {
            this.type = type;
            this.data = data;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new StepAdapter(arrayList,this,mTwoPane,recipe));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("pal", "onSaveInstanceState: ");
        outState.putParcelable("array",recipe);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lastFirstVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
    }
}
