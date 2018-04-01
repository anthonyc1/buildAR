package com.endercrest.arbuild;

import android.content.Intent;
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

public class ARStepActivity extends ARActivity implements Step.OnFragmentInteractionListener, StepFinishFragment.OnFragmentInteractionListener {

    private UUID arUUID;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    CalibrateFragment calibrateFragment;

    TapHelper tapHelper;
    List<APIStep> apiSteps;

    int step = 0;

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
            ARObject arObject = new ARObject("step"+step, anchor);
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
            apiSteps.add(new APIStep("Let's get Started!", "Check to ensure that you have all the necessary items.", this.getAssets().open("models/table_step0.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Move table legs", "Move your table legs and place your tabletop facing down.", this.getAssets().open("models/table_step1.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Place first leg", "Attach table leg of your favorite corner.", this.getAssets().open("models/table_step2.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Place second leg", "Attach next table leg.", this.getAssets().open("models/table_step3.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Place third leg", "Attach third table leg.", this.getAssets().open("models/table_step4.obj"), this.getAssets().open("models/wood.png")));
            apiSteps.add(new APIStep("Place fourth leg", "Attach final table leg. ", this.getAssets().open("models/table_step5.obj"), this.getAssets().open("models/wood.png")));
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

                step = position;
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
                    new MaterialProperties(0.3f, 0.6f, 0.0f, 0.0f));
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRepeatClick() {
        Intent intent = new Intent(getBaseContext(), BarcodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onHomeClick() {
        Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
        startActivity(intent);
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
            if (i >= steps.size()) {
               return StepFinishFragment.newInstance();
            }

            APIStep step = steps.get(i);

            return Step.newInstance(step.getTitle(), step.getMsg());
        }

        @Override
        public int getCount() {
            return steps.size() + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }



}
