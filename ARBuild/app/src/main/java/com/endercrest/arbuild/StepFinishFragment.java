package com.endercrest.arbuild;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFinishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFinishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFinishFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StepFinishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StepFinishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepFinishFragment newInstance() {
        StepFinishFragment fragment = new StepFinishFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_step_finish, container, false);
        rootView.findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomePressed();
            }
        });
        rootView.findViewById(R.id.replayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestartPressed();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onHomePressed() {
        if (mListener != null) {
            mListener.onHomeClick();
        }
    }

    public void onRestartPressed(){
        if (mListener != null) {
            mListener.onRepeatClick();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRepeatClick();

        void onHomeClick();
    }
}
