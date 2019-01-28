package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.ResturantClickInterface;

/**
 * Created by Addi.
 */
public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.VH> {

    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    private final ArrayList<String> imgReset;
    ResturantClickInterface onResturantClick;

    public NearbyAdapter(MainActivity mainActivity, ArrayList<String> imgRest, ResturantClickInterface onResturantClick) {

        this.mainActivity = mainActivity;
        this.imgReset = imgRest;
        inflater = LayoutInflater.from(mainActivity);
        this.onResturantClick = onResturantClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_nearby, parent, false);
        VH myViewHolder = new VH(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        UIHelper.setImagewithGlide(mainActivity, holder.ivnearby, imgReset.get(position));

        holder.mainitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResturantClick.onResturant_Click();
            }
        });


    }

    @Override
    public int getItemCount() {
        return imgReset.size();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView ivnearby;
        TextView tvName;
        TextView tvCount;
        TextView tvStatus;
        TextView tvCategory;
        TextView tvLocation;
        TextView tvDistance;
        TextView tvTime;
        LinearLayout mainitem;
        MaterialRatingBar mrRating;


        public VH(View itemView) {
            super(itemView);

            ivnearby = itemView.findViewById(R.id.ivnearby);
            tvName = itemView.findViewById(R.id.tvName);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvTime = itemView.findViewById(R.id.tvTime);
            mrRating = itemView.findViewById(R.id.mrRating);
            mainitem = itemView.findViewById(R.id.mainitem);
        }
    }
}
