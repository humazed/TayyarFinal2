package com.example.muhammad.tayyarfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.appspot.tayyar_trial.customerApi.CustomerApi;
import com.appspot.tayyar_trial.customerApi.model.CollectionResponseMerchantView;
import com.appspot.tayyar_trial.customerApi.model.MerchantView;
import com.appspot.tayyar_trial.merchantApi.model.MerchantCollection;
import com.appspot.tayyar_trial.merchantApi.model.Pharmacy;
import com.appspot.tayyar_trial.merchantApi.model.Restaurant;
import com.example.muhammad.tayyarfinal.okkhttp.OkHttpTransport;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    System.out.println(MainActivity.this.createTestRestaurant());
                    System.out.println("waiting ...");
                    Thread.sleep(2000);
                    for (MerchantView merchantView : getListOfMerchants("null").getItems()) {
                        System.out.println(merchantView);
                    }
                    System.out.println(getListOfMerchants("null").getNextPageToken());

                    System.out.println("again aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    String cursStr = getListOfMerchants("null").getNextPageToken();
                    for (MerchantView merchantView : getListOfMerchants(cursStr).getItems()) {
                        System.out.println(merchantView);
                    }

                } catch (IOException e) {
                    Log.e(TAG, "onCreate: ", e);
                } catch (InterruptedException e) {
                    Log.e(TAG, "onCreate: ", e);
                }
            }
        }).start();
    }

    private MerchantCollection createTestRestaurant() throws IOException {
        com.appspot.tayyar_trial.merchantApi.MerchantApi merchantApi = new com.appspot.tayyar_trial.merchantApi.MerchantApi
                .Builder(new OkHttpTransport().createRequestFactory().getTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8085/_ah/api/")
                //                .setRootUrl("http://10.0.2.2:8086/_ah/api/")
                //                .setRootUrl("https://tayyar-trial.appspot.com/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> clientRequest) throws IOException {
                        clientRequest.setDisableGZipContent(true);
                    }
                })
                .build();


        return merchantApi.createRandomMerchants().execute();
    }

    private CollectionResponseMerchantView getListOfMerchants(String curstr) throws IOException {
        com.appspot.tayyar_trial.customerApi.CustomerApi customerApi = new com.appspot.tayyar_trial.customerApi.CustomerApi
                .Builder(new OkHttpTransport().createRequestFactory().getTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8085/_ah/api/")
                //                .setRootUrl("http://10.0.2.2:8086/_ah/api/")
                //                .setRootUrl("https://tayyar-trial.appspot.com/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> clientRequest) throws IOException {
                        clientRequest.setDisableGZipContent(true);
                    }
                })
                .build();


        return customerApi.getListOfMerchantsViewsOrderedBy(curstr, "pricing", "restaurant", 50).execute();
    }


}
