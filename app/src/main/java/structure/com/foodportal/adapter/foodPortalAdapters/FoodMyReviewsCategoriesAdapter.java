package structure.com.foodportal.adapter.foodPortalAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.helper.BasePreferenceHelper;
import structure.com.foodportal.interfaces.foodInterfaces.FoodHomeListner;
import structure.com.foodportal.interfaces.foodInterfaces.FoodMyReviewsListener;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.Recipe;

import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static structure.com.foodportal.helper.AppConstant.Language.ENGLISH;
import static structure.com.foodportal.helper.AppConstant.Language.URDU;

public class FoodMyReviewsCategoriesAdapter extends RecyclerView.Adapter<FoodMyReviewsCategoriesAdapter.PlanetViewHolder> {

    ArrayList<CategorySlider> categories;
    int reviewPosition;
    Context context;
    FoodMyReviewsListener foodMyReviewsListener;

    public FoodMyReviewsCategoriesAdapter(ArrayList<CategorySlider> categories, int reviewPosition, Context context,
                                          FoodMyReviewsListener foodMyReviewsListener) {
        this.reviewPosition = reviewPosition;
        this.categories = categories;
        this.context = context;
        this.foodMyReviewsListener = foodMyReviewsListener;
    }

    @Override
    public FoodMyReviewsCategoriesAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip_new, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FoodMyReviewsCategoriesAdapter.PlanetViewHolder holder, int position) {
        holder.chip.setText(getTitleBySelectedLanguage(position, holder.chip));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodMyReviewsListener.onCategoryClick(reviewPosition, position);
            }
        });

//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.mFrameLayoutChip.getLayoutParams();
//
//        if (preferenceHelper != null) {
//            switch (preferenceHelper.getSelectedLanguage()) {
//                case ENGLISH:
//                default:
//                    params.gravity = START;
//                    break;
//
//                case URDU:
//                    params.gravity = END;
//                    break;
//            }
//            holder.mFrameLayoutChip.setLayoutParams(params);
//        }
    }

    private BasePreferenceHelper preferenceHelper;

    private String getTitleBySelectedLanguage(int position, TextView textView) {
        String title = categories.get(position).getCategory_title_en();
        textView.setTextSize(12);

        if (preferenceHelper != null) {
            if (preferenceHelper.getSelectedLanguage() == ENGLISH) {
                title = categories.get(position).getCategory_title_en();
                textView.setTextSize(12);
            } else if (preferenceHelper.getSelectedLanguage() == URDU) {
                title = categories.get(position).getCategory_title_ur();
                textView.setTextSize(14);
            }
        }
        return title;
    }

    public void setPreferenceHelper(BasePreferenceHelper preferenceHelper) {
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout mFrameLayoutChip;
        private TextView chip;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            mFrameLayoutChip = (FrameLayout) itemView.findViewById(R.id.frameLayoutChip);
            chip = (TextView) itemView.findViewById(R.id.chip);
        }
    }
}