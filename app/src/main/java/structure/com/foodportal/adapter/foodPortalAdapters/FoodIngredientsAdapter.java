package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.models.foodModels.Ingredient;

public class FoodIngredientsAdapter extends RecyclerView.Adapter<FoodIngredientsAdapter.PlanetViewHolder> {

    ArrayList<Ingredient> ingredientList;

    public FoodIngredientsAdapter(ArrayList<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
    }

    @Override
    public FoodIngredientsAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients,parent,false);
        PlanetViewHolder viewHolder=new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodIngredientsAdapter.PlanetViewHolder holder, int position) {
      //  holder.image.setImageResource(R.drawable.planetimage);


            if(ingredientList.get(position).getSub_ingredients().size()>0){
                //  holder.text.setText(ingredientList.get(position).getTag_en());
                for(int i =0; i<ingredientList.get(position).getSub_ingredients().size();i++){

                    holder.text.setText("   "+(i+1) +" "+ingredientList.get(position).getSub_ingredients().get(i).getIngredient_en()
                            +" "+ingredientList.get(position).getSub_ingredients().get(i).getQuantity()+" "+ingredientList.get(position).getSub_ingredients().get(i).getQuantity_type()
                    );



        }





        }


    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            text= (TextView) itemView.findViewById(R.id.tvingredients);
        }
    }
}