package structure.com.foodportal.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import structure.com.foodportal.R;
import structure.com.foodportal.adapter.ImagesRecylerAdapter;
import structure.com.foodportal.databinding.FragmentAddproductBinding;
import structure.com.foodportal.global.WebServiceConstants;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.JsonHelpers;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.MediaTypePicker;
import structure.com.foodportal.models.AddProductModel;
import structure.com.foodportal.models.AllCategory;
import structure.com.foodportal.models.Brand;
import structure.com.foodportal.models.Category;
import structure.com.foodportal.models.Itemlistobject;
import structure.com.foodportal.models.MakeModel;

public class AddProductFragment extends BaseFragment implements View.OnClickListener, MediaTypePicker, ImagesRecylerAdapter.ImagesRecylerClick, UIHelper.Utilinterface {
    FragmentAddproductBinding binding;
    ImagesRecylerAdapter adapter;
    ArrayList<Itemlistobject> selectedImageList;
    ArrayList<Brand> brands;
    ArrayList<AllCategory> category;
    ArrayList<AllCategory> subCategories;
    ArrayList<MakeModel> models;
    ArrayList<String> mImages;
    ArrayList<String> deletedImagesIds;
    int pos;
    Unbinder unbinder;
    @BindView(R.id.rbRadio)
    RadioButton rbRadio;
    @BindView(R.id.rbSale)
    RadioButton rbSale;
    @BindView(R.id.radioGroupProductOn)
    RadioGroup radioGroupProductOn;
    @BindView(R.id.etCategory)
    Spinner etCategory;
    @BindView(R.id.etBrand)
    Spinner etBrand;
    @BindView(R.id.etSubCategory)
    Spinner etSubCategory;
    @BindView(R.id.etModels)
    Spinner etModels;
    private AddProductModel.Builder productDetailsBuilder;
    Titlebar titlebar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addproduct, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        deletedImagesIds = new ArrayList<>();
        productDetailsBuilder = new AddProductModel.Builder();

        setListners();
        initImagesAdapter();
        initServices();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Mark By Default
        ProductCreation.getInstance().setProductOn(AppConstant.ProductOn.RENT);

        setData();
    }

    @Override
    public void onDestroyView() {
        titlebar.showTitlebar();
        titlebar.setTitle(mainActivity.getResources().getString(R.string.homeTitle));
//        titlebar.showNotification(0);
        titlebar.showCart(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.prefHelper.getLoginStatus()) {
                    mainActivity.replaceFragment(new CartFragment(), true, true);
                } else {

                    UIHelper.showToast(mainActivity, mainActivity.getString(R.string.please_login_viewcart));

                }
            }
        });
        titlebar.showMenuButton(mainActivity);
        mainActivity.showBottombar();
        super.onDestroyView();
    }

    @Override
    public void ResponseSuccess(Object result, String tag) {
        switch (tag) {
            case WebServiceConstants.GET_ALL_CATEGORIES:
                Category category = (Category) JsonHelpers.convertToModelClass(result, Category.class);
                mainActivity.setCategories(category);
                initCategorySpinner(category.getCategories());
                break;
            case WebServiceConstants.GET_ALL_SUBCATEGORIES:
                initSubCategories((ArrayList<AllCategory>) result);
                break;

            case WebServiceConstants.GET_ALL_BRANDS:
                initBrandsSpinner((ArrayList<Brand>) result);
                break;

            case WebServiceConstants.GET_ALL_MODELS:
                initModelsSpinner((ArrayList<MakeModel>) result);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                if (isValidated()) {
                    ProductCreation.getInstance().setDeletedImagesIds(deletedImagesIds);


                    productDetailsBuilder
                            .withCategoryId(0+category.get(etCategory.getSelectedItemPosition()).getId())
                            .withSubCatgories(0+subCategories.get(etSubCategory.getSelectedItemPosition()).getId())
                            .withBrandId(brands==null ? 0 : brands.get(etBrand.getSelectedItemPosition()).getId())
                            .withModels(models==null ? 0 : models.get(etModels.getSelectedItemPosition()).getId())
                            .withProductOn(0+ProductCreation.getInstance().getProductOn())
                            .withTitle(binding.etTitle.getText().toString())
                            .withImages(selectedImageList);
                    PlanFragment fragment = new PlanFragment();
                    fragment.setProductDetailsBuilder(productDetailsBuilder);
                    mainActivity.addFragment(fragment, true, true);
                }
//                else{
//                    mainActivity.addFragment(new MoreAboutProduct(), true, true);
//                }
                break;
            case R.id.addMore:
                if (adapter.getItemCount() < 5)
                    mainActivity.openFilePicker(AddProductFragment.this, 1, false, AppConstant.ProductPictureParameters.MIN_WINDOW_WIDTH, AppConstant.ProductPictureParameters.MIN_WINDOW_HEIGHT, AppConstant.ProductPictureParameters.ASPECT_RATIO_X, AppConstant.ProductPictureParameters.ASPECT_RATIO_Y);
                else
                    UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.max_images_selected_error));
                break;

        }
    }

    private boolean isValidated() {
        if (binding.etTitle.getText().toString().trim().length() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.titleError));
            return false;
        }

        if (etCategory.getSelectedItemPosition() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.categoryError));
            return false;
        }

        if (etSubCategory.getSelectedItemPosition() == 0) {
            UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.subCategoryError));
            return false;
        }

        if (category.get(etCategory.getSelectedItemPosition()).getId() != AppConstant.CategoriesIds.CLASSIFIED && category.get(etCategory.getSelectedItemPosition()).getId() != AppConstant.CategoriesIds.PROPERTIES) {

            if (etBrand.getSelectedItemPosition() == 0) {
                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.brandError));
                return false;
            }

//            if(models.size()>0){
//            if (etModels.getSelectedItemPosition() == 0) {
//                UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.modelError));
//                return false;
//            }
//            }


        }

        if (adapter.getItemCount() == 0) {
            UIHelper.showToast(mainActivity, getString(R.string.imagesError));
            return false;
        }

        return true;
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.resetView();
        titlebar.showTitlebar();
        if (ProductCreation.getInstance().isEditing()) {
            titlebar.setTitle(mainActivity.getResources().getString(R.string.editproduct));
        } else {
            titlebar.setTitle(mainActivity.getResources().getString(R.string.addproduct));
        }
        titlebar.showBackButton(mainActivity, true).setOnClickListener(v -> {
            mainActivity.setCategories(null);
            mainActivity.onBackPressed();
        });
    }

    @Override
    public void OnClickDeleteImage(View view, int position, Itemlistobject itemlistobject) {
        pos = position;
        UIHelper.alertDialog(mainActivity.getResources().getString(R.string.delete), mainActivity.getResources().getString(R.string.are_you_sure_you_want_to_delete_the_pic), false, this, mainActivity, mainActivity.getResources().getString(R.string.yes), getString(R.string.no));
    }

    @Override
    public void onPhotoClicked(ArrayList<File> file) {
        if (file.size() > 0) {
            getSelectedMedia(file);
        } else {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDocClicked(ArrayList<String> files) {

    }

    @Override
    public void dialogPositive_Click(DialogInterface dialog) {
        if (ProductCreation.getInstance().isEditing()) {
            if (!selectedImageList.get(pos).isNewImage()) {
                deletedImagesIds.add(selectedImageList.get(pos).getId());
            }
        }
        selectedImageList.remove(pos);
        adapter.notifyDataSetChanged();
    }

    private void initServices() {
        serviceHelper.enqueueCall(webService.getAllCategory(), WebServiceConstants.GET_ALL_CATEGORIES);
    }

    private void initBrandsSpinner(ArrayList<Brand> brandsList) {
        brands = new ArrayList<>(brandsList);
        ArrayList<String> categoriesTitle = new ArrayList<>();
//        categoriesTitle.add(mainActivity.getResources().getString(R.string.choose_a_brand));

        Brand none = new Brand();
        none.setId(0);
        none.setName(mainActivity.getResources().getString(R.string.choose_a_brand));
        brands.add(0, none);

        for (Brand item : brands) {
            categoriesTitle.add(item.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(mainActivity, R.layout.row_item_spinner, categoriesTitle);
        /*
        {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                return view;
            }

        };

        */


        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etBrand.setAdapter(categoryAdapter);

        //Edit Product
        if (ProductCreation.getInstance().isEditing()) {
            int brandId = 0, position = 0;
            if (ProductCreation.getInstance().getCurrentProduct() != null) {
                brandId = ProductCreation.getInstance().getCurrentProduct().getBrandId();
                for (int pos = 0; pos < brands.size(); pos++) {
                    if (brands.get(pos).getId() == brandId) {
                        position = pos;
                        break;
                    }
                }
                etBrand.setSelection(position);
            }
        }
    }

    private void initCategorySpinner(ArrayList<AllCategory> categories) {
        category = new ArrayList<>(categories);
        ArrayList<String> categoriesTitle = new ArrayList<>();
//        categoriesTitle.add(mainActivity.getResources().getString(R.string.select_category));

        AllCategory none = new AllCategory();
        none.setId(0);
        none.setTitle(mainActivity.getResources().getString(R.string.select_category));
        category.add(0, none);

        for (AllCategory item : category) {
            categoriesTitle.add(item.getTitle());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(mainActivity
                , R.layout.row_item_spinner, categoriesTitle) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }

        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etCategory.setAdapter(categoryAdapter);

        //Edit Product
        if (ProductCreation.getInstance().isEditing()) {
            int categoryID = 0, position = 0;
            if (ProductCreation.getInstance().getCurrentProduct() != null) {
                categoryID = ProductCreation.getInstance().getCurrentProduct().getCategoryId();
                for (int pos = 0; pos < category.size(); pos++) {
                    if (category.get(pos).getId() == categoryID) {
                        position = pos;
                        break;
                    }
                }
                etCategory.setSelection(position);
                /*
                if (category.size() > 1) {
                    getSubCategories(position);
                }
                */
            }
        } else {
            initSubCategories(new ArrayList<>());
        }

    }

    private void initSubCategories(ArrayList<AllCategory> result) {
        subCategories = new ArrayList<>(result);

        ArrayList<String> categoriesTitle = new ArrayList<>();
//        categoriesTitle.add(mainActivity.getResources().getString(R.string.select_category));

        AllCategory none = new AllCategory();
        none.setId(0);
        none.setTitle(mainActivity.getResources().getString(R.string.select_sub_category));
        subCategories.add(0, none);

        for (AllCategory item : subCategories) {
            categoriesTitle.add(item.getTitle());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(mainActivity
                , R.layout.row_item_spinner, categoriesTitle) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }

        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etSubCategory.setAdapter(categoryAdapter);

        if (ProductCreation.getInstance().isEditing()) {
            int subcategoryID = 0, position = 0;
            if (ProductCreation.getInstance().getCurrentProduct() != null) {
                subcategoryID = ProductCreation.getInstance().getCurrentProduct().getSub_category_id();
                for (int pos = 0; pos < subCategories.size(); pos++) {
                    if (subCategories.get(pos).getId() == subcategoryID) {
                        position = pos;
                        break;
                    }
                }
                etSubCategory.setSelection(position);
                /*
                if (category.size() > 1) {
                    getSubCategories(position);
                }
                */
            }
        } else {
            initModelsSpinner(new ArrayList<>());
        }

        if (category.get(etCategory.getSelectedItemPosition()).getId() == AppConstant.CategoriesIds.AUTOMOBILES || category.get(etCategory.getSelectedItemPosition()).getId() == AppConstant.CategoriesIds.ELECTRONICS || category.get(etCategory.getSelectedItemPosition()).getId() == AppConstant.CategoriesIds.MACHINERYEQUIPMENT) {
            binding.ctlayoutBrand.setVisibility(View.VISIBLE);
            binding.ctlayoutModel.setVisibility(View.VISIBLE);
        }else{
            binding.ctlayoutBrand.setVisibility(View.GONE);
            binding.ctlayoutModel.setVisibility(View.GONE);
        }
    }

    private void initModelsSpinner(ArrayList<MakeModel> result) {
        models = new ArrayList<>(result);
        ArrayList<String> categoriesTitle = new ArrayList<>();
//        categoriesTitle.add(mainActivity.getResources().getString(R.string.select_category));

        MakeModel none = new MakeModel();
        none.setId(0);
        none.setName(mainActivity.getResources().getString(R.string.choose_model));
        models.add(0, none);

        for (MakeModel item : models) {
            categoriesTitle.add(item.getName());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(mainActivity
                , R.layout.row_item_spinner, categoriesTitle) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }

        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etModels.setAdapter(categoryAdapter);

        if (ProductCreation.getInstance().isEditing()) {
            int modelID = 0, position = 0;
            if (ProductCreation.getInstance().getCurrentProduct() != null) {
                modelID = ProductCreation.getInstance().getCurrentProduct().getMake_model_id();
                for (int pos = 0; pos < models.size(); pos++) {
                    if (models.get(pos).getId() == modelID) {
                        position = pos;
                        break;
                    }
                }
                etModels.setSelection(position);
                /*
                if (category.size() > 1) {
                    getSubCategories(position);
                }
                */
            }
        } else {
            //initSubCategories(new ArrayList<>());
        }

    }

    public void initImagesAdapter() {
        mainActivity.hideBottombar();
        mImages = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity, OrientationHelper.HORIZONTAL, false);
        final DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000000);
        defaultItemAnimator.setRemoveDuration(1000000);

        binding.recyclerViewSuggestions.setLayoutManager(linearLayoutManager);
        binding.recyclerViewSuggestions.setItemAnimator(defaultItemAnimator);


        adapter = new ImagesRecylerAdapter(mainActivity, selectedImageList, this);
        binding.recyclerViewSuggestions.setAdapter(adapter);

    }

    private void setListners() {
        selectedImageList = new ArrayList<>();
        binding.btnNext.setOnClickListener(this);
        binding.addMore.setOnClickListener(this);
        etCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    getSubCategories(position);
                } else if (position == 0) {
                    initSubCategories(new ArrayList<>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    getBrands(position);
                } else if (position == 0) {
                    initBrandsSpinner(new ArrayList<>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    getModels(position);
                } else if (position == 0) {
                    initModelsSpinner(new ArrayList<>());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroupProductOn.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbRadio:
                    ProductCreation.getInstance().setProductOn(AppConstant.ProductOn.RENT);
                    break;

                case R.id.rbSale:
                    ProductCreation.getInstance().setProductOn(AppConstant.ProductOn.SALE);
                    break;
            }
        });

    }

    public void getSelectedMedia(ArrayList<File> selUris) {

        for (int j = 0; j < selUris.size(); j++) {
            if (selUris.get(j).getAbsolutePath().contains("png") || selUris.get(j).getAbsolutePath().contains("jpg") || selUris.get(j).getAbsolutePath().contains("jpeg")) {
                selectedImageList.add(new Itemlistobject(selUris.get(j).getAbsolutePath(), "0", true));
            }
        }
        adapter.notifyDataSetChanged();


    }

    private void getSubCategories(int position) {


        serviceHelper.enqueueCall(webService.getAllSubCategory(category.get(position).getId()), WebServiceConstants.GET_ALL_SUBCATEGORIES);
    }

    private void getBrands(int position) {
        serviceHelper.enqueueCall(webService.GetAllBrands(subCategories.get(position).getId()), WebServiceConstants.GET_ALL_BRANDS);
    }

    private void getModels(int position) {
        serviceHelper.enqueueCall(webService.GetAllMakeModels(brands.get(position).getId()), WebServiceConstants.GET_ALL_MODELS);
    }

    private void setData() {
        if (ProductCreation.getInstance().isEditing()) {
            if (ProductCreation.getInstance().getCurrentProduct().getProductOn() == AppConstant.ProductOn.RENT) {
                rbRadio.setChecked(true);
//                ProductCreation.getInstance().setProductOn(AppConstant.ProductOn.RENT);
            } else if (ProductCreation.getInstance().getCurrentProduct().getProductOn() == AppConstant.ProductOn.SALE) {
                rbSale.setChecked(true);
//                ProductCreation.getInstance().setProductOn(AppConstant.ProductOn.SALE);
            }

            if (ProductCreation.getInstance().getCurrentProduct().getTitle() != null) {
                binding.etTitle.setText(ProductCreation.getInstance().getCurrentProduct().getTitle());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getProductImages() != null) {
                if (ProductCreation.getInstance().getCurrentProduct().getProductImages().size() > 0) {
                    List<Itemlistobject> images = new ArrayList<>();
                    for (int pos = 0; pos < ProductCreation.getInstance().getCurrentProduct().getProductImages().size(); pos++) {
                        Itemlistobject image = new Itemlistobject(ProductCreation.getInstance().getCurrentProduct().getProductImages().get(pos).getProductImage(), ProductCreation.getInstance().getCurrentProduct().getProductImages().get(pos).getId() + "", false);
                        images.add(image);
                    }
                    adapter.addAll(images);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
