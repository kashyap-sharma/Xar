package co.jlabs.xar.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.bigImage.BigImageViewer;
import co.jlabs.xar.bigImage.view.BigImageView;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.views.NiceSpinner;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.glide.GlideImageLoader;
import co.jlabs.xar.progresspie.ProgressPieIndicator;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseLists extends RootFragment  {
    RecyclerView.LayoutManager layoutManager,layoutManager1;
    Context context;


    public FragmentBrowseLists() {
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
//        addFragB();
        // Inflate the layout for this fragment

        Intent intent = getActivity().getIntent();

        try {
            if (intent.getDataString().matches(
                    "http:\\/\\/((www.)?)arteryindia.com\\/.*top500work\\/USD")) {
                Log.e("hello","I a");
                addFragB(1);
                // is match - do stuff
            } else if (intent.getDataString().matches(
                    "http:\\/\\/((www.)?)arteryindia.com\\/.*top50artists")) {
                Log.e("hello","I a");
                addFragB(2);
                // is match - do stuff
            }
            else {
                // is not match - do other stuff
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        context=getContext();
        View rootView = inflater.inflate(R.layout.fragment_browse_lis, container, false);
        rootView.findViewById(R.id.top_500).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragB(1);
            }
        });
        rootView.findViewById(R.id.top_100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragB(2);
            }
        });
        rootView.findViewById(R.id.top_100_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragB(2);
            }
        });
        return rootView;
    }






    public void addFragB(int id) {

        if (id==1) {
            FragmentBrowseListsEnter a2Fragment = new FragmentBrowseListsEnter();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // Store the Fragment in stack
            transaction.addToBackStack("B");
            transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();
        } else {
            FragmentBrowseListsEnter1 a2Fragment = new FragmentBrowseListsEnter1();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // Store the Fragment in stack
            transaction.addToBackStack("B");
            transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();
        }
//        Bundle arguments = new Bundle();
//        //arguments.putString( "artist_id" , id);
//        a2Fragment.setArguments(arguments);


    }




}