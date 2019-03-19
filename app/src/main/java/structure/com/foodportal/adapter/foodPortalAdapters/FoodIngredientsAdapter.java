package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Step;

public class FoodIngredientsAdapter extends RecyclerView.Adapter<FoodIngredientsAdapter.PlanetViewHolder> {

    ArrayList<CustomIngredient> ingredientList;
    ArrayList<String> title;
    String mytitle;

    public FoodIngredientsAdapter(ArrayList<CustomIngredient> ingredientList, ArrayList<String> title, Context context) {
        this.ingredientList = ingredientList;
        this.title = title;
    }

    @Override
    public FoodIngredientsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }
    int k=0;
    @Override
    public void onBindViewHolder(FoodIngredientsAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);



        if(ingredientList.get(position).getIsHeader()==1){
            k=0;
            String sourceString = "* " + ingredientList.get(position).getName()!=null ? ingredientList.get(position).getName():" " + " *";
            holder.text.setText(Html.fromHtml(sourceString));
            holder.text.setTypeface(null, Typeface.BOLD);
            holder.text.setTextColor(Color.BLACK);

            holder.tvQuantity.setText(ingredientList.get(position).getMainquantity()!=null ?ingredientList.get(position).getMainquantity(): " ");

        }else{
            k++;
            holder.text.setText(" "+k +"     "+ingredientList.get(position).getName() !=null ? ingredientList.get(position).getName() : " ");
            holder.tvQuantity.setText(ingredientList.get(position).getSubquantity() !=null ?ingredientList.get(position).getSubquantity() :" ");
        }



    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text,tvQuantity;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvingredients);
            tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
        }
    }
}