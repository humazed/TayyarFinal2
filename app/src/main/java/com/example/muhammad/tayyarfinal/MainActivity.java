package com.example.muhammad.tayyarfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appspot.tayyar_trial.customerApi.model.CollectionResponseMerchantView;
import com.appspot.tayyar_trial.merchantApi.MerchantApi;
import com.appspot.tayyar_trial.merchantApi.model.MerchantCollection;
import com.example.muhammad.tayyarfinal.okkhttp.OkHttpTransport;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MerchantApi.Builder builder = new MerchantApi.Builder(new OkHttpTransport().createRequestFactory().getTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
//                        .setRootUrl("https://hello-muhammad.appspot.com/_ah/api/")
                        .setRootUrl("https://tayyar-quota.appspot.com/_ah/api/")
                        //                       .setRootUrl("http://10.0.2.2:8086/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                MerchantApi merchantApiService = builder.build();

                try {
                    List<String> merchantCategoriesNames = merchantApiService.createInventoryCategories().execute().getMerchantCategoriesNames();
                    Log.d(TAG, "merchantCategoriesNames = " + merchantCategoriesNames);

                } catch (IOException e) {
                    e.printStackTrace();
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
