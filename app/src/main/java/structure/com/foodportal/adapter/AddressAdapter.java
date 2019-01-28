package structure.com.foodportal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.VH> {
    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    private final ArrayList<String> list;
    Addaddresslistner addaddresslistner;
    private int lastCheckedPosition = -1;
    public AddressAdapter(MainActivity mainActivity, ArrayList<String> list, Addaddresslistner addaddresslistner) {

        this.mainActivity = mainActivity;
        inflater = LayoutInflater.from(mainActivity);
        this.list = list;
        this.addaddresslistner=addaddresslistner;


    }

    @NonNull
    @Override
    public AddressAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address, parent, false);
        AddressAdapter.VH myViewHolder = new AddressAdapter.VH(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.VH holder, int position) {



        holder.addressRadio.setChecked(position == lastCheckedPosition);


        holder.ivDelete.setOnClickListener(view -> addaddresslistner.onClickDelete(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        ImageView ivDelete;
        TextView tvAddresstitle,tvAddress,tvCountryCity,tvNumber;
        RadioButton addressRadio;

        public VH(View itemView) {
            super(itemView);
            ivDelete = itemView.findViewById(R.id.ivDeleteAddress);
            tvAddresstitle = itemView.findViewById(R.id.tvAddressTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvCountryCity = itemView.findViewById(R.id.tvCountryCity);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            addressRadio = itemView.findViewById(R.id.rdAddress);



            addressRadio.setOnClickListener(view -> {
                lastCheckedPosition = getAdapterPosition();
                notifyDataSetChanged();
            });

        }
    }

    public  interface Addaddresslistner{


    void onClickDelete(int position);

    }
}