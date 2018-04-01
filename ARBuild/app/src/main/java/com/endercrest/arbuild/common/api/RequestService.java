package com.endercrest.arbuild.common.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestService {

    public static final String API_URL = "https://jeffzh4ng.lib.id/furniture@dev/";

    private static OkHttpClient httpClient;

    private static OkHttpClient getHttpClient() {
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }
        return httpClient;
    }

    public static APIFurniture requestFurniture(String barcode) {
        final String REQUEST_URL = API_URL + String.format("get-furniture/?id=%s", barcode);

        Request request = new Request.Builder().url(REQUEST_URL).build();

        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //func2(response);
            }
        });
        return null;
    }

    public static APISteps requestSteps(String barcode) {
        final String REQUEST_URL = API_URL + String.format("get-steps/?id=%s", barcode);

        Request request = new Request.Builder().url(REQUEST_URL).build();

        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return null;
    }
}
