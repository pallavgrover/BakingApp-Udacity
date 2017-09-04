package pallavgrover.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pallavgrover.bakingapp.R;
import pallavgrover.bakingapp.activity.RecipieDetailsDetailActivity;
import pallavgrover.bakingapp.activity.RecipieDetailsListActivity;
import pallavgrover.bakingapp.fragment.RecipieDetailsDetailFragment;
import pallavgrover.bakingapp.models.Ingredient;
import pallavgrover.bakingapp.models.Recipe;
import pallavgrover.bakingapp.models.Step;

import static pallavgrover.bakingapp.activity.RecipieDetailsListActivity.TYPE_HEADER;

public class StepAdapter extends RecyclerView.Adapter {
    private List recipeList;
    private Recipe recipe;
    private Context context;
    private boolean mTwoPane;

    public StepAdapter(List mRecipeList, Context mContext,boolean twoPane,Recipe mRecipe) {
        this.recipeList = mRecipeList;
        context = mContext;
        mTwoPane = twoPane;
        recipe = mRecipe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        if(viewType == TYPE_HEADER){
            view = LayoutInflater.from(context).inflate(R.layout.title_layout, parent, false);
            viewHolder = new TitleViewHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.step_list, parent, false);
            viewHolder =   new StepHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(recipeList.get(position) instanceof RecipieDetailsListActivity.CustomData){
            return TYPE_HEADER;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {
        RecipieDetailsListActivity.CustomData customData = null;
        if(recipeList.get(position) instanceof RecipieDetailsListActivity.CustomData) {
             customData = (RecipieDetailsListActivity.CustomData) recipeList.get(position);
        }
        if(customData != null && customData.type== TYPE_HEADER){
            ((TitleViewHolder) holder1).bindView((String) customData.data);
        }else {
            StepHolder holder = (StepHolder) holder1;
            if (recipeList.get(position) instanceof Ingredient) {
                holder.tvRecipeName.setText(((Ingredient) recipeList.get(position)).getIngredient());
                holder.tvRecipeName.setSelected(true);
            } else {
                holder.tvRecipeName.setText(((Step) recipeList.get(position)).getId() + ". " + ((Step) recipeList.get(position)).getShortDescription());
                holder.tvRecipeName.setSelected(true);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isTablet = context.getResources().getBoolean(R.bool.isTab);
                    if (recipeList.get(position) instanceof Step) {
                        if (mTwoPane || isTablet) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelable(RecipieDetailsDetailFragment.ARG_RECIPIE_ID, (Step) recipeList.get(position));
                            arguments.putParcelable("recipe", recipe);
                            RecipieDetailsDetailFragment fragment = new RecipieDetailsDetailFragment();
                            fragment.setArguments(arguments);
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipiedetails_detail_container, fragment).addToBackStack(null)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, RecipieDetailsDetailActivity.class);
                            intent.putExtra(RecipieDetailsDetailFragment.ARG_RECIPIE_ID, (Step) recipeList.get(position));
                            intent.putExtra("recipe", recipe);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class StepHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        ImageView image;
        CardView cardView ;


        public StepHolder(View v) {
            super(v);
            tvRecipeName = (TextView) v.findViewById(R.id.tv_recipe_name1);
            cardView = (CardView) v.findViewById(R.id.cv_recipe_list1);
        }
    }
}