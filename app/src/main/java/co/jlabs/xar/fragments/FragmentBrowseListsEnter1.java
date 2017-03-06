package co.jlabs.xar.fragments;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.adapters.EndlessRecyclerViewScrollListener;
import co.jlabs.xar.bigImage.BigImageViewer;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.Fontasm;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.glide.GlideImageLoader;
import io.fabric.sdk.android.Fabric;


/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseListsEnter1 extends RootFragment  {
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
    public FragmentBrowseListsEnter1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BigImageViewer.initialize(GlideImageLoader.with(getContext()));
        View rootView = inflater.inflate(R.layout.fragment_browse_lis_enter1, container, false);
        recycler=(RecyclerView)rootView.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getContext(),1);
        recycler.setHasFixedSize(true);
        fbTop500=(Fontasm)rootView.findViewById(R.id.fb_top500);
        tweetTop500=(Fontasm)rootView.findViewById(R.id.twitter_top500);
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("EF4lJjslwp31rAAkDYkFGy8a3", "ig1G8NYxzZQSfg8n83IBqhoqZ9kTdgjNc4QbtuLSByBT68UWkY");
        Fabric.with(getContext(), new TwitterCore(authConfig), new TweetComposer());
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
                .setContentTitle("Top 50 Artists")
                .setContentDescription(
                            "  The 'artery top 50 artists' ranks the leading artists based on their turnover in global auctions since 1965.")
                .setContentUrl(Uri.parse("http://arteryindia.com/top50artists"))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#Top50Artists")
                        .build())
                .build();



        fbTop500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.show(content);
            }
        });
        tweetTop500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                            .text("The 'artery top 50 artists' ranks the leading artists based on their turnover in global auctions. #Top50Artists").url(new URL("http://arteryindia.com/top50artists"));
                    builder.show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
//                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
//                        .getActiveSession();
//                final Intent intent = new ComposerActivity.Builder(getContext())
//                        .session(session)
//                        .createIntent();
//                startActivity(intent);
            }
        });


        getTop50();


        return rootView;
    }


    private void getTop50(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/top50artists",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONArray jsonArray1=new JSONArray(response);
                           // JSONObject jsonObject=new JSONObject(response);
                           // JSONArray jsonArray=jsonObject.getJSONArray("data");
                            Log.e("hella",""+jsonArray1.toString());
                            showTop50(jsonArray1);
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


    public void showTop50(JSONArray data)
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
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_browse_artist, parent, false));
        }

        @Override
        public void onBindViewHolder(final FakeViewHolder holder, final int position) {
            JSONObject jo;
            try {
                holder.artistName.setText(""+data.getJSONObject(position).getString("artist_name"));
                holder.born.setText("Born "+data.getJSONObject(position).getInt("yob"));
                final String se=data.getJSONObject(position).getString("artist_category_color");
                try {
                    holder.artist_category_color.setBackgroundColor(Color.parseColor(data.getJSONObject(position).getString("artist_category_color")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    holder.artist_category_color.setBackgroundColor(Color.parseColor("#bd362d"));
                }
                holder.artist_category_name.setText(data.getJSONObject(position).getString("artist_category_name"));
                holder.artist_category_color.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.i("TAG", "touched down");
                                holder.artist_.setVisibility(View.VISIBLE);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                Log.i("TAG", "move");
                                holder.artist_.setVisibility(View.GONE);
                                break;
                            case MotionEvent.ACTION_UP:
                                Log.i("TAG", "touched up");
                                holder.artist_.setVisibility(View.GONE);
                                break;
                        }
                        return true;
                    }
                });
                final int id=data.getJSONObject(position).getInt("artist_id");
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addFragB(""+id);
                    }
                });
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
            Log.e("muchas",""+data.length());
            return data.length();
        }
    }
    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        BebasNeueTextView artistName,born,follow,artist_category_name,artist_category_color;
        ImageView imageView;
        RelativeLayout artist_;

        public FakeViewHolder(View itemView) {
            super(itemView);
            artistName = (BebasNeueTextView) itemView.findViewById(R.id.name);
            born = (BebasNeueTextView) itemView.findViewById(R.id.textView1);
            follow = (BebasNeueTextView) itemView.findViewById(R.id.follow);
            artist_category_name  = (BebasNeueTextView) itemView.findViewById(R.id.artist_category_name );
            artist_category_color  = (BebasNeueTextView) itemView.findViewById(R.id.artist_category_color );
            imageView = (ImageView) itemView.findViewById(R.id.imageart);
            artist_  = (RelativeLayout) itemView.findViewById(R.id.artist_ );

        }
    }



    public void addFragB(String id) {

        FragmentBrowseProfile a2Fragment = new FragmentBrowseProfile();
        Bundle arguments = new Bundle();
        arguments.putString( "artist_id" , id);
        a2Fragment.setArguments(arguments);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("B");
        transaction.replace(R.id.fragA_LinearLayou, a2Fragment).commit();
    }



}