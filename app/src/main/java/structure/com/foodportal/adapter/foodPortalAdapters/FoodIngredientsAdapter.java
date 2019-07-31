package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.Spanny;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.Step;

public class FoodIngredientsAdapter extends RecyclerView.Adapter<FoodIngredientsAdapter.PlanetViewHolder> {

    ArrayList<CustomIngredient> ingredientList;
    ArrayList<String> title;
    String mytitle;
    MainActivity context;

    public FoodIngredientsAdapter(ArrayList<CustomIngredient> ingredientList, ArrayList<String> title, MainActivity context) {
        this.ingredientList = ingredientList;
        this.title = title;
        this.context = context;
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
            String sourceString =  ingredientList.get(position).getName();
            holder.text.setText(Html.fromHtml(sourceString));
            Typeface face = Typeface.createFromAsset(context.getResources().getAssets(),
                    "font/proximaextrabold.ttf");
            holder.text.setTypeface(face);
            holder.text.setTextColor(Color.BLACK);
           // holder.text.setBackgroundColor(context.getResources().getColor(R.color.white));


           // holder.tvQuantity.setText("  "/*categories.get(position).getMainquantity()!=null ?categories.get(position).getMainquantity(): " "*/);

        }else{
            k++;
            Typeface face = Typeface.createFromAsset(context.getResources().getAssets(),
            "font/poppinsmedium.ttf");
            holder.text.setTypeface(face);
            Spanny spanny=new Spanny();
            spanny.append("",new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)),new StyleSpan(Typeface.BOLD)
            ).append(ingredientList.get(position).getMainquantity());
            holder.text.setText( spanny);
            holder.tvQuantity.setText(ingredientList.get(position).getSubquantity() );
        }
/*

  if(categories.get(position).getIsHeader()==1){
            k=0;
            String sourceString = "* " + categories.get(position).getName()!=null ? categories.get(position).getName():" " + " *";
            holder.text.setText(Html.fromHtml(sourceString));
            holder.text.setTypeface(null, Typeface.BOLD);
            holder.text.setTextColor(Color.BLACK);
            holder.text.setBackgroundColor(context.getResources().getColor(R.color.white));


            holder.tvQuantity.setText("  "*/
/*categories.get(position).getMainquantity()!=null ?categories.get(position).getMainquantity(): " "*//*
);

        }else{
            k++;
            Spanny spanny=new Spanny();
            spanny.append(k+".  ",new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccentPink)),new StyleSpan(Typeface.BOLD)
            ).append(categories.get(position).getName() !=null ? categories.get(position).getName() : " ");
            holder.text.setText( spanny);
            holder.tvQuantity.setText(categories.get(position).getSubquantity() !=null ?categories.get(position).getSubquantity() :" sasasa");
        }

*/


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