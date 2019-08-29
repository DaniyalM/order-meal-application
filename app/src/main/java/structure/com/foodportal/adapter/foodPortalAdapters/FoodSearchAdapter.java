package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.helper.Spanny;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodDetailListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodSearchListner;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.Step;

import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;

public class FoodSearchAdapter extends RecyclerView.Adapter<FoodSearchAdapter.FoodSearchViewHolder> {

    ArrayList<FoodDetailModel> steps;
    FoodSearchListner foodDetailListner;
    Context context;
    BasePreferenceHelper mPreferenceHelper;

    public FoodSearchAdapter(ArrayList<FoodDetailModel> steps, Context context, FoodSearchListner foodDetailListner) {
        this.steps = steps;
        this.context = context;
        this.foodDetailListner = foodDetailListner;
    }

    @Override
    public FoodSearchAdapter.FoodSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        FoodSearchViewHolder viewHolder = new FoodSearchViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodSearchAdapter.FoodSearchViewHolder holder, int position) {
        //  holder.image.setImageResource(R.drawable.planetimage);
        Spanny spanny ;
        spanny = new Spanny().append(mPreferenceHelper.getSelectedLanguage() == ENGLISH ? steps.get(position).getTitle_en() : steps.get(position).getTitle_ur());
        holder.text.setText(spanny);
        holder.textnum.setText("" + (position + 1));
        UIHelper.setImageWithGlide(context,holder.imageView,steps.get(position).getGallery().getPhotos().get(0).getThumb_path());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodDetailListner.clickSearch(steps.get(position),position);
            }
        });

    }

    public void addAll(ArrayList<FoodDetailModel> foodDetailModels){

        this.steps.clear();
        this.steps.addAll(foodDetailModels);
        notifyDataSetChanged();


    }

    public void setPreferenceHelper(BasePreferenceHelper preferenceHelper) {
        mPreferenceHelper = preferenceHelper;
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class FoodSearchViewHolder extends RecyclerView.ViewHolder {

        protected TextView text, textnum;
        ImageView imageView;

        public FoodSearchViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tvsteps);
            textnum = (TextView) itemView.findViewById(R.id.tvStepsnum);
            imageView = (ImageView) itemView.findViewById(R.id.recipeimage);
        }
    }
}