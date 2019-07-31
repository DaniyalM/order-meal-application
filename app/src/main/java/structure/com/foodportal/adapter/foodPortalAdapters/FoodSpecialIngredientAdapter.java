package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.SpecialStepListner;
import structure.com.foodportal.models.foodModels.CustomIngredient;
import structure.com.foodportal.models.foodModels.Ingredient;
import structure.com.foodportal.models.foodModels.SpecialIngedient;
import structure.com.foodportal.models.foodModels.Step;

public class FoodSpecialIngredientAdapter extends RecyclerView.Adapter<FoodSpecialIngredientAdapter.PlanetViewHolder> {

    ArrayList<SpecialIngedient> ingredientList;
     public  int lastCheckedPosition = -1;

    MainActivity context;
    SpecialStepListner specialStepListener;
    public FoodSpecialIngredientAdapter(ArrayList<SpecialIngedient> ingredientList, MainActivity context,SpecialStepListner specialStepListener) {
        this.ingredientList = ingredientList;
        this.specialStepListener = specialStepListener;
        this.context = context;
    }

    @Override
    public FoodSpecialIngredientAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkbox, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodSpecialIngredientAdapter.PlanetViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);


        holder.masalaname.setText(ingredientList.get(position).getTitle_en());
        UIHelper.setImagewithGlide(context,holder.masalaimage,ingredientList.get(position).getImage_path());
        holder.radioButton.setChecked(position == lastCheckedPosition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioButton.setChecked(position == lastCheckedPosition);
              //  specialStepListener.specialClick(categories.get(position).getIngredient_en());


            }
        });




    }





    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public  class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView masalaname;
        ImageView masalaimage;
        LinearLayout selectedposition;
        RadioButton radioButton;

        public PlanetViewHolder(View itemView) {
            super(itemView);

            selectedposition = (LinearLayout) itemView.findViewById(R.id.selectedposition);
            masalaname = (TextView) itemView.findViewById(R.id.masalaname);
            radioButton = (RadioButton) itemView.findViewById(R.id.radio);
            masalaimage = (ImageView) itemView.findViewById(R.id.masalaimage);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastCheckedPosition = getAdapterPosition();
                    specialStepListener.specialClick(ingredientList.get(lastCheckedPosition).getIngredient_en());
                    notifyDataSetChanged();

                }
            });

        }
    }
}