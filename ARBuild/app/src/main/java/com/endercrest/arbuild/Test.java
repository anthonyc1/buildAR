package com.endercrest.arbuild;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

public class Test  extends AppCompatActivity {

    String barcode = "0";
    String getreq_url = "https://jeffzh4ng.lib.id/furniture@dev/get-furniture/?id=";

    String file_url = "http://endercrest.com/hackprinceton/";
    String obj_ext = "andy.obj";
    String png_ext = "andy.png";

    OkHttpClient client = new OkHttpClient();
    TextView myText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myText = (TextView) findViewById(R.id.myText);

        //getRequest(getreq_url, barcode);
        getRequest(file_url, obj_ext);
    }

    // Call this method
    void getRequest(String url, String ext) {
        try {
            run(url, ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void run(String url, String ext) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url + ext)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                func2(response);
            }
        });
    }

    void func1(Response response) {
        // Convert obtained text to JSON object
        try {
            // This the the text obtained from GET request
            final String myResponse = response.body().string();
            final String name, obj_link, img_link, texture_link;

            JSONObject jsonObject = new JSONObject(myResponse);

            // Values
            name = jsonObject.getString("name");
            obj_link = jsonObject.getString("obj_link");
            img_link = jsonObject.getString("img_link");
            texture_link = jsonObject.getString("texture_link");

            // Output to activity
            Test.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myText.setText(name);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void func2(Response response) {
        // Convert obtained text to JSON object
        try {
            final String myResponse = response.body().string();
            // Output to activity
            Test.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myText.setText(myResponse);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

