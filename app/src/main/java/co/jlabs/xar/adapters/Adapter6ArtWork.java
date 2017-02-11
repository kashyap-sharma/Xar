package co.jlabs.xar.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.taishi.flipprogressdialog.FlipProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.activity_area.CategoryActivity;
import co.jlabs.xar.activity_area.KindaHome;
import co.jlabs.xar.custom_views.BebasNeueButton;
import co.jlabs.xar.functions.Static_Catelog;


/**
 * Created by Pradeep on 12/31/2015.
 */
public class Adapter6ArtWork extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int VIEW_HEADER = 0;
    private static final int VIEW_NORMAL = 1;
    private View headerView;
    ImageLoader imageLoader;
    List<Integer> responseList = new ArrayList<Integer>();
    JSONArray json_offers;
    Context context;
    Adapter6ArtWork adapter;
    //private int lastPosition = -1;
    private int i;
    TextView tv_position;
    int int_painting_id=0;
    FlipProgressDialog fpd ;
    int [] paint_id;


    //Changes


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View v) {
                super(v);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View gridItem;
            ImageView imageart,white_heart;
            TextView blackfilm;
           // AddOrRemoveCart addOrRemoveCart;


            public ViewHolder(View v) {
                super(v);
                gridItem = v;
                imageart=(ImageView)v.findViewById(R.id.imageart);
                white_heart=(ImageView)v.findViewById(R.id.white_heart);
                blackfilm=(TextView) v.findViewById(R.id.blackfilm);
            //    addOrRemoveCart= (AddOrRemoveCart) v.findViewById(R.id.add_or_remove_cart);
            }
        }



        public Adapter6ArtWork(Context context, JSONArray json) {
            this.context=context;
            this.adapter=this;
            paint_id=new int [json.length()];
            this.json_offers=json;

        }

        public void setHeader(View v) {
            this.headerView = v;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            int count=0;
            try
            {
                count=json_offers.length();
            }
            catch (Exception e)
            {

            }

            return count;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            if (viewType == VIEW_HEADER) {
//                return new HeaderViewHolder(headerView);
//            } else {
                fpd = new FlipProgressDialog();
                List<Integer> imageList = new ArrayList<Integer>();
                imageList.add(R.drawable.artery_a);
                imageList.add(R.drawable.artery_full);
                fpd.setImageList(imageList);                              // *Set a imageList* [Have to. Transparent background png recommended]
                fpd.setCanceledOnTouchOutside(false);                      // If true, the dialog will be dismissed when user touch outside of the dialog. If false, the dialog won't be dismissed.
                fpd.setDimAmount(0.2f);                                   // Set a dim (How much dark outside of dialog)

    // About dialog shape, color
                fpd.setBackgroundColor(Color.parseColor("#FF4081"));      // Set a background color of dialog
                fpd.setBackgroundAlpha(0.6f);                             // Set a alpha color of dialog
                fpd.setBorderStroke(0);                                   // Set a width of border stroke of dialog
                fpd.setBorderColor(-1);                                   // Set a border stroke color of dialog
                fpd.setCornerRadius(16);                                  // Set a corner radius

    // About image
                fpd.setImageSize(200);                                    // Set an image size
                fpd.setImageMargin(10);                                   // Set a margin of image

    // About rotation
                fpd.setOrientation("rotationY");                          // Set a flipping rotation
                fpd.setDuration(1000);                                     // Set a duration time of flipping ratation
                fpd.setStartAngle(0.0f);                                  // Set an angle when flipping ratation start
                fpd.setEndAngle(180.0f);                                  // Set an angle when flipping ratation end
                fpd.setMinAlpha(0.0f);                                    // Set an alpha when flipping ratation start and end
                fpd.setMaxAlpha(1.0f);                                    // Set an alpha while image is flipping






                View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_artwork, parent, false);
                return new ViewHolder(textView);
//            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {


                final ViewHolder holder = (ViewHolder) viewHolder;
            try {
                holder.imageart.setTag(json_offers.getJSONObject(position).getInt("painting_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            holder.imageart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.blackfilm.getVisibility()==View.VISIBLE) {

                            holder.blackfilm.setVisibility(View.GONE);
                            holder.white_heart.setImageResource(R.drawable.heart);
                            try {
                                int_painting_id=json_offers.getJSONObject(position).getInt("painting_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            holder.white_heart.setBackgroundResource(R.drawable.transparent_with_border);

                            try {

                                responseList.remove( responseList.indexOf(json_offers.getJSONObject(position).getInt("painting_id")));
                                Log.e("responceList",""+responseList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            removeFromLikes();

                        } else {

                            if (responseList.size()<5) {
                                holder.blackfilm.setVisibility(View.VISIBLE);
                                try {
                                    int_painting_id=json_offers.getJSONObject(position).getInt("painting_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                holder.white_heart.setImageResource(R.drawable.red_heart);
                                holder.white_heart.setBackgroundResource(R.drawable.transparent_with_red_border);
                                try {
                                    responseList.add(json_offers.getJSONObject(position).getInt("painting_id"));
                                    Log.e("responceListr",""+responseList.toString());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                addToLikes();
                            } else {
                                final Dialog openDialog = new Dialog(context);
                                openDialog.setContentView(R.layout.customdialog_layout);
                                openDialog.setCancelable(true);

                                BebasNeueButton next = (BebasNeueButton) openDialog.findViewById(R.id.next);
                                next.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        Intent intent =new Intent(context, KindaHome.class);
                                        context.startActivity(intent);
                                        Static_Catelog.setStringProperty(context,"five_art","five");
                                        ((Activity)context).finish();
                                    }
                                });
                                openDialog.show();

                            }
                        }
                    }
                });


                String picas= null;
                try {
                    picas = json_offers.getJSONObject(position).getString("painting_thumb_image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("dadada",""+picas);
                try {
                    Picasso.with(context)
                            .load(picas)
                            .into(holder.imageart);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //holder.imageart.setImageUrl(json.getString("img"), imageLoader);

            }




    @Override
    public void onClick(View v) {

    }

    private Activity getActivity(View v) {
        Context context = v.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private void addToLikes() {
        showProgressDialog();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/addToLikes/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        Log.e("QSQSQS",""+response.toString());
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
                params.put("user_id", Static_Catelog.getStringProperty(context,"user_id"));
                params.put("painting_id", ""+int_painting_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    private void removeFromLikes() {
        showProgressDialog();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/api/removeFromLikes/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
                        Log.e("QSQSQS",""+response.toString());
//                        try {
//                            JSONObject respo=new JSONObject(response);
//                            if(respo.getBoolean("success")){
////                                Static_Catelog.setStringProperty(context,"email",string_emails);
////                                JSONObject jo=respo.getJSONObject("data");
////                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
//                                Log.e("user_id","successsss");
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
                params.put("user_id", Static_Catelog.getStringProperty(context,"user_id"));
                params.put("painting_id", ""+int_painting_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void showProgressDialog() {

            fpd.show(((Activity)context).getFragmentManager(),"");

    }

    private void hideProgressDialog() {
        if (fpd != null && fpd.isVisible()) {
            fpd.dismiss();
        }
    }

}