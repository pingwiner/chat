package com.night.chat.network;

/**
 * Created by nightrain on 10/18/15.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.night.chat.MainActivity;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

import retrofit.RestAdapter;
import retrofit.converter.Converter;

public class CustomSpiceService extends RetrofitJackson2SpiceService {
    public static final String SERVER_URL = "http://5.164.185.197/";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(Api.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return super.createRestAdapterBuilder()
                .setClient(NetworkManager.getInstance(this).getClient())
                .setLogLevel(RestAdapter.LogLevel.FULL);
    }

    @Override
    protected String getServerUrl() {
        return SERVER_URL;
    }

    @Override
    protected Converter createConverter() {
        return new CustomConverter(new ObjectMapper());
    }
}
