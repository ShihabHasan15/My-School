package com.devsyncit.schoolmanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCAdditionalInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class Payment_activity extends AppCompatActivity{
    MaterialCardView pay_now_btn;
    TextView amount;
    TextView currency;
    SSLCommerzInitialization initialization;
    SSLCAdditionalInitializer additionalInitializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        pay_now_btn = findViewById(R.id.pay_now_btn);
        amount = findViewById(R.id.amount);
        currency = findViewById(R.id.currency);

        String Amount = amount.getText().toString();

        Double dueAmount = Double.parseDouble(Amount);


        pay_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initialization = new SSLCommerzInitialization(
                        "nexde63e48d9521823",
                        "nexde63e48d9521823@ssl",
                        dueAmount,
                        SSLCCurrencyType.BDT,
                        "TXNBDT"+dueAmount+"CMPLT",
                        "eshop",
                        SSLCSdkType.TESTBOX
                );

                additionalInitializer = new SSLCAdditionalInitializer();
                additionalInitializer.setValueA("Value Option 1");
                additionalInitializer.setValueB("Value Option 2");
                additionalInitializer.setValueC("Value Option 3");
                additionalInitializer.setValueD("Value Option 4");

                IntegrateSSLCommerz.getInstance(Payment_activity.this)
                        .addSSLCommerzInitialization(initialization)
                        .addAdditionalInitializer(additionalInitializer)
                        .buildApiCall(new SSLCTransactionResponseListener() {
                            @Override
                            public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
                                String status = sslcTransactionInfoModel.getStatus();
                                String transaction_id = sslcTransactionInfoModel.getTranId();

                                MotionToast.Companion.createToast(
                                        Payment_activity.this,
                                        ""+status,
                                        "Transaction - "+transaction_id,
                                        MotionToastStyle.SUCCESS,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        null
                                );

                                amount.setText("0");
                                amount.setTextColor(Color.GREEN);
                                currency.setTextColor(Color.GREEN);
                            }

                            @Override
                            public void transactionFail(String s) {
                                MotionToast.Companion.createToast(
                                        Payment_activity.this,
                                        "Failed",
                                        ""+s,
                                        MotionToastStyle.ERROR,
                                        MotionToast.GRAVITY_BOTTOM,
                                        MotionToast.LONG_DURATION,
                                        null
                                );
                            }

                            @Override
                            public void closed(String s) {

                            }
                        });

            }
        });

    }

}