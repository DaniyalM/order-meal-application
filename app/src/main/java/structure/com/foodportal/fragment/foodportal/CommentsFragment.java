package structure.com.foodportal.fragment.foodportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Comment;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodCommentsAdapter;
import structure.com.foodportal.adapter.foodPortalAdapters.FoodSubCategory;
import structure.com.foodportal.databinding.FragmenCommentsBinding;
import structure.com.foodportal.databinding.FragmentRecipeBinding;
import structure.com.foodportal.fragment.BaseFragment;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.DataSyncEvent;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.LocalDataHelper;
import structure.com.foodportal.helper.NetworkUtils;
import structure.com.foodportal.helper.SimpleItemTouchHelperCallback;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.helper.Utils;
import structure.com.foodportal.interfaces.foodInterfaces.CommentClickListner;
import structure.com.foodportal.interfaces.foodInterfaces.SubCategoryListner;
import structure.com.foodportal.models.foodModels.CategorySlider;
import structure.com.foodportal.models.foodModels.CategorySliderWrapper;
import structure.com.foodportal.models.foodModels.Comments;
import structure.com.foodportal.models.foodModels.CommentsWrapper;
import structure.com.foodportal.models.foodModels.FoodDetailModel;
import structure.com.foodportal.models.foodModels.FoodDetailModelWrapper;
import structure.com.foodportal.models.foodModels.RecipeWrapper;
import structure.com.foodportal.models.foodModels.SavedRecipe;

public class CommentsFragment extends BaseFragment implements CommentClickListner, View.OnClickListener {


    FoodSubCategory foodCategoryAdapter;

    CategorySlider categorySlider;
    FoodDetailModel savedRecipe;
    ArrayList<CategorySlider> categorySliders;
    LinearLayoutManager linearLayoutManagerComment;
    FoodDetailModelWrapper foodDetailModel;
    ArrayList<Comments> comments;
    FragmenCommentsBinding binding;
    FoodCommentsAdapter foodCommentsAdapter;
   public boolean sepcial;
    public void setArrayComments(FoodDetailModelWrapper foodDetailModel,Boolean sepcial)
    {      this.sepcial=sepcial;
        this.foodDetailModel = foodDetailModel;


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragmen_comments, container, false);
        mainActivity.hideBottombar();
        setListners();


        return binding.getRoot();
    }


    void setListners() {
        binding.send.setOnClickListener(this);
        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);
        binding.rvCommentsSection.setItemAnimator(defaultItemAnimator);
        linearLayoutManagerComment = new LinearLayoutManager(mainActivity, OrientationHelper.VERTICAL, false);
        binding.rvCommentsSection.setLayoutManager(linearLayoutManagerComment);
        comments = new ArrayList<>();
        setdata(foodDetailModel);

    }

    private void setdata(FoodDetailModelWrapper foodDetailModel) {


        foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, false, true);
        binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
      /*  ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(foodCommentsAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(binding.rvCommentsSection);*/
        int resId = R.anim.layout_bottom_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mainActivity, resId);
        binding.rvCommentsSection.setLayoutAnimation(animation);
        if (foodDetailModel.getAllReviews()!=null&& foodDetailModel.getAllReviews().size() > 0) {
            foodCommentsAdapter.addAll(foodDetailModel.getAllReviews());

        } else {


        }

        binding.rvCommentsSection.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        binding.rvCommentsSection.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < binding.rvCommentsSection.getChildCount(); i++) {
                            View v = binding.rvCommentsSection.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });


    }

    Titlebar titlebar;

    @Override
    protected void setTitle(Titlebar titlebar) {

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showBackButton(mainActivity);
        this.titlebar = titlebar;


    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().post(new DataSyncEvent(0));
        super.onDestroyView();

        titlebar.showTitlebar();
        titlebar.setTitle("");
        titlebar.showBackButton(mainActivity);

    }

    CommentsWrapper foodDetailModelWrapper;
    @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case AppConstant.FOODPORTAL_FOOD_DETAILS.SUB_CATEGORY_RECIPE:


                break;


            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW:
                binding.rvCommentsSection.smoothScrollToPosition(replyposition);
                allreview(foodDetailModel.getData());

                break;
            case AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_ALL_REVIEW:

                foodCommentsAdapter=null;
                comments.clear();
                foodCommentsAdapter = new FoodCommentsAdapter(comments, mainActivity, this, false, true);
                binding.rvCommentsSection.setAdapter(foodCommentsAdapter);
                 foodDetailModelWrapper = (CommentsWrapper) result;
                foodDetailModel.setAllReviews(null);
                 foodDetailModel.setAllReviews(foodDetailModelWrapper.getData());
                foodCommentsAdapter.addAll(foodDetailModelWrapper.getData());


                break;


        }

    }


    @Override
    public void onReplyClick(int positon) {

        createDialog(foodDetailModel, positon);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.send:

                if (binding.etCommentsm.getText().toString().trim().equalsIgnoreCase("")) {

                    Toast.makeText(registrationActivity, "Please write somthing", Toast.LENGTH_SHORT).show();
                } else {
                    Utils.hideKeyboard(getView(), mainActivity);
                    sendreview(foodDetailModel.getData());

                }


                break;


        }
    }

    public void sendreview(FoodDetailModel foodDetailModel) {


        if(sepcial){
        serviceHelper.enqueueCall(webService.sendreview(preferenceHelper.getUser().getId(),
                "special_recipe",
                foodDetailModel.getFeature_type_id(),
                foodDetailModel.getId(), binding.etCommentsm.getText().toString(),
                0), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW);

    }   else{

        serviceHelper.enqueueCall(webService.sendreview(preferenceHelper.getUser().getId(),
                "story",
                foodDetailModel.getFeature_type_id(),
                foodDetailModel.getId(), binding.etCommentsm.getText().toString(),
                0), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW);
    }



        binding.etCommentsm.setText("");
    }

    int replyposition = 0;

    public void sendreply(FoodDetailModelWrapper foodDetailModel, int position,String editText) {
        replyposition = position;
        if(sepcial){
            serviceHelper.enqueueCall(webService.sendreply(preferenceHelper.getUser().getId(),
                    "special_recipe",
                    foodDetailModel.getData().getFeature_type_id(),
                    foodDetailModel.getAllReviews().get(position).getStory_id(), editText,
                    foodDetailModel.getAllReviews().get(position).getId()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW);

        }else {
            serviceHelper.enqueueCall(webService.sendreply(preferenceHelper.getUser().getId(),
                    "story",
                    foodDetailModel.getData().getFeature_type_id(),
                    foodDetailModel.getAllReviews().get(position).getStory_id(), editText,
                    foodDetailModel.getAllReviews().get(position).getId()), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_SEND_REVIEW);
        }

    }

    public void allreview(FoodDetailModel foodDetailModel) {
        if(sepcial){
        serviceHelper.enqueueCall(webService.getAlReviews(String.valueOf(foodDetailModel.getId()),"special_recipe"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_ALL_REVIEW);
    }else{
            serviceHelper.enqueueCall(webService.getAlReviews(String.valueOf(foodDetailModel.getId()),"story"), AppConstant.FOODPORTAL_FOOD_DETAILS.FOOD_ALL_REVIEW);

        }
    }

    void createDialog(FoodDetailModelWrapper foodDetailModelWrapper, int pos) {


        LayoutInflater li = LayoutInflater.from(mainActivity);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mainActivity,R.style.PauseDialog);
        promptsView.setBackgroundColor(mainActivity.getResources().getColor(R.color.colorAccent));

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final ImageView personimage = (ImageView) promptsView
                .findViewById(R.id.replyingimage);
        final TextView personname = (TextView) promptsView
                .findViewById(R.id.replyingname);
        final TextView persontext = (TextView) promptsView
                .findViewById(R.id.replyingetxt);

        switch (foodDetailModelWrapper.getAllReviews().get(pos).getUser().getAcct_type()) {

            case 1:
                UIHelper.setImageWithGlide(mainActivity, personimage, AppConstant.BASE_URL_IMAGE +foodDetailModelWrapper.getAllReviews().get(pos).getUser().getProfile_picture());
                break;
            case 2://gmail
                UIHelper.setImageWithGlide(mainActivity,personimage, AppConstant.BASE_URL_IMAGE + foodDetailModelWrapper.getAllReviews().get(pos).getUser().getProfile_picture());
                break;
            case 3://facebook
                UIHelper.setImageWithGlide(mainActivity, personimage, "https://graph.facebook.com/" + foodDetailModelWrapper.getAllReviews().get(pos).getUser().getProfile_picture() + "/picture?type=large");
                break;

        }


        personname.setText(foodDetailModelWrapper.getAllReviews().get(pos).getUser().getName_en());
        persontext.setText(foodDetailModelWrapper.getAllReviews().get(pos).getReviews());
        // set dialog message

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text

                                if (userInput.getText().toString().trim().equalsIgnoreCase("")) {
                                    Toast.makeText(mainActivity, "Please write something", Toast.LENGTH_SHORT).show();

                                } else {
                                    dialog.dismiss();
                                    sendreply(foodDetailModelWrapper, pos,userInput.getText().toString());
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }


}