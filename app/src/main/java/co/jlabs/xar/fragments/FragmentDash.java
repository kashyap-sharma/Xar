package co.jlabs.xar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.jlabs.xar.R;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentDash extends RootFragment {

    public FragmentDash() {
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
//        return inflater.inflate(R.layout.fragment_dash, container, false);
        View rootView = inflater.inflate(R.layout.fragment_dash, container, false);
        Log.e("now m at","dash");
//        rootView.findViewById(R.id.by_artist).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFragB();
//            }
//        });

        return rootView;
    }

}