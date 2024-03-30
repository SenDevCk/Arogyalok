package com.rkvit.arogyalok.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.rkvit.arogyalok.R;
import com.rkvit.arogyalok.RetrofitUtil.Constant;

import java.text.DateFormatSymbols;

public class HelperActivity {

    Activity activity;

    public HelperActivity(Activity activity) {
        this.activity = activity;
    }


    @SuppressLint("IntentReset")
    public void sendEmail(String mailId) {

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("message/rfc822");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mailId});
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello");
        try {
            activity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public void shareApp() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nGet All Medical Services  At Your Fingertip. Install the app now\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {

        }

    }

    public void doWhatsApp(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp.w4b");
        activity.startActivity(i);

    }

    public void makeCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));//change the number
        activity.startActivity(callIntent);
    }

    public void doMsg(String number) {
//        String smsNumber = edittextSmsNumber.getText().toString();
//        String smsText = edittextSmsText.getText().toString();
        Uri uri = Uri.parse("smsto:" + number);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//        intent.putExtra("sms_body", smsText);
        activity.startActivity(intent);

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("07123456789", null, "Hello there!", null, null);

    }

    public void rateUs() {

        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
    }

    // check version on play store and force update
    // Now depricated
    public void forceUpdate() {
        PackageManager packageManager = activity.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new ForceUpdateAsync(currentVersion,activity).execute();
    }


    public void UpdateApp(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(activity);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);
                android.view.ContextThemeWrapper ctw = new android.view.ContextThemeWrapper(activity, R.style.AlertDialog_AppCompat);
                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctw);
                alertDialogBuilder.setTitle("Arogyalok Update");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.logo);
                alertDialogBuilder.setMessage("Update to the latest version for a seamless & enhanced performance of the app.");
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+activity.getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+activity.getPackageName())));
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("No Thanks",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                alertDialogBuilder.show();

            } else {

            }
        });
    }

}
