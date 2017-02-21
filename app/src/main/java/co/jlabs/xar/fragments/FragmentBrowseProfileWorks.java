package co.jlabs.xar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import co.jlabs.xar.R;
import co.jlabs.xar.model.Image;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseProfileWorks extends RootFragment {
    JSONArray jsonArray;
    public FragmentBrowseProfileWorks() {
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
        Image image = new Image();

        View rootView = inflater.inflate(R.layout.fragment_profile_works, container, false);
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