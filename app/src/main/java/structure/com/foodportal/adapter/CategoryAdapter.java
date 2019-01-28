package structure.com.foodportal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.interfaces.CategoryInterface;
import structure.com.foodportal.models.AllCategory;


/**
 * Created by syedghazanfar on 1/3/2018.
 */


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<AllCategory> category;
    private MainActivity context;
    int lastPosition =-1;
    public CategoryInterface categoryInterface;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;


        public ViewHolder(final View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvCategory.setOnClickListener(view -> categoryInterface.onCategoryClicked(category,getAdapterPosition()));
        }
    }


    public void clear() {
        category.clear();
    }

    public CategoryAdapter(MainActivity context, ArrayList<AllCategory> category, CategoryInterface categoryInterface) {


        this.category = category;
        this.categoryInterface = categoryInterface;
        this.context = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {

        holder.tvCategory.setText(category.get(position).getTitle());
       // setAnimation(holder.itemView,position);
    }


    @Override
    public int getItemCount() {
        return this.category.size();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}