package co.jlabs.xar.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import co.jlabs.xar.R;


/**
 * Created by Pradeep on 12/31/2015.
 */
public class Adapter6ArtWork extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int VIEW_HEADER = 0;
    private static final int VIEW_NORMAL = 1;
    private View headerView;
    ImageLoader imageLoader;

    JSONArray json_offers;
    Context context;
    Adapter6ArtWork adapter;
    //private int lastPosition = -1;
    private int i;
    TextView tv_position;
    int num_item=0;


    //Changes


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View v) {
                super(v);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View gridItem;
            ImageView imageart;
            TextView blackfilm;
           // AddOrRemoveCart addOrRemoveCart;


            public ViewHolder(View v) {
                super(v);
                gridItem = v;
                imageart=(ImageView)v.findViewById(R.id.imageart);
                blackfilm=(TextView) v.findViewById(R.id.blackfilm);
            //    addOrRemoveCart= (AddOrRemoveCart) v.findViewById(R.id.add_or_remove_cart);
            }
        }



        public Adapter6ArtWork(Context context, JSONArray json) {
            this.adapter=this;
            this.json_offers=json;

        }

        public void setHeader(View v) {
            this.headerView = v;
        }

        @Override
        public int getItemViewType(int position) {
            return position == 0 ? VIEW_HEADER : VIEW_NORMAL;
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

                View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_choose_artwork, parent, false);
                return new ViewHolder(textView);
//            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {


                final ViewHolder holder = (ViewHolder) viewHolder;

                holder.blackfilm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.blackfilm.getVisibility()==View.VISIBLE) {
                            holder.blackfilm.setVisibility(View.GONE);
                        } else {
                            holder.blackfilm.setVisibility(View.VISIBLE);
                        }
                    }
                });

//
                String picas= null;
                try {
                    picas = json_offers.getJSONObject(position).getString("painting_hd_image");
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

}