package structure.com.foodportal.adapter;

import android.content.DialogInterface;
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
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DialogFactory;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.CartListner;
import structure.com.foodportal.models.CartModule.CartProductMainClass;

/**
 * Created by Addi.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    ArrayList<CartProductMainClass> cartProducts;
    CartListner cartListner;
    String number;

    public CartAdapter(MainActivity mainActivity, ArrayList<CartProductMainClass> cartProducts, CartListner cartListner ) {

        this.mainActivity = mainActivity;
        this.cartProducts = cartProducts;
        this.cartListner = cartListner;
        inflater = LayoutInflater.from(mainActivity);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cart_bag, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {


        return this.cartProducts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        if (cartProducts.get(position).getRent_type_id() != null && !cartProducts.get(position).getRent_type_id().equals("0")) {
            holder.tvproducttitle.setText(cartProducts.get(position).getCart_product().getTitle());
            holder.tvproductdescription.setText(cartProducts.get(position).getCart_product().getProductCategory().getTitle());
            holder.tvproductlocation.setText(cartProducts.get(position).getCart_product().getLocation());
            holder.tvproductprice.setText("AED " + cartProducts.get(position).getCart_product().getAmount());
            holder.tvSelectDays.setVisibility(View.VISIBLE);
            fillDropDown(Integer.valueOf(cartProducts.get(position).getRent_type_id()), holder);
        } else {
            holder.tvproducttitle.setText(cartProducts.get(position).getCart_product().getTitle());
            holder.tvproductdescription.setText(cartProducts.get(position).getCart_product().getModelNumber());
            holder.tvproductlocation.setText(cartProducts.get(position).getCart_product().getLocation());
            holder.tvproductprice.setText("AED " + cartProducts.get(position).getCart_product().getAmount());
            //holder.tvproductper.setText(cartProducts.get(position).getCart_product().getRentType());
        }


        if (cartProducts.get(position).getCart_product().getProductOn() == AppConstant.ProductOn.SALE) {
            holder.tvproductper.setText(mainActivity.getResources().getString(R.string.for_sale));
        } else if (cartProducts.get(position).getCart_product().getProductOn() == AppConstant.ProductOn.RENT) {

            holder.tvproductper.setText(mainActivity.getResources().getString(R.string.for_rent));

        }

        if (cartProducts.get(position).getCart_product().getProduct_images().size() > 0) {
            UIHelper.setImagewithGlide(mainActivity, holder.ivProductImage, cartProducts.get(position).getCart_product().getProduct_images().get(0).getProductImage());
        }


        holder.ivRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListner.removefromcart(cartProducts.get(position), position);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListner.onClickItem(cartProducts.get(position), position);

            }
        });

        holder.tvSelectDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFactory.listDialog(mainActivity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //    rentTypeID = renttypes.get(which).getId();


                        holder.tvSelectDays.setText(types.get(which));
                        number = types.get(which).replaceAll("[^0-9]", "");
                        cartListner.onDialogClick(number);

                    }
                }, mainActivity.getResources().getString(R.string.select_rent_type), types);

            }
        });

    }

    private void fillDropDown(Integer rentTypeId, CartAdapter.VH holder) {
        switch (rentTypeId) {
            case 1:
                setloop(23, "Hour");
                holder.tvSelectDays.setText("1 Hour");
                break;
            case 2:
                setloop(30, "Day");
                holder.tvSelectDays.setText("1 Day");
                break;
            case 3:
                setloop(4, "Week");
                holder.tvSelectDays.setText("1 Week");
                break;
            case 4:
                setloop(12, "Month");
                holder.tvSelectDays.setText("1 Month");
                break;

        }


    }

    ArrayList<String> types;

    private void setloop(int i, String text) {
        types = new ArrayList<>();
        for (int j = 1; j <= i; j++) {

            if(j==1){
                types.add(j + " " + text);
            }else{
                types.add(j + " " + text+"s");

            }



        }


    }

    class VH extends RecyclerView.ViewHolder {

        ImageView ivProductImage, ivRemoveItem;
        TextView tvproducttitle, tvproductdescription, tvproductlocation, tvproductprice, tvproductper, tvSelectDays;
        int quan = 0;

        public VH(View itemView) {
            super(itemView);

            tvproducttitle = itemView.findViewById(R.id.tvproducttitle);
            tvproductdescription = itemView.findViewById(R.id.tvproductdescription);
            tvproductlocation = itemView.findViewById(R.id.tvproductlocation);
            tvproductprice = itemView.findViewById(R.id.tvproductprice);
            tvproductper = itemView.findViewById(R.id.tvproductper);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            ivRemoveItem = itemView.findViewById(R.id.ivRemoveItem);
            tvSelectDays = itemView.findViewById(R.id.tvSelectDays);


        }
    }
}
