package structure.com.foodportal.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.interfaces.CategoryInterface;
import structure.com.foodportal.interfaces.RecyclerItemClickListener;
import structure.com.foodportal.models.AllCategory;


/**
 * Created by syedghazanfar on 1/3/2018.
 */


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    public CategoryInterface categoryInterface;
    private ArrayList<AllCategory> category;
    private MainActivity context;
    private int lastPosition = -1;
    private RecyclerItemClickListener itemClickListener;

    public SubCategoryAdapter(MainActivity context, ArrayList<AllCategory> category, CategoryInterface categoryInterface) {


        this.category = category;
        this.categoryInterface = categoryInterface;
        this.context = context;
    }

    public void setItemClickListener(RecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void clear() {
        category.clear();
    }

    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(SubCategoryAdapter.ViewHolder holder, final int position) {

        holder.tvCategory.setText(category.get(position).getTitle());

        //  setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return this.category.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;


        public ViewHolder(final View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvCategory.setOnClickListener(view ->
                    categoryInterface.onCategoryClicked(category, getAdapterPosition()));
        }
    }






}