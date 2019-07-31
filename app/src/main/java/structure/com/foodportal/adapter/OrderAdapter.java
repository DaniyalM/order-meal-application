package structure.com.foodportal.adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;
import structure.com.foodportal.helper.AppConstant;
import structure.com.foodportal.helper.UIHelper;
import structure.com.foodportal.interfaces.OrderListner;
import structure.com.foodportal.models.Order;
import structure.com.foodportal.models.UserModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {

    private final MainActivity mainActivity;
    private final LayoutInflater inflater;
    ArrayList<Order> Order;
    ArrayList<Order> OrderCopy =new ArrayList<>();
    OrderListner orderListner;
    UserModel userModel;
    int orderType = 1;

    public OrderAdapter(MainActivity mainActivity, ArrayList<Order> Order, OrderListner orderListner ) {
        userModel = mainActivity.prefHelper.getUser();
        this.mainActivity = mainActivity;
        this.Order = new ArrayList<>();
        this.Order.addAll(Order);
        this.OrderCopy.addAll(Order);
        this.orderListner = orderListner;
        inflater = LayoutInflater.from(mainActivity);

    }

    public void addAll(ArrayList<Order> list) {
        this.Order.clear();
        this.Order.addAll(list);
        // this.OrderCopy.addAllToAdapter(list);

    }

//    public void clear() {
//        this.Order.clear();
//        this.OrderCopy.clear();
//    }
//
    @NonNull
    @Override
    public OrderAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_history, parent, false);
        return new OrderAdapter.VH(view);
    }

    @Override
    public int getItemCount() {
        return this.Order.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.VH holder, int position) {




            if (Order.get(position).getStatus()== AppConstant.OrderStatuses.PENDING) {
                bindData(holder, position);
                if (Order.get(position).getOrderProduct().getUserId() == userModel.getId()) {
                  //  holder.prllacceptorrejectoductiimage.setVisibility(View.VISIBLE);
                    holder.vfOrdertime.setVisibility(View.VISIBLE);
                }

            } else if (Order.get(position).getStatus() == AppConstant.OrderStatuses.COMPLETED) {

                bindData(holder, position);
                if (Order.get(position).getOrderProduct().getUserId() == userModel.getId()) {
                  //  holder.prllacceptorrejectoductiimage.setVisibility(View.GONE);
                    holder.vfOrdertime.setVisibility(View.GONE);
                }
            }



        holder.btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   AnimationFactory.flipAnimation(holder.prllacceptorrejectoductiimage, holder.vfOrdertime, AnimationFactory.FlipDirection.LEFT_RIGHT, 1500, null);


                if (dateandtime != null) {
                    orderListner.Accept(Order.get(position), position, dateandtime);

                } else {

                    UIHelper.showToast(mainActivity, mainActivity.getString(R.string.pick_date_time));
                }
            }
        });


        holder.tvPickUpDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datepickershow(holder);


            }
        });
        holder.btnreject.setOnClickListener(view -> orderListner.Reject(Order.get(position),position));


    }

//    public void setOrderListType(int orderType) {
//        this.orderType = orderType;
//         this.Order.clear();
//        this.Order.addAllToAdapter(OrderCopy);
//        notifyDataSetChanged();
//
//    }

    class VH extends RecyclerView.ViewHolder {


        TextView title, modelnum, customername, orderid, status, ordertype, duration, total, orderDate, tvPickUpDateTime;
        ImageView productiimage;
        LinearLayout prllacceptorrejectoductiimage, vfOrdertime;
        Button btnaccept, btnreject;

        public VH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvproducttitle);
            tvPickUpDateTime = itemView.findViewById(R.id.tvPickUpDateTime);
            modelnum = itemView.findViewById(R.id.tvproductmodel);
            customername = itemView.findViewById(R.id.tvCustomername);
            orderid = itemView.findViewById(R.id.tvOrderId);
            status = itemView.findViewById(R.id.tvStatus);
            orderDate = itemView.findViewById(R.id.tvOrderDate);
            ordertype = itemView.findViewById(R.id.tvOrderType);
            duration = itemView.findViewById(R.id.tvDuration);
            total = itemView.findViewById(R.id.tvTotalAmount);
            productiimage = itemView.findViewById(R.id.ivProductImage);
            btnaccept = itemView.findViewById(R.id.btnAccept);
            btnreject = itemView.findViewById(R.id.btnReject);
            prllacceptorrejectoductiimage = itemView.findViewById(R.id.llacceptorreject);
            vfOrdertime = itemView.findViewById(R.id.vfOrdertime);


        }
    }

    private int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog;

    public void datepickershow(OrderAdapter.VH holder) {
        final Calendar c = Calendar.getInstance();
        Locale locale = mainActivity.getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        int cyy = c.get(Calendar.YEAR);
        int cmm = c.get(Calendar.MONTH);
        int cdd = c.get(Calendar.DAY_OF_MONTH);
        //   datePickerDialog = new DatePickerDialog(mainActivity, this, cyy, cmm, cdd);

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(mainActivity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        ///*   holder.tvPickUpDateTime.setText(*/dayOfMonth + "-" + (monthOfYear + 1) + "-" + year/*);*/
                        timepickershow(holder, year, monthOfYear, dayOfMonth);
                        datePickerDialog.dismiss();

                    }
                }, cyy, cmm, cdd);
        datePickerDialog.getDatePicker().setMinDate(c.getTime().getTime());
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();

    }

    TimePickerDialog timePickerDialog;
    String dateandtime;

    public void timepickershow(OrderAdapter.VH holder, int year,
                               int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        timePickerDialog = new TimePickerDialog(mainActivity,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        //holder.tvPickUpDateTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+" / "+hourOfDay + ":" + minute);
                        dateandtime = UIHelper.getFormattedDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " " + hourOfDay + ":" + minute, "dd-MM-yyyy hh:mm", AppConstant.SERVER_DATE_FORMAT);
                        holder.tvPickUpDateTime.setText(dateandtime);
                        timePickerDialog.dismiss();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    void bindData(OrderAdapter.VH holder, int position) {

        holder.title.setText(Order.get(position).getOrderProduct().getTitle());



        holder.modelnum.setText(Order.get(position).getOrderProduct().getModelNumber());

        String CustomerName  = "Customer Name "+"<b>"+Order.get(position).getOrderUser().getFullName()+"</b>";
        holder.customername.setText(Html.fromHtml(CustomerName));


       String OrderID = "Order ID "+"<b>" + Order.get(position).getId() + "</b>";

        holder.orderid.setText(Html.fromHtml(OrderID));



        String  Status;

            switch (Order.get(position).getStatus()){

                case AppConstant.OrderStatuses.ACCEPTED:
                      Status = "<b>" + mainActivity.getString(R.string.accepted) + "</b>";
                    holder.status.setText(Html.fromHtml(Status));
                    holder.status.setTextColor(mainActivity.getResources().getColor(R.color.colorLightGreen));

                    break;

                case AppConstant.OrderStatuses.CANCELLED:
                      Status = "<b>" + mainActivity.getString(R.string.canceled) + "</b>";
                    holder.status.setText(Html.fromHtml(Status));
                    holder.status.setTextColor(mainActivity.getResources().getColor(R.color.colorRed));

                    break;

                case AppConstant.OrderStatuses.PENDING:
                      Status ="<b>" + mainActivity.getString(R.string.pending) + "</b>";
                    holder.status.setText(Html.fromHtml(Status));
                    holder.status.setTextColor(mainActivity.getResources().getColor(R.color.colorLightGreen));

                    break;

                case AppConstant.OrderStatuses.COMPLETED:
                      Status = "<b>" + mainActivity.getString(R.string.completed) + "</b>";
                    holder.status.setText(Html.fromHtml(Status));
                    holder.status.setTextColor(mainActivity.getResources().getColor(R.color.colorStatusWarning));
                    break;


            }


        //  holder.duration.setText(Order.get(position).getOrderProduct().getAvailableDays().getDays());

       String Amount = "Total AED " + "<b>" + Order.get(position).getOrderProduct().getAmount() + "</b>";

        holder.total.setText(Html.fromHtml(Amount));

        if (Order.get(position).getOrderProduct().getProductOn()== AppConstant.ProductOn.RENT) {
            holder.duration.setVisibility(View.VISIBLE);
            if (Order.get(position).getQuantity() > 0) {
                if (Order.get(position).getRent_type_id() != null) {
                    switch (Order.get(position).getRent_type_id()) {
                        case AppConstant.RentTypes.PER_HOUR:
                            holder.duration.setText(Order.get(position).getQuantity() == 1 ? Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_singular) : Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.hour_plural));
                            break;

                        case AppConstant.RentTypes.PER_DAY:
                            holder.duration.setText(Order.get(position).getQuantity() == 1 ? Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                            break;

                        case AppConstant.RentTypes.PER_WEEK:
                            holder.duration.setText(Order.get(position).getQuantity() == 1 ? Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.week_singular) : Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.week_plural));
                            break;

                        case AppConstant.RentTypes.PER_MONTH:
                            holder.duration.setText(Order.get(position).getQuantity() == 1 ? Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.month_singular) : Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.months_plural));
                            break;
                    }

                } else {
                    holder.duration.setText(Order.get(position).getQuantity() == 1 ? Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.day_singular) : Order.get(position).getQuantity() + " " + mainActivity.getResources().getString(R.string.days_plural));
                }

            }
          String  ordertype = "Order Type "+"<b>"+"For Rent"+"</b>";

            holder.ordertype.setText(Html.fromHtml(ordertype));

        } else {
            holder.duration.setVisibility(View.GONE);
            String  ordertype = "Order Type "+"<b>"+"For Sale"+"</b>";

            holder.ordertype.setText(Html.fromHtml(ordertype));

        }
        UIHelper.setImagewithGlide(mainActivity, holder.productiimage, Order.get(position).getOrderProduct().getProductImages().get(0).getProductImage());


    }


}