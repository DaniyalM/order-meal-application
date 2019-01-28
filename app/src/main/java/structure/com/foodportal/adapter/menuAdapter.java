package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;

/**
 * Created by Addi.
 */
public class menuAdapter extends RecyclerView.Adapter<menuAdapter.VH> {
    private final LayoutInflater inflater;
    private final ArrayList<String> list;
    private final MainActivity mainActivity;

    public menuAdapter(MainActivity mainActivity, ArrayList<String> list) {
        inflater = LayoutInflater.from(mainActivity);
        this.list = list;
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_bestsellers_item, parent, false);
        menuAdapter.VH myViewHolder = new menuAdapter.VH(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        UIHelper.setImagewithGlide(mainActivity,holder.ivImage,list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName,tvAmount;

        public VH(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
