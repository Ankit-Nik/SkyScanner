package com.sky.scanner.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.sky.scanner.interfaces.AlertMagnatic;
import com.sky.scanner.R;
import com.sky.scanner.interfaces.AlertMagnaticDialog;

/**
 * This class contains all the alert dialog box.
 */
public class DialogUtils {
    public static EditText txtInputEditText;

    public static AlertDialog showDialog(
            Context context, String title, String message, String positiveBtnText,
            DialogInterface.OnClickListener positiveClickListener,
            String negativeBtnText,
            DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog alert = null;
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title).setMessage(message)
                    .setPositiveButton(positiveBtnText, positiveClickListener);
            if (negativeBtnText != null && negativeClickListener != null) {
                builder.setNegativeButton(negativeBtnText, negativeClickListener);
            }
            alert = builder.create();
            alert.show();
        }
        return alert;
    }

    public static AlertDialog showDialog(
            Context context, String title, String message, String positiveBtnText,
            DialogInterface.OnClickListener positiveClickListener) {
        return showDialog(context, title, message, positiveBtnText, positiveClickListener, null, null);
    }


    public static void getConfirmDialog(final Context mContext,
                                        final String title, final String msg,
                                        final String positiveBtnCaption, final String negativeBtnCaption,
                                        final boolean isCancelable, final AlertMagnatic target) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext, R.style.MyDialogTheme);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(
                        imageResource);


                builder.setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton(positiveBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        target.onButtonClicked(true, "");
                                    }
                                })
                        .setNegativeButton(negativeBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        target.onButtonClicked(false, "");
                                    }
                                });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.setCancelable(isCancelable);
                alert.show();
                if (isCancelable) {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface arg0) {
                            target.onButtonClicked(false, "");
                        }
                    });
                }
            }
        });
    }


    public static void showConfirmationDialog(final Context mContext, final String msg,
                                              final String positiveBtnCaption, final String negativeBtnCaption,
                                              final boolean isCancelable, final AlertMagnaticDialog target) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext, R.style.MyDialogTheme);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(
                        imageResource);


                builder.setMessage(msg).setTitle(mContext.getString(R.string.confirm))
                        .setCancelable(false)
                        .setPositiveButton(positiveBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        target.onButtonClicked(dialog, true, "");
                                    }
                                })
                        .setNegativeButton(negativeBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        target.onButtonClicked(dialog, false, "");
                                    }
                                });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.setCancelable(isCancelable);
                alert.show();
                if (isCancelable) {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface arg0) {
                            target.onButtonClicked(arg0, false, "");
                        }
                    });
                }
            }
        });
    }

    public static void getConfirmDialogWithOutNegButton(final Context mContext,
                                                        final String title, final String msg,
                                                        final String positiveBtnCaption, final String negativeBtnCaption,
                                                        final boolean isCancelable, final AlertMagnatic target) {

        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder builder =
                        new android.support.v7.app.AlertDialog.Builder(mContext, R.style.MyDialogTheme);

                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(
                        imageResource);


                builder.setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton(positiveBtnCaption,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        target.onButtonClicked(true, "");
                                    }
                                });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.setCancelable(isCancelable);
                alert.show();
                if (isCancelable) {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface arg0) {
                            target.onButtonClicked(false, "");
                        }
                    });
                }
            }
        });
    }


    public static void getSingleButtonOkDialog(final Context mContext, final String msg, final AlertMagnatic target) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                android.support.v7.app.AlertDialog.Builder builder =
                        new android.support.v7.app.AlertDialog.Builder(mContext, R.style.MyDialogTheme);

                final boolean isCancellable = true;
                int imageResource = android.R.drawable.ic_dialog_alert;
                Drawable image = mContext.getResources().getDrawable(
                        imageResource);


                builder.setMessage(msg).setTitle(mContext.getString(R.string.alert))
                        .setCancelable(isCancellable)
                        .setPositiveButton(mContext.getString(R.string.btn_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        if (target == null) return;
                                        target.onButtonClicked(true, "");
                                    }
                                });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.setCancelable(isCancellable);
                alert.show();
                if (isCancellable) {
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface arg0) {
                            if (target == null) return;
                            target.onButtonClicked(false, "");
                        }
                    });
                }
            }
        });
    }

}
