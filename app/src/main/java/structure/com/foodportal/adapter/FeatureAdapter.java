package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.ResturantClickInterface;

/**
 * Created by Addi.
 */
public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.VH> {
    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    private final ArrayList<String> list;
    ResturantClickInterface resturantClickInterface;

    public FeatureAdapter(MainActivity mainActivity, ArrayList<String> list,ResturantClickInterface resturantClickInterface) {

        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
        this.list = list;
        this.resturantClickInterface=resturantClickInterface;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_feature, parent, false);
        VH myViewHolder = new VH(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        UIHelper.setImagewithGlide(mainActivity, holder.ivFeature, list.get(position));
        holder.ivFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resturantClickInterface.onResturant_Click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView ivFeature;

        public VH(View itemView) {
            super(itemView);
            ivFeature = itemView.findViewById(R.id.ivFeature);
        }
    }

}
