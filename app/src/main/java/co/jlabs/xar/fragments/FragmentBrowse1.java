package co.jlabs.xar.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.adapters.Adapter6ArtWork;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.views.NiceSpinner;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowse1 extends RootFragment {
    RecyclerView featured,browseby;
    String url= "http://arteryindia.com/api/featuredArtists/";
    String url1= "http://arteryindia.com/api/artists/sortBy/";
    private ProgressDialog pDialog,pDialog1;
    RecyclerView.LayoutManager layoutManager,layoutManager1;
    Context context;
    String alpha="a";
    NiceSpinner niceSpinner;
    List<String> dataset;

    public FragmentBrowse1() {
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
        context=getContext();
        View rootView = inflater.inflate(R.layout.fragment_browse1, container, false);
        layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager1 = new GridLayoutManager(getContext(),1);
        featured=(RecyclerView)rootView.findViewById(R.id.featured);
        browseby=(RecyclerView)rootView.findViewById(R.id.browseby);
        featured.setHasFixedSize(true);
        featured.setNestedScrollingEnabled(false);
        browseby.setHasFixedSize(true);
        browseby.setNestedScrollingEnabled(false);
        NiceSpinner niceSpinner = (NiceSpinner) rootView.findViewById(R.id.spinnerCustom);
        dataset = new LinkedList<>(Arrays.asList("A", "B", "C", "D", "E","F", "G", "H", "I", "J","K", "L", "M", "N", "O","P", "Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z"));
        niceSpinner.attachDataSource(dataset);
        getFeatured();
        getBrowsByName();
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    alpha=dataset.get(i);
                    Log.e("test3",""+dataset.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getBrowsByName();
            }
        });
        Log.e("now m at","brouse");
        rootView.findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragB();
            }
        });

        return rootView;
    }

    private void getFeatured(){

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());
                        showFeat(response);
//                        try {
//                            // Parsing json array response
//                            // loop through each json object
//                            jsonResponse = "";
//                            show_offers(response);
//
//                            //txtResponse.setText(jsonResponse);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
                        hidepDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Tag", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
    private void getBrowsByName(){

        pDialog1 = new ProgressDialog(getContext());
        pDialog1.setMessage("Please wait...");
        pDialog1.setCancelable(false);
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(url1+alpha,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());
                        showBrows(response);
//                        try {
//                            // Parsing json array response
//                            // loop through each json object
//                            jsonResponse = "";
//                            show_offers(response);
//
//                            //txtResponse.setText(jsonResponse);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
                        hidepDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Tag", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
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
    public void showFeat(JSONArray data)
    {

        featured.setAdapter(new RecyclerViewAdapter(context,1,data));
        featured.setLayoutManager(layoutManager);

    }
    public void showBrows(JSONArray data)
    {

        browseby.setAdapter(new RecyclerViewAdapter1(context,1,data));
        browseby.setLayoutManager(layoutManager1);

    }

    private static class RecyclerViewAdapter extends RecyclerView.Adapter<FakeViewHolder> {
        JSONArray data;
        Context context;
        int[] drawables;
        int[] text;
        int[] notif_count;
        JSONObject[] jsonObjects;





        public RecyclerViewAdapter(Context context,int index, JSONArray data ) {
            this.data=data;
            this.context=context;
            if (index==1) {
//                drawables = new int[] {
//                        R.drawable.settings,
//                        R.drawable.calendar,
//                        R.drawable.fambond_white,
//                        R.drawable.vault,
//                        R.drawable.contacts,
//                        R.drawable.feeds,
//                        R.drawable.polls,
//                        R.drawable.events,
//                        R.drawable.tasks
//                };
//                text = new int[] {
//                        R.string.settings,
//                        R.string.calendar,
//                        R.string.bonds,
//                        R.string.vault,
//                        R.string.contacts,
//                        R.string.feed,
//                        R.string.polls,
//                        R.string.events,
//                        R.string.tasks
//
//                };
//                notif_count = new int[] {
//                        R.string.settings1,
//                        R.string.calendar1,
//                        R.string.bonds1,
//                        R.string.vault1,
//                        R.string.contacts1,
//                        R.string.feed1,
//                        R.string.polls1,
//                        R.string.events1,
//                        R.string.tasks1
//                };
            }

        }

        @Override
        public FakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_featured_artist, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder holder, final int position) {
            JSONObject jo;




            try {
                holder.artistName.setText(""+data.getJSONObject(position).getString("artist_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String picas= null;
            try {
                picas = data.getJSONObject(position).getString("mugshot");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("dadada",""+picas);
            try {
                Picasso.with(context)
                        .load(picas)
                        .into( holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return data.length();
        }
    }
    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        BebasNeueTextView artistName;
        ImageView imageView;

        public FakeViewHolder(View itemView) {
            super(itemView);
            artistName = (BebasNeueTextView) itemView.findViewById(R.id.artist_name);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);

         }
    }


    private static class RecyclerViewAdapter1 extends RecyclerView.Adapter<FakeViewHolder1> {
        JSONArray data;
        Context context;
        int[] drawables;
        int[] text;
        int[] notif_count;
        JSONObject[] jsonObjects;





        public RecyclerViewAdapter1(Context context,int index, JSONArray data ) {
            this.data=data;
            this.context=context;
            if (index==1) {

            }

        }

        @Override
        public FakeViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FakeViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_browse_artist, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder1 holder, final int position) {
            JSONObject jo;




            try {
                holder.artistName.setText(""+data.getJSONObject(position).getString("artist_name"));
                holder.born.setText("Born "+data.getJSONObject(position).getInt("yob"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String picas;
            try {
                picas = data.getJSONObject(position).getString("mugshot");
            } catch (JSONException e) {
                picas = "http://jlabs.co/no_image.png";
                e.printStackTrace();
            }
            Log.e("dadada",""+picas);
            try {
                Picasso.with(context)
                        .load(picas)
                        .into( holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return data.length();
        }
    }
    private static class FakeViewHolder1 extends RecyclerView.ViewHolder {

        BebasNeueTextView artistName,born,follow;
        ImageView imageView;

        public FakeViewHolder1(View itemView) {
            super(itemView);
            artistName = (BebasNeueTextView) itemView.findViewById(R.id.name);
            born = (BebasNeueTextView) itemView.findViewById(R.id.textView1);
            follow = (BebasNeueTextView) itemView.findViewById(R.id.follow);
            imageView = (ImageView) itemView.findViewById(R.id.imageart);

        }
    }



    public void addFragB() {
        FragmentDash a2Fragment = new FragmentDash();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("B");
        transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();

    }



}