package com.example.evalycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {


    Button calculateButton,newCalculationButton;
    EditText editTextPrice,editTextPercent;
    TextView resultFull,resultPart,fullPayment,partialPatment;
    private AdView mAdView;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPrice = findViewById(R.id.queryPrice);
        editTextPercent = findViewById(R.id.queryPercent);
        calculateButton = findViewById(R.id.calculator);
        newCalculationButton = findViewById(R.id.newCalculation);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "Ad lodded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressCalculateBtn();
            }
        });

        newCalculationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resultFull.setText(" ");
               editTextPrice.getText().clear();
               editTextPercent.getText().clear();
               resultPart.setText(" ");
               fullPayment.setText(" ");
               partialPatment.setText(" ");
            }
        });

    }

    private void onPressCalculateBtn() {
        try {
            String price = editTextPrice.getText().toString();
            String percent = editTextPercent.getText().toString();
            calculation(price, percent);

        }catch (Exception e){
            Log.d(" sd", "handling");
            e.printStackTrace();
        }
    }

    private void calculation(String price, String percent){
        resultFull = findViewById(R.id.resultFull);
        resultPart = findViewById(R.id.resultPart);
        fullPayment = findViewById(R.id.fullPayment);
        partialPatment = findViewById(R.id.partialPayment);

        int priceInt = Integer.parseInt(price);
        int percentInt =Integer.parseInt(percent);

        double resultPar = partial(priceInt,percentInt);
        double resultPercent = percent(priceInt,percentInt);


        resultFull.setText("If you pay full "+priceInt+"TK then you'll get "+(int)Math.round(resultPercent)+"TK cashback for "+percentInt+"% cashback offer. So your product price will be "+(priceInt-(int)Math.round(resultPercent)));
        resultPart.setText("For partial payment, you've to pay "+(int)Math.round(resultPar)+"TK for "+percentInt+"% cashback offer. So you'll get "+(priceInt-(int)Math.round(resultPar))+"TK discount");
        fullPayment.setText("Full Payment");
        partialPatment.setText("Partial Payment");
    }

    private double partial(int pricePar,int percentPar){
        if(percentPar > 0 && percentPar <= 100 && percentPar % 10 == 0) {
            return pricePar/calculatePartialDenominator(percentPar);
        }
        return 0;

    };

    private double calculatePartialDenominator(double val) {
        return (1 + (val * 1 / 100));
    }

    private double percent(int pricePerc, int percePerce){
      return (pricePerc*percePerce)/100;
    };

    private void display(){

    }





}
