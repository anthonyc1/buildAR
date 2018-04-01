package com.endercrest.arbuild;

import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.endercrest.arbuild.common.api.APIStep;
import com.endercrest.arbuild.common.data.ARObject;
import com.endercrest.arbuild.common.data.MaterialProperties;
import com.endercrest.arbuild.common.helpers.TapHelper;
import com.google.ar.core.Anchor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ARStepActivity extends ARActivity implements Step.OnFragmentInteractionListener {

    private UUID arUUID;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    CalibrateFragment calibrateFragment;

    TapHelper tapHelper;
    List<APIStep> apiSteps;

    @Override
    protected int getContentView() {
        return R.layout.activity_arstep;
    }

    @Override
    protected int getSurfaceViewId() {
        return R.id.surfaceview;
    }

    @Override
    protected void tapEvent(Anchor anchor) {
        if(arUUID == null) {
            ARObject arObject = new ARObject("", anchor);
            arUUID = arObject.getUuid();

            placeObject(arObject);
            System.out.println("Placing Object" + anchor.toString());
        } else {
            removeObject(arUUID);

            arUUID = null;
        }
    }

    @Override
    protected MotionEvent pollClick() {
        return tapHelper.poll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tapHelper = new TapHelper(/*context=*/ this);


        apiSteps = new ArrayList<>();
        try {
            apiSteps.add(new APIStep("Lets get Started!", "Some BS Instructions", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Move table legs", "Move your table legs and place your tabletop facing down.", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png")));
            apiSteps.add(new APIStep("Place first leg", "Some BS Instructions", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png")));
            apiSteps.add(new APIStep("Place second leg", "Some BS Instructions", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png")));
            apiSteps.add(new APIStep("Place third leg", "Some BS Instructions", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png")));
            apiSteps.add(new APIStep("Place fourth leg", "Some BS Instructions", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), apiSteps);
        mViewPager = findViewById(R.id.pager);
        calibrateFragment = (CalibrateFragment) getFragmentManager().findFragmentById(R.id.calibrate);
        if (!isCalibrating) {
            calibrateFragment.hide();
        }



        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                System.out.println("Page Selected" + position);

                if(arUUID == null) {
                    return;
                }

                ARStepActivity.this.updateModel(arUUID, "step" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //mViewPager.setOnTouchListener(tapHelper);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Touch!");
                return surfaceView.dispatchTouchEvent(event);
            }
        });

    }

    @Override
    protected void updateCalibrateStatus(boolean calibrating) {
        super.updateCalibrateStatus(calibrating);
        if(calibrating) {
            calibrateFragment.show();
            runOnUiThread(new Runnable() {
                public void run() {
                    mViewPager.setVisibility(View.GONE);
                }
            });
            //mViewPager.setVisibility(View.GONE);
        } else {
            calibrateFragment.hide();
            runOnUiThread(new Runnable() {
                public void run() {
                    mViewPager.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    protected void loadAssets() throws IOException {
        for (int i = 0; i < apiSteps.size(); i++) {
            APIStep step = apiSteps.get(i);

            loadObject("step"+i, step.getObjStream(), step.getTextureStream(),
                    new MaterialProperties(0.0f, 2.0f, 0.5f, 6.0f));
        }

        loadObject("shiny", this.getAssets().open("models/table_complete.obj"), this.getAssets().open("models/andy.png"),
                new MaterialProperties(0.0f, 2.0f, 0.5f, 6.0f));
        loadObject("idk", this.getAssets().open("models/andy.obj"), this.getAssets().open("models/andy.png"),
                    new MaterialProperties(0.9f, 0.6f, 0.0f, 0.0f));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        List<APIStep> steps;

        DemoCollectionPagerAdapter(FragmentManager fm, List<APIStep> steps) {
            super(fm);
            this.steps = steps;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            APIStep step = steps.get(i);

            return Step.newInstance(step.getTitle(), step.getMsg());
        }

        @Override
        public int getCount() {
            return steps.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }



}
