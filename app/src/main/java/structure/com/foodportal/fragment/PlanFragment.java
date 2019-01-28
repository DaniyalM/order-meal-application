package structure.com.foodportal.fragment;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.databinding.FragmentPlanBinding;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.ProductCreation;
import structure.com.foodportal.helper.Titlebar;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.models.AddProductModel;
import structure.com.foodportal.models.PaymentType;
import structure.com.foodportal.models.ProductModelAPI;
import structure.com.foodportal.models.WeekDay;

public class PlanFragment extends BaseFragment implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {
    FragmentPlanBinding binding;
    ArrayList<String> days;
    @BindView(R.id.rdPerhour)
    CheckBox rdPerhour;
    @BindView(R.id.rdPerDay)
    CheckBox rdPerDay;
    @BindView(R.id.rdPerWeek)
    CheckBox rdPerWeek;
    @BindView(R.id.rdPerMonth)
    CheckBox rdPerMonth;
    @BindView(R.id.rdCashCollection)
    CheckBox rdCashCollection;
    @BindView(R.id.rdCreditCard)
    CheckBox rdCreditCard;
    @BindView(R.id.rdCashReturn)
    CheckBox rdCashReturn;
    Unbinder unbinder;
    int paymmentTypeId = 0;
    int rentType_id = 0;
    ArrayList<PaymentType> paymentTypes = new ArrayList<>();
    @BindView(R.id.tvSelectDays)
    TextView tvSelectDays;
    @BindView(R.id.tvTypeBasedLabelForAmount)
    TextView tvTypeBasedLabelForAmount;
    @BindView(R.id.llPlanForRent)
    LinearLayout llPlanForRent;
    private AddProductModel.Builder productDetailsBuilder;

    private boolean isClicked = true;
    private Titlebar titlebar;

    ArrayList<ProductModelAPI.ProductRentType> paymentRentTypes = new ArrayList<>();

    public void setProductDetailsBuilder(AddProductModel.Builder productDetailsBuilder) {
        this.productDetailsBuilder = productDetailsBuilder;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plan, container, false);
        unbinder = ButterKnife.bind(this, binding.getRoot());
        mainActivity.hideBottombar();
        if (ProductCreation.getInstance().getProductOn() == AppConstant.ProductOn.SALE) {
            tvTypeBasedLabelForAmount.setText(mainActivity.getResources().getString(R.string.enter_your_selling_amount));
            llPlanForRent.setVisibility(View.GONE);
            rdCashReturn.setVisibility(View.GONE);
        }

        initSpinners();
        initDays();
        setListners();
        setData();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
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
        super.onDestroy();
    }

    private void initSpinners() {
        days = new ArrayList<>();
        days.add(mainActivity.getString(R.string.fri_sat_sun_repeated_every_week));
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Satday");
        days.add("Sunday");

        binding.etAvaialableDays.setItems(days);

        String selectedDays = "";
        for (int pos = 0; pos < AppConstant.selectedDays.size(); pos++) {
            selectedDays += AppConstant.selectedDays.get(pos);
            if (pos != AppConstant.selectedDays.size() - 1) {
                selectedDays += ", ";
            }
        }
        if (AppConstant.selectedDays.size() > 0) {
            tvSelectDays.setText(selectedDays);
        }
    }

    private void setListners() {
        binding.btnNext.setOnClickListener(this);
        binding.etAvaialableDays.setOnItemSelectedListener(this);
        rdCreditCard.setOnClickListener(this);
        rdCashReturn.setOnClickListener(this);
        rdCashCollection.setOnClickListener(this);
        rdPerDay.setOnClickListener(this);
        rdPerhour.setOnClickListener(this);
        rdPerMonth.setOnClickListener(this);
        rdPerWeek.setOnClickListener(this);
        tvSelectDays.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
//                AppConstant.selectedDays.size();
                if (validated()) {
                    ProductCreation.getInstance().setRentTypes(paymentRentTypes);
                    ProductCreation.getInstance().setPaymentTypes(paymentTypes);
                    productDetailsBuilder
                            .withAmount(binding.etTitle.getText().toString())
                            .withSelectedDays(AppConstant.selectedDays)
                            .withPaymentTypes(paymentTypes)
                            .withRentTypeId(rentType_id)
                            .withCurrencyCode("1");
                    MoreAboutProduct fragment = new MoreAboutProduct();
                    fragment.setProductDetailsBuilder(productDetailsBuilder);
                    mainActivity.addFragment(fragment, true, true);
                }
                break;

            case R.id.rdCashCollection:
                boolean cashchecked = ((CheckBox) view).isChecked();
                cashCollection(cashchecked);
//                rdCashCollection.setChecked(true);
//                rdCashReturn.setChecked(false);
//                rdCreditCard.setChecked(false);
//                paymmentTypeId = 1;
                break;

            case R.id.rdCashReturn:
                boolean cashreturnchecked = ((CheckBox) view).isChecked();
                cashReturn(cashreturnchecked);
//                rdCashReturn.setChecked(true);
//                rdCashCollection.setChecked(false);
//                rdCreditCard.setChecked(false);
//                paymmentTypeId = 3;
                break;
            case R.id.rdCreditCard:
                boolean creditcardchecked = ((CheckBox) view).isChecked();
                creditCard(creditcardchecked);
//                rdCreditCard.setChecked(true);
//                rdCashCollection.setChecked(false);
//                rdCashReturn.setChecked(false);
//                paymmentTypeId = 2;
                break;


            ////////////////////////////////////////////////////////////////////////////////////////////////
            case R.id.rdPerDay:
                boolean perDay = ((CheckBox) view).isChecked();
                checkPerDay(perDay);
//                perDay();
                break;

            case R.id.rdPerhour:
                boolean perHour = ((CheckBox) view).isChecked();
                checkPerHour(perHour);
//                perHour();
                break;


            case R.id.rdPerMonth:
                boolean perMonth = ((CheckBox) view).isChecked();
                checkPerMonth(perMonth);
//                perMonth();
                break;

            case R.id.rdPerWeek:
                boolean perWeek = ((CheckBox) view).isChecked();
                checkPerWeek(perWeek);
//                perWeek();
                break;

            case R.id.tvSelectDays:
                if (isClicked) {
                    /*
                    multipleChoiceItemsDialog(
                            mainActivity,
                            mainActivity.getResources().getString(R.string.select_available_days),
                            mainActivity.getResources().getStringArray(R.array.days),
                            AppConstant.checkedDays,
                            mainActivity.getResources().getString(R.string.daysError),
                            mainActivity.getResources().getString(R.string.cancel)
                    );
                    */
                    DaysDialog daysDialog = new DaysDialog(mainActivity, ProductCreation.getInstance().getDays(), () -> {
                        String selectedDays = "";
                        for (int pos = 0; pos < ProductCreation.getInstance().getDays().size(); pos++) {
                            if (ProductCreation.getInstance().getDays().get(pos).isChecked() && pos > 0) {
                                selectedDays += ProductCreation.getInstance().getDays().get(pos).getName();
                                selectedDays += ", ";
                            }
                        }
                        if (selectedDays.length() > 1) {
                            selectedDays = selectedDays.trim();
                            if (selectedDays.charAt(selectedDays.length() - 1) == ',') {
                                selectedDays = selectedDays.substring(0, selectedDays.length() - 1);
                            }
                        }
                        tvSelectDays.setText(selectedDays);

                        if (ProductCreation.getInstance().getDays().get(0).isChecked()) {
                            rdPerWeek.setAlpha(1);
                            rdPerWeek.setEnabled(true);

                            rdPerMonth.setAlpha(1);
                            rdPerMonth.setEnabled(true);
                        } else {
                            rdPerWeek.setChecked(false);
                            rdPerMonth.setChecked(false);

                            rdPerWeek.setAlpha((float) 0.5);
                            rdPerWeek.setEnabled(false);

                            rdPerMonth.setAlpha((float) 0.5);
                            rdPerMonth.setEnabled(false);
                        }

                    });
                    daysDialog.show(mainActivity.getSupportFragmentManager(), DaysDialog.class.getSimpleName());
                    isClicked = false;
                    new Handler().postDelayed(() -> isClicked = true, 2500);

                }
                break;
        }
    }

    private boolean validated() {
        if (binding.etTitle.getText().toString().equals("")) {
            UIHelper.showToast(getActivity(), mainActivity.getResources().getString(R.string.amountError));
            return false;
        } else if (!(tvSelectDays.getText().toString().length() > 0) && ProductCreation.getInstance().getProductOn() == AppConstant.ProductOn.RENT) {
            UIHelper.showToast(getActivity(), mainActivity.getResources().getString(R.string.daysError));
            return false;

        } else if (!(paymentRentTypes.size() > 0) && ProductCreation.getInstance().getProductOn() == AppConstant.ProductOn.RENT) {
            UIHelper.showToast(getActivity(), mainActivity.getResources().getString(R.string.rentError));
            return false;

        } else if (!(paymentTypes.size() > 0)) {
            UIHelper.showToast(getActivity(), mainActivity.getResources().getString(R.string.paymentError));
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void setTitle(Titlebar titlebar) {
        this.titlebar = titlebar;
        titlebar.setTitle(getString(R.string.product_plan));
        titlebar.showBackButton(mainActivity);
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

        switch (view.getId()) {

            case R.id.etAvaialableDays:
                binding.etAvaialableDays.setText(days.get(position));

                break;

        }
    }

    public void multipleChoiceItemsDialog(MainActivity mainActivity, String title, CharSequence[] listItems, boolean[] checkedItems, String positiveButton, String negativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(title).setMultiChoiceItems(
                listItems,
                checkedItems,
                (dialog, which, isChecked) -> {
                    if (isChecked) {
                        if (!AppConstant.selectedDays.contains(listItems[which].toString())) {
                            AppConstant.selectedDays.add(listItems[which].toString());
                            AppConstant.checkedDays[which] = true;
                        }
                    } else {
                        for (int pos = 0; pos < AppConstant.selectedDays.size(); pos++) {
                            if (AppConstant.selectedDays.get(pos).equals(listItems[which].toString())) {
                                AppConstant.selectedDays.remove(pos);
                                AppConstant.checkedDays[which] = false;
                                break;
                            }
                        }
                    }
                })
                .setPositiveButton(positiveButton, (dialog, id) -> {
                    if (AppConstant.selectedDays.size() == 0) {
                        tvSelectDays.setText(mainActivity.getResources().getString(R.string.select_available_days_dots));
                        UIHelper.showToast(mainActivity, mainActivity.getResources().getString(R.string.nothing_selected));
                    } else {
                        String selectedDays = "";
                        for (int pos = 0; pos < AppConstant.selectedDays.size(); pos++) {
                            selectedDays += AppConstant.selectedDays.get(pos);
                            if (pos != AppConstant.selectedDays.size() - 1) {
                                selectedDays += ", ";
                            }
                        }
                        tvSelectDays.setText(selectedDays);
                        if (AppConstant.selectedDays.size() == 0) {
                            tvSelectDays.setText(mainActivity.getResources().getString(R.string.select_available_days_dots));
                        }
                    }
                })
                .setNegativeButton(negativeButton, (dialog, id) -> {
                    AppConstant.selectedDays.clear();
                    for (int pos = 0; pos < AppConstant.checkedDays.length; pos++) {
                        AppConstant.checkedDays[pos] = false;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initDays() {
        if (ProductCreation.getInstance().getDays() == null) {
            ArrayList<WeekDay> days = new ArrayList<>();
            days.add(new WeekDay(0, mainActivity.getResources().getString(R.string.all_days)));
            days.add(new WeekDay(1, mainActivity.getResources().getString(R.string.sunday)));
            days.add(new WeekDay(2, mainActivity.getResources().getString(R.string.monday)));
            days.add(new WeekDay(3, mainActivity.getResources().getString(R.string.tuesday)));
            days.add(new WeekDay(4, mainActivity.getResources().getString(R.string.wednesday)));
            days.add(new WeekDay(5, mainActivity.getResources().getString(R.string.thursday)));
            days.add(new WeekDay(6, mainActivity.getResources().getString(R.string.friday)));
            days.add(new WeekDay(7, mainActivity.getResources().getString(R.string.saturday)));

            ProductCreation.getInstance().setDays(days);

            tvSelectDays.setSelected(true);
        }
    }

    private void perHour() {
//        rdPerhour.setChecked(true);
//        rdPerDay.setChecked(false);
//        rdPerWeek.setChecked(false);
//        rdPerMonth.setChecked(false);
        rentType_id = AppConstant.RentTypes.PER_HOUR;
    }

    private void perDay() {
//        rdPerDay.setChecked(true);
//        rdPerWeek.setChecked(false);
//        rdPerMonth.setChecked(false);
//        rdPerhour.setChecked(false);
        rentType_id = AppConstant.RentTypes.PER_DAY;
    }

    private void perWeek() {
//        rdPerWeek.setChecked(true);
//        rdPerDay.setChecked(false);
//        rdPerMonth.setChecked(false);
//        rdPerhour.setChecked(false);
        rentType_id = AppConstant.RentTypes.PER_WEEK;
    }

    private void perMonth() {
//        rdPerMonth.setChecked(true);
//        rdPerDay.setChecked(false);
//        rdPerWeek.setChecked(false);
//        rdPerhour.setChecked(false);
        rentType_id = AppConstant.RentTypes.PER_MONTH;
    }

    private void checkPerHour(boolean perHourChecked) {
        ProductModelAPI.ProductRentType rentType = mainActivity.getCategories().getPaymentRentTypes().get(0);
        if (perHourChecked) {
            if (!paymentRentTypes.contains(rentType)) {
                paymentRentTypes.add(rentType);
            } else {
                if (paymentRentTypes.contains(rentType)) {
                    paymentRentTypes.remove(rentType);
                }
            }
        }
    }

    private void checkPerDay(boolean perDayChecked) {
        ProductModelAPI.ProductRentType rentType = mainActivity.getCategories().getPaymentRentTypes().get(1);
        if (perDayChecked) {
            if (!paymentRentTypes.contains(rentType)) {
                paymentRentTypes.add(rentType);
            } else {
                if (paymentRentTypes.contains(rentType)) {
                    paymentRentTypes.remove(rentType);
                }
            }
        }
    }

    private void checkPerWeek(boolean perWeekChecked) {
        ProductModelAPI.ProductRentType rentType = mainActivity.getCategories().getPaymentRentTypes().get(2);
        if (perWeekChecked) {
            if (!paymentRentTypes.contains(rentType)) {
                paymentRentTypes.add(rentType);
            } else {
                if (paymentRentTypes.contains(rentType)) {
                    paymentRentTypes.remove(rentType);
                }
            }
        }
    }

    private void checkPerMonth(boolean perMonthChecked) {
        ProductModelAPI.ProductRentType rentType = mainActivity.getCategories().getPaymentRentTypes().get(3);
        if (perMonthChecked) {
            if (!paymentRentTypes.contains(rentType)) {
                paymentRentTypes.add(rentType);
            } else {
                if (paymentRentTypes.contains(rentType)) {
                    paymentRentTypes.remove(rentType);
                }
            }
        }
    }

    private void cashCollection(boolean cashchecked) {
        PaymentType cashCollection = mainActivity.getCategories().getPaymentTypes().get(0);
        if (cashchecked) {
            if (!paymentTypes.contains(cashCollection)) {
                paymentTypes.add(cashCollection);
            }
        } else {
            if (paymentTypes.contains(cashCollection)) {
                paymentTypes.remove(cashCollection);
            }
        }
    }

    private void cashReturn(boolean cashreturnchecked) {
        PaymentType cashReturn = mainActivity.getCategories().getPaymentTypes().get(1);
        if (cashreturnchecked) {
            if (!paymentTypes.contains(cashReturn)) {
                paymentTypes.add(cashReturn);
            }
        } else {
            if (paymentTypes.contains(cashReturn)) {
                paymentTypes.remove(cashReturn);
            }
        }
    }

    private void creditCard(boolean creditcardchecked) {
        PaymentType creditCard = mainActivity.getCategories().getPaymentTypes().get(2);
        if (creditcardchecked) {
            if (!paymentTypes.contains(creditCard)) {
                paymentTypes.add(creditCard);
            }
        } else {
            if (paymentTypes.contains(creditCard)) {
                paymentTypes.remove(creditCard);
            }
        }
    }

    private void setData() {
        if (ProductCreation.getInstance().isEditing()) {

            if (ProductCreation.getInstance().getCurrentProduct().getAmount() != null) {
                binding.etTitle.setText(ProductCreation.getInstance().getCurrentProduct().getAmount());
            }

            if (ProductCreation.getInstance().getCurrentProduct().getAvailableDays() != null) {
                if (ProductCreation.getInstance().getCurrentProduct().getAvailableDays().getDays() != null) {
                    binding.tvSelectDays.setText(ProductCreation.getInstance().getCurrentProduct().getAvailableDays().getDays());
                }
                if (ProductCreation.getInstance().isEditing()) {
//                    AppConstant.checkedDays = AppConstant.clearCheckedDays;
                    String[] days;
                    if (ProductCreation.getInstance().getCurrentProduct().getAvailableDays() != null) {
                        if (ProductCreation.getInstance().getCurrentProduct().getAvailableDays().getDays() != null) {
                            days = ProductCreation.getInstance().getCurrentProduct().getAvailableDays().getDays().split(",");
                            for (int dayslistitempos = 0; dayslistitempos < ProductCreation.getInstance().getDays().size(); dayslistitempos++) {
                                for (String day : days) {
                                    if (day != null) {
                                        if (ProductCreation.getInstance().getDays().get(dayslistitempos).getName().equals(day.trim())) {
                                            ProductCreation.getInstance().getDays().get(dayslistitempos).setChecked(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
/*
            if (ProductCreation.getInstance().getCurrentProduct().getRentTypeId() == AppConstant.RentTypes.PER_HOUR) {
                perHour();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getRentTypeId() == AppConstant.RentTypes.PER_DAY) {
                perDay();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getRentTypeId() == AppConstant.RentTypes.PER_WEEK) {
                perWeek();
            }

            if (ProductCreation.getInstance().getCurrentProduct().getRentTypeId() == AppConstant.RentTypes.PER_MONTH) {
                perMonth();
            }
*/

            if (ProductCreation.getInstance().getCurrentProduct().getProductRentType() != null) {
                if (ProductCreation.getInstance().getCurrentProduct().getProductRentType().size() > 0) {
                    for (int pos = 0; pos < ProductCreation.getInstance().getCurrentProduct().getProductRentType().size(); pos++) {
                        if (ProductCreation.getInstance().getCurrentProduct().getProductRentType().get(pos).getId() == mainActivity.getCategories().getPaymentRentTypes().get(0).getId()) {
                            binding.rdPerhour.setChecked(true);
                            checkPerHour(true);
                        } else if (ProductCreation.getInstance().getCurrentProduct().getProductRentType().get(pos).getId() == mainActivity.getCategories().getPaymentRentTypes().get(1).getId()) {
                            binding.rdPerDay.setChecked(true);
                            checkPerDay(true);
                        } else if (ProductCreation.getInstance().getCurrentProduct().getProductRentType().get(pos).getId() == mainActivity.getCategories().getPaymentRentTypes().get(2).getId()) {
                            binding.rdPerWeek.setChecked(true);
                            checkPerWeek(true);
                        } else if (ProductCreation.getInstance().getCurrentProduct().getProductRentType().get(pos).getId() == mainActivity.getCategories().getPaymentRentTypes().get(3).getId()) {
                            binding.rdPerMonth.setChecked(true);
                            checkPerMonth(true);
                        }
                    }
                }
            }

            if (ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes() != null) {
                if (ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes().size() > 0) {
                    for (int pos = 0; pos < ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes().size(); pos++) {
                        if (ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes().get(pos).getPaymentTypeId() == mainActivity.getCategories().getPaymentTypes().get(0).getId()) {
                            binding.rdCashCollection.setChecked(true);
                            cashCollection(true);
                        } else if (ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes().get(pos).getPaymentTypeId() == mainActivity.getCategories().getPaymentTypes().get(1).getId()) {
                            binding.rdCashReturn.setChecked(true);
                            cashReturn(true);
                        } else if (ProductCreation.getInstance().getCurrentProduct().getProductPaymentTypes().get(pos).getPaymentTypeId() == mainActivity.getCategories().getPaymentTypes().get(2).getId()) {
                            binding.rdCreditCard.setChecked(true);
                            creditCard(true);
                        }
                    }
                }
            }

        }
    }
}
