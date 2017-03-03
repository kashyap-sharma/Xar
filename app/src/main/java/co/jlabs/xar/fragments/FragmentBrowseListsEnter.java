package co.jlabs.xar.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.Checkas;
import co.jlabs.xar.R;
import co.jlabs.xar.activity_area.KindaHome;
import co.jlabs.xar.adapters.EndlessRecyclerViewScrollListener;
import co.jlabs.xar.bigImage.BigImageViewer;
import co.jlabs.xar.bigImage.view.BigImageView;
import co.jlabs.xar.custom_views.BebasNeueButton;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.functions.JSONfunctions;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.glide.GlideImageLoader;
import co.jlabs.xar.progresspie.ProgressPieIndicator;


/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseListsEnter extends RootFragment  {

    RecyclerView recycler;
    private ProgressDialog pDialog;
    int i=1;
    RecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout tip;

    public FragmentBrowseListsEnter() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BigImageViewer.initialize(GlideImageLoader.with(getContext()));
        View rootView = inflater.inflate(R.layout.fragment_browse_lis_enter, container, false);
        recycler=(RecyclerView)rootView.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getContext(),1);
        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);
        getTop500();


        return rootView;
    }


    private void getTop500(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/top500work",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONArray jsonArray1=new JSONArray(response);
                           // JSONObject jsonObject=new JSONObject(response);
                           // JSONArray jsonArray=jsonObject.getJSONArray("data");
                            Log.e("hella",""+jsonArray1.toString());
                            showTop500(jsonArray1);
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

        })


        {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Static_Catelog.getStringProperty(getContext(),"user_id"));
                params.put("sortby", "USD");
                params.put("access_token", Static_Catelog.getStringProperty(getContext(),"access_token"));
                Log.e("ssas",""+params.toString());
                return params;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjRequest.setRetryPolicy(policy);
        mRequestQueue.add(jsonObjRequest);


        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


    public void showTop500(JSONArray data)
    {

        recycler.setAdapter(new RecyclerViewAdapter(getContext(),1,data));
        recycler.setLayoutManager(layoutManager);

    }

    private void showpDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }

    }
    private void hidepDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }








    private  class RecyclerViewAdapter extends RecyclerView.Adapter<FakeViewHolder> {
        JSONArray data;
        Context context;






        public RecyclerViewAdapter(Context context,int index, JSONArray data ) {
            this.data=data;
            this.context=context;


        }

        @Override
        public FakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_top_list_items, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder holder, final int position) {
            JSONObject jo;
            try {
                holder.nigga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addFragB(data.toString());
                    }
                });
                holder.artist_name.setText(""+data.getJSONObject(position).getString("artist_name"));
                int tip=position+1;
                Log.e("Muchas"+""+data.getJSONObject(position).getString("artist_name"),""+tip);
                holder.rank.setText(""+tip);
                holder.title.setText(""+data.getJSONObject(position).getString("title"));
                holder.details.setText(""+data.getJSONObject(position).getString("medium")+" on "+data.getJSONObject(position).getString("material")+" - "+"size: "+data.getJSONObject(position).getInt("size_w")+" X "+data.getJSONObject(position).getInt("size_h")+" IN.");
                //JSONObject jsonObject=data.getJSONObject(position).getJSONObject("SOLD_PRICE");
                holder.rupee.setText("Rs "+data.getJSONObject(position).getInt("sold_price_inr"));
                holder.dollar.setText("$ "+data.getJSONObject(position).getInt("sold_price_usd"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String picas= null;
            try {
                picas = data.getJSONObject(position).getString("painting_thumb_image");
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
            holder.white_zoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    BigImageViewer.initialize(GlideImageLoader.with(getContext()));
//                    final Dialog openDialog = new Dialog(getContext());
//                    openDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    openDialog.setContentView(R.layout.dialog_zoomee_layout);
//                    openDialog.setCancelable(true);
//
//                    BigImageView mBigImage = (BigImageView) openDialog.findViewById(R.id.mBigImage);
//                    mBigImage.setProgressIndicator(new ProgressPieIndicator());
//                    mBigImage.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_CROP);
//                    try {
//                        mBigImage.showImage(Uri.parse(data.getJSONObject(position).getString("painting_image")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
////                    next.setOnClickListener(new View.OnClickListener(){
////                        @Override
////                        public void onClick(View v) {
////                            // TODO Auto-generated method stub
////                            Intent intent =new Intent(context, KindaHome.class);
////                            context.startActivity(intent);
////                            Static_Catelog.setStringProperty(context,"five_art","five");
////                            ((Activity)context).finish();
////                        }
////                    });
//                    openDialog.show();
                }
            });




        }

        @Override
        public int getItemCount() {
            Log.e("muchas",""+data.length());
            return data.length();
        }
    }
    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        BebasNeueTextView artist_name,rank,title,details,rupee,dollar;
        ImageView imageart,white_heart,white_zoom;
        RelativeLayout nigga;

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
            nigga=(RelativeLayout)itemView.findViewById(R.id.nigga);

        }
    }



    public void addFragB(String id) {

        FragmentBrowseListsEntered a2Fragment = new FragmentBrowseListsEntered();
        Bundle arguments = new Bundle();
        arguments.putString( "jsonArray" , id);

        a2Fragment.setArguments(arguments);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("C");
        transaction.replace(R.id.fragA_LinearLayou, a2Fragment).commit();
    }



}