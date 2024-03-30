package com.rkvit.arogyalok.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.rkvit.arogyalok.R;


import org.json.JSONObject;

public class PaymentActivity extends Activity implements PaymentResultListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
//        Button button = (Button) findViewById(R.id.btn_pay);

        startPayment();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startPayment();
//            }
//        });

//        TextView privacyPolicy = (TextView) findViewById(R.id.txt_privacy_policy);

//        privacyPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
//                httpIntent.setData(Uri.parse("https://razorpay.com/sample-application/"));
//                startActivity(httpIntent);
//            }
//        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        double amt = Double.parseDouble(getIntent().getStringExtra("amount")) * 100;
        System.out.println(">>>Amt"+ amt);


        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Payment");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
//            options.put("order_id", "646646");//from response of step 3.
            options.put("theme.color", R.color.colorPrimaryDark);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "pmm.etouchindia@gmail.com");
            preFill.put("contact", "9654875082");
            preFill.put("enabled", true);
            preFill.put("max_count", 10);

            options.put("retry", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", "Razorpay Corp");
//            options.put("description", "Demoing Charges");
//            options.put("send_sms_hash",true);
//            options.put("allow_rotation", true);
//            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("currency", "INR");
//            options.put("amount", amt);
//
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "test@razorpay.com");
//            preFill.put("contact", "9876543210");
//
//            options.put("prefill", preFill);
//
//            co.open(activity, options);
//        } catch (Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
//                    .show();
//            e.printStackTrace();
//        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
