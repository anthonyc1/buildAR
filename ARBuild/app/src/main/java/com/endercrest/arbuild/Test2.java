package com.endercrest.arbuild;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import com.endercrest.arbuild.BackendService.*;

public class Test2 extends AppCompatActivity {

    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView myText = new TextView(this);
        myText.setText("Hello World!");
        setContentView(myText);

//        setContentView(R.layout.activity_main);
//        myText = (TextView) findViewById(R.id.myText);

//        String barcode = "0";
//        String getreq_url = "https://jeffzh4ng.lib.id/furniture@dev/get-furniture/?id=";
//
//        BackendService bs = new BackendService();
//        bs.getRequest(getreq_url, barcode);
    }

}
