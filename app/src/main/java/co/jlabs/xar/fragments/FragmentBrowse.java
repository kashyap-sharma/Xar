package co.jlabs.xar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.jlabs.xar.R;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowse extends Fragment {

    public FragmentBrowse() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addFragB();



        return inflater.inflate(R.layout.fragment_browse, container, false);
    }

    public void addFragB() {
        FragmentManager childFragMan = getChildFragmentManager();

        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        FragmentDash fragB = new FragmentDash();
        childFragTrans.add(R.id.fragA_LinearLayout, fragB);
        childFragTrans.addToBackStack("B");
        childFragTrans.commit();

    }
}