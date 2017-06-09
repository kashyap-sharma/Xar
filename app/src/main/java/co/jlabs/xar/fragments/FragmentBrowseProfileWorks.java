package co.jlabs.xar.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.bigImage.BigImageViewer;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.functions.Static_Catelog;
import co.jlabs.xar.glide.GlideImageLoader;
import co.jlabs.xar.model.Image;
import co.jlabs.xar.model.ReceiverInterface;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseProfileWorks extends RootFragment  implements ReceiverInterface {
    JSONArray jsonArray;
    private ProgressDialog pDialog;
    boolean b=false;
    RecyclerView recycler;
    RecyclerView.LayoutManager layoutManager;
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
        Image image = new Image();
        BigImageViewer.initialize(GlideImageLoader.with(getContext()));

        View rootView = inflater.inflate(R.layout.fragment_profile_works, container, false);
        Log.e("now m at","dash");
        recycler=(RecyclerView)rootView.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getContext(),1);
        recycler.setHasFixedSize(true);
        recycler.setNestedScrollingEnabled(false);
        getArtistsWork(Static_Catelog.getStringProperty(getContext(),"farzi"));
        return rootView;
    }

    private void getArtistsWork(final String id) {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/artistPaintings",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("fkus",""+response.toString());
                        try {
                            JSONArray jsonArray1=new JSONArray(response);
                            // JSONObject jsonObject=new JSONObject(response);
                            // JSONArray jsonArray=jsonObject.getJSONArray("data");
                            Log.e("hella",""+jsonArray1.toString());
                            showArtistsWork(jsonArray1);
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
                params.put("artist_id", id);
                Log.e("fkus",""+params.toString());
                return params;
            }

        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjRequest.setRetryPolicy(policy);
        mRequestQueue.add(jsonObjRequest);


        AppController.getInstance().addToRequestQueue(jsonObjRequest);


    }


    public void showArtistsWork(JSONArray data)
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
            Log.e("okokok",""+data.toString());
            try {
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



    public void receiveMessage(JSONObject str)
    {
        Log.e("h","sasas"+str.toString());
        try {
            int id=str.getInt("artist_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}