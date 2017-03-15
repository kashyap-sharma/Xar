package co.jlabs.xar.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.Checkas;
import co.jlabs.xar.R;
import co.jlabs.xar.activity_area.KindaHome;
import co.jlabs.xar.activity_area.Login;
import co.jlabs.xar.adapters.EndlessRecyclerViewScrollListener;
import co.jlabs.xar.bigImage.BigImageViewer;
import co.jlabs.xar.bigImage.view.BigImageView;
import co.jlabs.xar.custom_views.BebasNeueButton;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.Fontasm;
import co.jlabs.xar.functions.JSONfunctions;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.glide.GlideImageLoader;
import co.jlabs.xar.progresspie.ProgressPieIndicator;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseListsEnter extends RootFragment  {
    boolean b=false;

    RecyclerView recycler;
    private ProgressDialog pDialog;
    int i=1;
    RecyclerViewAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout tip;
    Fontasm fbTop500,tweetTop500;
    //ShareButton shareButton;
    ShareLinkContent content;
    private LoginManager manager;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
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
        fbTop500=(Fontasm)rootView.findViewById(R.id.fb_top500);
        tweetTop500=(Fontasm)rootView.findViewById(R.id.twitter_top500);

        FacebookSdk.sdkInitialize(this.getContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
       // List<String > permissionNeeds = Arrays.asList("publish_actions");

        //this loginManager helps you eliminate adding a LoginButton to your UI
        //manager = LoginManager.getInstance();

        //manager.logInWithPublishPermissions(this, permissionNeeds);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result loginResult) {


                Log.e("nailed it", "oit");


            }


            @Override
            public void onCancel() {
                Log.d("user2", "hello2");

            }

            @Override
            public void onError(FacebookException exception) {


            }
        });

//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Hello Facebook")
//                    .setContentDescription(
//                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                    .build();
//
//            shareDialog.show(linkContent);
//        }


        //shareButton = (ShareButton)rootView.findViewById(R.id.fb_share);
        recycler.setNestedScrollingEnabled(false);
        content = new ShareLinkContent.Builder()
                .setContentTitle("Top 500 Artworks")
                .setContentDescription(
                            " The 'artery top 500' ranks the 500 most expensive works sold in auction globally since 1965")
                .setContentUrl(Uri.parse("http://arteryindia.com/top500work/USD"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#Top500Artworks")
                        .build())
                .build();



        fbTop500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.show(content);
            }
        });

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
                        try {
                            addFragB(data.getJSONObject(position).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.nigga.setTag(0);
                if(data.getJSONObject(position).getBoolean("isLiked")){
                    holder.white_heart.setImageResource(R.drawable.red_heart);
                    holder.white_heart.setTag(1);
                    holder.white_heart.setBackgroundResource(R.drawable.transparent_with_red_border);
                }
                holder.white_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                          int  painting_id =data.getJSONObject(position).getInt("painting_id");

                            if( holder.white_heart.getTag()!=holder.nigga.getTag()){
                                isliked(painting_id);
                                holder.white_heart.setTag(0);
                                holder.white_heart.setImageResource(R.drawable.heart);
                                holder.white_heart.setBackgroundResource(R.drawable.transparent_with_border);
                                Log.e("unliked","as");
                                b=true;
                            }else{
                                isliked(painting_id);
                                holder.white_heart.setImageResource(R.drawable.red_heart);
                                holder.white_heart.setTag(1);
                                holder.white_heart.setBackgroundResource(R.drawable.transparent_with_red_border);
                                b=false;
                                Log.e("liked","as");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                Glide.with(getContext()).load(data.getJSONObject(position).getString("painting_thumb_image"))
                        .thumbnail(1f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageart);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            try {
//                picas = data.getJSONObject(position).getString("painting_thumb_image");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.e("dadada",""+picas);
//            try {
//                Picasso.with(context)
//                        .load(picas)
//                        .into( holder.imageart);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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

    public boolean isliked(final int painting_id){


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/changeLike",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("message").equals("painting liked")){
                                b=true;
                                Log.e("params",""+jsonObject.toString());
                            }
                        } catch (JSONException e) {

                        }
//                        try {
//                            JSONObject respo=new JSONObject(response);
//                            if(respo.getBoolean("success")){
////                                Static_Catelog.setStringProperty(context,"email",string_emails);
////                                JSONObject jo=respo.getJSONObject("data");
////                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
//                               Log.e("user_id","successsss");
////                                Intent intent=new Intent(context,CategoryActivity.class);
////                                startActivity(intent);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
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
                params.put("user_id", Static_Catelog.getStringProperty(getContext(),"user_id"));
                params.put("painting_id", ""+painting_id);
                Log.e("params",""+params.toString());

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);


            return true;

    }

}