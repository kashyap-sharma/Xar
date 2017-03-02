package co.jlabs.xar.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.Checkas;
import co.jlabs.xar.R;
import co.jlabs.xar.adapters.EndlessRecyclerViewScrollListener;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.functions.JSONfunctions;
import co.jlabs.xar.functions.Static_Catelog;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseListsEnter extends RootFragment  {
    LinearLayoutManager layoutManager,layoutManager1;
    Context context;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RecyclerView recycler;
    private ProgressDialog pDialog;
    int i=1;
    RecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    JSONArray jsonArray=new JSONArray();
    public FragmentBrowseListsEnter() {
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
        adapter =new RecyclerViewAdapter(context,1,jsonArray);
        View rootView = inflater.inflate(R.layout.fragment_browse_lis_enter, container, false);
        recycler=(RecyclerView)rootView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(context);
//        recycler.setHasFixedSize(true);
//        recycler.setNestedScrollingEnabled(false);

        recycler.setLayoutManager(layoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.e("hmm","sasa");

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(adapter.getItemCount(), 500);
                    }
                });

                loadNextDataFromApi(page+1);
            }
        };



        scrollListener.resetState();
        recycler.addOnScrollListener(scrollListener);
       // new JSONParse().execute(""+1);
        //getList();

        return rootView;
    }


    public void loadNextDataFromApi(int page){
        Log.e("hmm","sasas");
        new JSONParse().execute(""+page);
    }




    public void showFeat(JSONArray data)
    {

       // https://github.com/ogrebgr/android_volley_examples/blob/master/src/com/github/volley_examples/misc/PicasaArrayAdapter.java

    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            uid = (TextView)findViewById(R.id.uid);
//            name1 = (TextView)findViewById(R.id.name);
//            email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONfunctions jParser = new JSONfunctions();
            String a = args[0];
            // Getting JSON from URL
            JSONObject json = jParser.getJSONfromURL("http://220.227.105.55/artapi/paintings/page/"+a);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                JSONArray jsonArray=json.getJSONArray("data");
                showFeat(jsonArray);
                recycler.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Getting JSON Array
//                user = json.getJSONArray(TAG_USER);
            Log.e("saasas",""+json.toString());

            // Storing  JSON item in a Variable
//                String id = c.getString(TAG_ID);
//                String name = c.getString(TAG_NAME);
//                String email = c.getString(TAG_EMAIL);

            //Set JSON Data in TextView
//                uid.setText(id);
//                name1.setText(name);
//                email1.setText(email);


        }
    }


    public void addFragB(int id) {
        FragmentBrowseProfile a2Fragment = new FragmentBrowseProfile();
        Bundle arguments = new Bundle();
        //arguments.putString( "artist_id" , id);
        a2Fragment.setArguments(arguments);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("B");
        transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();

    }



    private  class RecyclerViewAdapter extends RecyclerView.Adapter<FakeViewHolder> {
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
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_top_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder holder, final int position) {
            JSONObject jo;
            try {
                holder.artist_name.setText(""+data.getJSONObject(position).getString("ARTIST"));
                holder.rank.setText(""+position+1);
                holder.title.setText(""+data.getJSONObject(position).getString("TITLE"));
                holder.details.setText(""+data.getJSONObject(position).getString("MEDIUM")+" on "+data.getJSONObject(position).getString("MATERIAL")+" - "+"size: "+data.getJSONObject(position).getString("SIZE_W_IN")+" X "+data.getJSONObject(position).getString("SIZE_H_IN")+" IN.");
                JSONObject jsonObject=data.getJSONObject(position).getJSONObject("SOLD_PRICE");
                holder.rupee.setText("Rs "+jsonObject.getInt("INR"));
                holder.dollar.setText("$ "+jsonObject.getInt("USD"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String picas= null;
            try {
                picas = data.getJSONObject(position).getString("IMAGE");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("dadada",""+picas);
            try {
                Picasso.with(context)
                        .load(picas)
                        .into( holder.imageart);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            final int ids;
//            try {
//                ids = data.getJSONObject(position).getInt("artist_id");
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        addFragB(""+ids);
//                    }
//                });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }



        }

        @Override
        public int getItemCount() {
            return data.length();
        }
    }
    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        BebasNeueTextView artist_name,rank,title,details,rupee,dollar;
        ImageView imageart,white_heart,white_zoom;

        public FakeViewHolder(View itemView) {
            super(itemView);
            artist_name = (BebasNeueTextView) itemView.findViewById(R.id.artist_name);
            rank = (BebasNeueTextView) itemView.findViewById(R.id.rank);
            title = (BebasNeueTextView) itemView.findViewById(R.id.title);
            details = (BebasNeueTextView) itemView.findViewById(R.id.details);
            rupee = (BebasNeueTextView) itemView.findViewById(R.id.rupee);
            dollar = (BebasNeueTextView) itemView.findViewById(R.id.dollar);
            imageart = (ImageView) itemView.findViewById(R.id.imageart);
            white_heart = (ImageView) itemView.findViewById(R.id.white_heart);
            white_zoom = (ImageView) itemView.findViewById(R.id.white_zoom);

        }
    }



}