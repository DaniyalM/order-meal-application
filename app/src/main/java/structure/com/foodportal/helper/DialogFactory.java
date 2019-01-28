package structure.com.foodportal.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import structure.com.foodportal.R;
import structure.com.foodportal.activity.MainActivity;


public class DialogFactory {
    private static int i = -1;

    public static Dialog createProgressDialog(Activity activity, String title,
                                              String loadingMessage) {
        ProgressDialog prDialog = new ProgressDialog(activity);
        prDialog.setTitle(title);
        prDialog.setMessage(loadingMessage);
        return prDialog;
    }

    public static Dialog createQuitDialog(Activity activity,
                                          DialogInterface.OnClickListener dialogPositive, int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle("")
                .setMessage(messageId)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, dialogPositive)
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();

                            }
                        });
        return builder.create();

    }


    public static Dialog createSimpleDialog(Activity activity,
                                            DialogInterface.OnClickListener dialogPositive, int messageId,
                                            int titleId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(messageId)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, dialogPositive)
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();

                            }
                        });
        return builder.create();

    }

    public static Dialog createSingleButtonDialog(Activity activity,
                                                  DialogInterface.OnClickListener dialogPositive, String messageId,String title
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(messageId)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, dialogPositive);

        return builder.create();

    }

    public static Dialog createMessageDialog(Activity activity,
                                             DialogInterface.OnClickListener dialogPositive,
                                             String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Yes", dialogPositive)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                    }
                });

        return builder.create();

    }

    public static Dialog createMessageSignUpDialog(Activity activity,
                                                   DialogInterface.OnClickListener dialogPositive, DialogInterface.OnClickListener dialogNegative,
                                                   String message) {


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, dialogPositive);

        // .setNegativeButton("No",dialogNegative);
        return builder.create();

    }

    public static Dialog createOneButtonMessageDialog(Activity activity,
                                                      DialogInterface.OnClickListener dialogPositive,
                                                      String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, dialogPositive);
        return builder.create();

    }

    public static AlertDialog.Builder createMessageDialog2(Activity activity,
                                                           DialogInterface.OnClickListener dialogPositive,
                                                           DialogInterface.OnClickListener dialogNegative,
                                                           CharSequence message, String titleId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(message).setCancelable(true)
                .setPositiveButton("Yes", dialogPositive)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        return builder;

    }

    public static Dialog createInputDialog(Activity activity,
                                           DialogInterface.OnClickListener dialogPositive,
                                           DialogInterface.OnClickListener dialogNegative, String title,
                                           String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.setTitle(title);
        alert.setMessage(message);
        final EditText input = new EditText(activity);
        alert.setView(input);

        alert.setPositiveButton("Yes", dialogPositive);

        alert.setNegativeButton("No", dialogNegative);

        return alert.create();

    }

    public static void listDialogforcar(final MainActivity activity,
                                        DialogInterface.OnClickListener dialogPositive,
                                        String title,
                                        ArrayList<String> list) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle(title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.addAll(list);
        builderSingle.setAdapter(arrayAdapter, dialogPositive);
        AlertDialog dialog = builderSingle.create();
        dialog.setCancelable(true);
        dialog.show();

    }


    public static void listDialog(final MainActivity activity,
                                  DialogInterface.OnClickListener dialogPositive,
                                  String title,
                                  ArrayList<String> list) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle(title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.addAll(list);
        builderSingle.setAdapter(arrayAdapter, dialogPositive);
        AlertDialog dialog = builderSingle.create();
        dialog.show();

    }

    public static void multipleChoiceItemsDialog(MainActivity mainActivity, String title, CharSequence[] listItems, boolean[] checkedItems, String positiveButton, String negativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle(title).setMultiChoiceItems(
                listItems,
                checkedItems,
                (dialog, which, isChecked) -> {
                    if(isChecked){
                        AppConstant.checkedDays[which] = true;
                    }else{
                        AppConstant.checkedDays[which] = false;
                    }
                    /*
                    if (isChecked) {
                        mSelectedItems.add(which);
                    } else if (mSelectedItems.contains(which)) {
                        mSelectedItems.remove(Integer.valueOf(which));
                    }
                    */
                })
                .setPositiveButton(positiveButton, (dialog, id) -> {

                })
                .setNegativeButton(negativeButton, (dialog, id) -> {

                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void listDialogInt(final MainActivity activity,
                                     DialogInterface.OnClickListener dialogPositive,
                                     String title,
                                     ArrayList<Integer> list) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle(title);
        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(activity, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.addAll(list);
        builderSingle.setAdapter(arrayAdapter, dialogPositive);
        AlertDialog dialog = builderSingle.create();
        dialog.show();

    }

    public static void showAlertDialog(MainActivity context, String title, String message,
                                       Boolean status) {
      /*  final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);
        alertDialog.setCancelable(true);
        // Setting Dialog Message
        alertDialog.setMessage(message);


        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        Animation.animation(Techniques.Tada, 1000, 0, alertDialog.getCurrentFocus().getRootView());*/

//        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(context)
//                .setTitle("Blocked!")
//                .setDescription(message).setPositive("Ok", new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setCancelable(true)
//                .setHeaderColor(R.color.lightGrayColor).withDialogAnimation(true)
//                .build();
//        dialog.show();


    }

    public static void showmaterialdialog(MainActivity refence) {
    }


    public static void showAlert(MainActivity context, String title, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            final AlertDialog dialog = builder.create();

            dialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static void showAlert(MainActivity context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            builder.setCancelable(false);
            builder.setMessage(message);
            if (onClickListener != null) {
                builder.setPositiveButton(android.R.string.ok, onClickListener);
            } else {
                builder.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }

            final AlertDialog dialog = builder.create();

            dialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
