package com.night.chat;

import android.app.Activity;
import android.app.ListFragment;

/**
 * Created by nightrain on 10/17/15.
 */
public class BaseListFragment extends ListFragment {
    protected OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
