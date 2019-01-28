package structure.com.foodportal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.Itemlistobject;


/**
 * Created by syedghazanfar on 1/3/2018.
 */


public class ImagesRecylerAdapter extends RecyclerView.Adapter<ImagesRecylerAdapter.ViewHolder> {
    private List<Itemlistobject> itemList;
    private MainActivity context;

    public ImagesRecylerClick imagesRecylerClick;

    public interface ImagesRecylerClick {

        void OnClickDeleteImage(View view, int pos, Itemlistobject itemlistobject);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView1;
        ImageView imageView, deleteImage;
        FrameLayout framelayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            deleteImage = (ImageView) itemView.findViewById(R.id.deleteimage);
            framelayout = (FrameLayout) itemView.findViewById(R.id.frameforimages);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagesRecylerClick.OnClickDeleteImage(view,getAdapterPosition(),itemList.get(getAdapterPosition()));

                }
            });
        }
    }


    public void clear() {
        itemList.clear();
    }

    public ImagesRecylerAdapter(MainActivity context, List<Itemlistobject> itemList, ImagesRecylerClick imagesRecylerClick) {


        this.itemList = itemList;
        this.imagesRecylerClick = imagesRecylerClick;
        this.context = context;
    }

    @Override
    public ImagesRecylerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_images, parent, false);
// set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(ImagesRecylerAdapter.ViewHolder holder, final int position) {
/*
        holder.textView.setText(itemList.get(position).getName());
        holder.textView1.setText(itemList.get(position).getDesc());
*/
        UIHelper.setImagewithGlide(context, holder.imageView, itemList.get(position).getPhoto());
        //  holder.imageView.setImageURI(Uri.parse(itemList.get(position).getPhoto()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Utils.showToast(context, context.getString(R.string.implementlater));
                // Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                // context.initFragments(new SuggestionsFragment(true));
                // Utils.showToast(context,context.getString(R.string.implementlater)+" Friend No "+position+" Click");


                // clickListener.itemClick(view,itemList.get(position));

//               OnClick.WhenImageFriendOnClick(view, position);
            }
        });


       /* holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) clickListener.itemClick(v, position);
            }
        });
        holder.imageView.setTag(holder);
   */
    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void addAll(List<Itemlistobject> itemList){
        this.itemList.clear();
        this.itemList.addAll(itemList);
    }


}