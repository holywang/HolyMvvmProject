package com.mvvm.holyandroid.activity;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;
import static com.mvvm.holyandroid.activity.LoginActivity.REQ_ONE_TAP;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.databinding.ActivityGooglePlayBillingBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GooglePlayBillingActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityGooglePlayBillingBinding layoutBinding;
    BillingClient client;

    List<ProductDetails> productDetails;

    private PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, purchases) -> {
        // To be implemented in a later section.
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    };

    private void handlePurchase(Purchase purchase) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityGooglePlayBillingBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        layoutBinding.submit.setOnClickListener(this);
        layoutBinding.prepare.setOnClickListener(this);
        layoutBinding.login.setOnClickListener(this);
        client = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        client.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                Log.e("GooglePlayBillingActivity", "onBillingSetupFinished: " + billingResult.getResponseCode());
                if (billingResult.getResponseCode() == OK) {
                    // The BillingClient is ready. You can query purchases here.
                    queryProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.e("GooglePlayBillingActivity", "onBillingServiceDisconnected");
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    private void queryProducts() {
        Log.e("GooglePlayBillingActivity", "queryProducts in");
        QueryProductDetailsParams queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder().setProductList(
                        ImmutableList.of(
                                QueryProductDetailsParams.Product
                                        .newBuilder()
                                        .setProductId("test.for.sub")
                                        .setProductType(BillingClient.ProductType.SUBS)
                                        .build()
                        )
                ).build();
        client.queryProductDetailsAsync(
                queryProductDetailsParams,
                (billingResult, productDetailsList) -> {
                    // check billingResult
                    // process returned productDetailsList
                    Log.e("GooglePlayBillingActivity", "queryProductDetailsAsync billingResult ==>" + billingResult);
                    Log.e("GooglePlayBillingActivity", "queryProductDetailsAsync productDetailsList ==>" + productDetailsList.size());
                    if (productDetailsList.size() > 0) {
                        productDetails = productDetailsList;
                    }
                    for (int i = 0; i < productDetailsList.size(); i++) {
                        Log.e("productDetailsList",
                                "position: " + i +
                                        " ==>Info: " + productDetailsList.get(i));
                    }

                }
        );
    }

//    private void queryProducts() {
//        List<String> skuList = new ArrayList<>();
//
//        skuList.add("test.for.sub");
//
//        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//
//        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
//
//        client.querySkuDetailsAsync(params.build(),
//                (billingResult, skuDetailsList) -> {
//                    // Process the result.
//                    Log.e("GooglePlayBillingActivity", "queryProductDetailsAsync billingResult ==>" + billingResult);
//                    if (skuDetailsList != null)
//                        Log.e("GooglePlayBillingActivity", "queryProductDetailsAsync productDetailsList ==>" + skuDetailsList.size());
//                }
//        );
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == layoutBinding.submit.getId()) {
            submit();
        } else if (v.getId() == layoutBinding.prepare.getId()) {
            prepare();
        } else if (v.getId() == layoutBinding.login.getId()) {
            login();
        }
    }


    private void login() {

    }

    /**
     * 准备支付
     */
    private void prepare() {
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                .setProductDetails(productDetails.get(0))
                                // to get an offer token, call ProductDetails.getSubscriptionOfferDetails()
                                // for a list of offers that are available to the user
                                .setOfferToken(productDetails.get(0).getSubscriptionOfferDetails().get(0).getOfferToken())
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();
        // Launch the billing flow
        client.launchBillingFlow(this, billingFlowParams);

    }

    private void submit() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
