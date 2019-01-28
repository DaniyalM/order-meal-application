package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.VH> {

    private final MainActivity mainActivity;
    private final LayoutInflater inflater;

    public OrderSummaryAdapter(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_cart_product, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {


    }

    class VH extends RecyclerView.ViewHolder {

        ImageView ivMinus, ivAdd;
        TextView tvQuantity, tvName, tvAmount;
        int quan = 0;

        public VH(View itemView) {
            super(itemView);

//            tvAmount = itemView.findViewById(R.id.tvAmount);
//            ivAdd = itemView.findViewById(R.id.ivAdd);
//            tvQuantity = itemView.findViewById(R.id.tvQuantity);
//            tvName = itemView.findViewById(R.id.tvName);
//            ivMinus = itemView.findViewById(R.id.ivMinus);


        }
    }
}
