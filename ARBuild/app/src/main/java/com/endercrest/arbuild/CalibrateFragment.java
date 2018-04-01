package com.endercrest.arbuild;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalibrateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalibrateFragment extends Fragment {

    private RelativeLayout layout;

    public CalibrateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalibrateFragment.
     */
    public static CalibrateFragment newInstance() {
        CalibrateFragment fragment = new CalibrateFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calibrate, container, false);
        layout = rootView.findViewById(R.id.container);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void show() {
        layout.setVisibility(View.VISIBLE);
    }

    public void hide() {
        layout.setVisibility(View.INVISIBLE);
    }
}
