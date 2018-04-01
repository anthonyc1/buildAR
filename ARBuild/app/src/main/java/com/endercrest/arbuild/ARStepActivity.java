package com.endercrest.arbuild;

import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.endercrest.arbuild.common.data.ARObject;
import com.endercrest.arbuild.common.data.MaterialProperties;
import com.endercrest.arbuild.common.helpers.TapHelper;
import com.google.ar.core.Anchor;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ARStepActivity extends ARActivity implements Step.OnFragmentInteractionListener {

    private UUID arUUID;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    TapHelper tapHelper;

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
            ARObject arObject = new ARObject("shiny", anchor);
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

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(position);

                if(arUUID == null) {
                    return;
                }

                long val = Math.round(Math.random());
                if (val == 1) {
                    ARStepActivity.this.updateModel(arUUID, "shiny");
                } else {
                    ARStepActivity.this.updateModel(arUUID, "idk");
                }
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
    protected void loadAssets() throws IOException {
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
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return Step.newInstance("Testing", "Some description");
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }



}
