package pallavgrover.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pallavgrover.bakingapp.R;
import pallavgrover.bakingapp.activity.RecipieDetailsListActivity;
import pallavgrover.bakingapp.models.Recipe;
import pallavgrover.bakingapp.widget.RecipeWidgetService;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private ArrayList<Recipe> mRecipeList;
    private Context context;

    public RecipeAdapter(ArrayList<Recipe> incomingRecipeSet, Context mContext) {
        this.mRecipeList = incomingRecipeSet;
        context = mContext;
    }

    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipie_list, parent, false);;
        return new RecipeHolder(inflater);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeHolder holder, final int position) {
//        Glide.with(holder.itemView.getContext())
//                .load(holder.mRecipe.getImage())
//                .into(holder.mBinding.ivRecipeImage);
        holder.tvRecipeName.setText(mRecipeList.get(position).getName());
        holder.tvRecipeName.setSelected(true);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,RecipieDetailsListActivity.class);
                i.putExtra("recipe",mRecipeList.get(position));
                RecipeWidgetService.startActionUpdateRecipeWidgets(context, mRecipeList.get(position));
                context.startActivity(i);
            }
        });
//        holder.tvIngredientCount.setText(String.valueOf(holder.mRecipe.getIngredients().size()));
//        holder.tvServingCount.setText(String.valueOf(holder.mRecipe.getServings()));
//        holder.tvStepCount.setText(String.valueOf(holder.mRecipe.getSteps().size()));
    }

    @Override
    public int getItemCount() {
        return (mRecipeList == null) ? 0 : mRecipeList.size();
    }


    public class RecipeHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        ImageView image;
        CardView cardView ;


        public RecipeHolder(View v) {
            super(v);
            tvRecipeName = (TextView) v.findViewById(R.id.tv_recipe_name);
            cardView = (CardView) v.findViewById(R.id.cv_recipe_list);
        }
    }
}