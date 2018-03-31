package com.endercrest.arbuild;

import android.os.Bundle;

public class ARStepActivity extends ARActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_arstep;
    }

    @Override
    protected int getSurfaceViewId() {
        return R.id.surfaceview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
