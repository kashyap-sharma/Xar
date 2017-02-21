package co.jlabs.xar.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;

import co.jlabs.xar.coordinatorlayouthelper.CoordinatorLayoutHelperViewPager;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.CustomViewPager;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.model.FragmentEvents;
import co.jlabs.xar.model.Image;
import co.jlabs.xar.model.ReceiverInterface;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseProfile extends RootFragment implements FragmentEvents {
    private TabLayout tabLayout;
    CustomViewPager viewPager;
    ViewPagerAdapter adapter;
    String desired_string;
    Context context;
    BebasNeueTextView artistName;
    private ProgressDialog pDialog,pDialog1;
    View tabFragment1;

    String url="http://arteryindia.com/api/artistInfo";
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    public FragmentBrowseProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context=this.getContext();
        Bundle arguments = getArguments();
         desired_string = arguments.getString("artist_id");
        Log.e("mynigga",desired_string);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dash, container, false);
        View rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);
        getArtistan();
        artistName=(BebasNeueTextView)rootView.findViewById(R.id.textView1);
        tabLayout=(TabLayout)rootView.findViewById(R.id.tab_layout);
        viewPager=(CustomViewPager)rootView.findViewById(R.id.viewpager);


//        rootView.findViewById(R.id.by_artist).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFragB();
//            }
//        });

        return rootView;
    }
    private void setupTabIcons() {

        RelativeLayout ll1 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab1, null);
        BebasNeueTextView tabOne1=(BebasNeueTextView)ll1.findViewById(R.id.taba) ;
        tabOne1.setText("profile");
        tabLayout.getTabAt(0).setCustomView(ll1);

        RelativeLayout ll2 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab1, null);
        BebasNeueTextView tabOne2=(BebasNeueTextView)ll2.findViewById(R.id.taba) ;
        tabOne2.setText("works");
        TextView danda=(TextView) ll2.findViewById(R.id.danda) ;
        danda.setVisibility(View.GONE);
        tabLayout.getTabAt(1).setCustomView(ll2);


    }

    private void setupViewPager(ViewPager viewPager) {
        Log.e("now m at","dash");

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentBrowseProfileDetail(), "BROWSE");
        adapter.addFragment(new FragmentBrowseProfileWorks(), "DASHBOARD");

        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("now m at","dash");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public void getArtistan(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            //JSONArray jsonArray=jsonObject.getJSONArray("data");
                            Log.e("hella",""+jsonArray.toString());
                            Image image = new Image();
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            image.setJsonArray(jsonObject);
                            showDetailed(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());

            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("artist_id", desired_string);
                Log.e("ssas",""+params.toString());
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }
    private void showpDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }
        else if(!pDialog1.isShowing()){
            pDialog1.show();
        }

    }
    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }

        else if(pDialog1.isShowing()){
            pDialog1.dismiss();
        }
    }
    public void showDetailed(JSONArray jsonArray){
        try {
            JSONObject jsonObject= jsonArray.getJSONObject(0);
           // ((FragmentEvents) this.context).getJson(jsonObject);

            artistName.setText(""+jsonObject.getString("artist_name"));
//            Image image = new Image();
//            image.setJsonArray(jsonObject);
          //  Log.e("somecha",""+image.getJsonArray().toString());
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
            ((ReceiverInterface) adapter.getItem(0)).receiveMessage(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getJson(JSONObject message)
    {
        System.out.println(message.toString());
        ((ReceiverInterface) adapter.getItem(0)).receiveMessage(message);
    }

}