package co.jlabs.xar.activity_area;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.adapters.Adapter6ArtWork;
import co.jlabs.xar.functions.Static_Catelog;

import static java.security.AccessController.getContext;

public class ChooseFive extends AppCompatActivity {
   String url= "http://arteryindia.com/api/showcaseWork/";
    JSONArray json=null;
    private String jsonResponse;
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_five);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position = 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        download5art();
    }





    public void download5art(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("TAG", response.toString());
                        show_offers(response);
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
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
    public void show_offers(JSONArray offers)
    {

        final Adapter6ArtWork elementsAdapter = new Adapter6ArtWork(this,offers);
       // elementsAdapter.setHeader(header);
        recyclerView.setAdapter(elementsAdapter);

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
