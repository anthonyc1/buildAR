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

    OkHttpClient client = new OkHttpClient();
    public String url = "https://jeffzh4ng.lib.id/furniture@dev/get-furniture/?id=0";
    TextView myText;

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myText = (TextView)findViewById(R.id.myText);

        try {
            run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // This the the text obtained from GET request
                final String myResponse = response.body().string();
                final String name, obj_link, img_link, texture_link;

                // Convert obtained text to JSON object
                try {
                    JSONObject jsonObject = new JSONObject(myResponse);
                    name = jsonObject.getString("name");
                    obj_link = jsonObject.getString("obj_link");
                    img_link = jsonObject.getString("img_link");
                    texture_link = jsonObject.getString("texture_link");

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
        });
    }
}
