package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.SpecialIngedient;
import structure.com.foodportal.models.foodModels.SpecialIngredientSteps;

public class FoodSpecialStepsAdapter extends RecyclerView.Adapter<FoodSpecialStepsAdapter.PlanetViewHolder> {

    ArrayList<SpecialIngredientSteps> ingredientList;
    ArrayList<String> title;
    String mytitle;
    MainActivity context;
    public FoodSpecialStepsAdapter(ArrayList<SpecialIngredientSteps> ingredientList,MainActivity context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @Override
    public FoodSpecialStepsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_steps, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodSpecialStepsAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        holder.maincontent.setText(ingredientList.get(position).getSteps_en());
        holder.mainheading.setText(ingredientList.get(position).getTitle_en());
        UIHelper.setImagewithGlide(context,holder.imagemain,ingredientList.get(position).getImage_path());


    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView mainheading,maincontent;
        KenBurnsView imagemain;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            mainheading = (TextView) itemView.findViewById(R.id.mainheading);
            maincontent = (TextView) itemView.findViewById(R.id.maincontent);
            imagemain = (KenBurnsView) itemView.findViewById(R.id.imagemain);
        }
    }
}