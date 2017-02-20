package co.jlabs.xar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.jlabs.xar.functions.JSONfunctions;

public class Checkas extends AppCompatActivity {
//     String[] mMonth;
             //=new String[];
//        "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
//                "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
//    };
    String url="http://bduuxw.jlabs.co.in/snapshot-prive-vs-noworks.php?id=2";
    private ProgressDialog pDialog;
    Context context;
   // RelativeLayout chartLayout=(RelativeLayout)findViewById(R.id.activity_checkas);
    private GraphicalView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkas);

        context=this;
        new JSONParse().execute();
        //getData();

    }

    private void openChart(JSONObject jsonObject){


        JSONArray categories= null;
        try {
            categories = jsonObject.getJSONArray("categories");
            Log.e("asdf",""+categories.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("asdf","sas");

        JSONObject categories1= null;
        try {
            categories1 = categories.getJSONObject(0);
            Log.e("asdf1",""+categories1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray category= null;
        try {
            category = categories1.getJSONArray("category");
            Log.e("asdf2",""+category.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] mMonth= new String[0];
        try {
            mMonth = new String[category.length()];
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i=0;i<category.length();i++) {

                JSONObject lab = null;
                try {
                    lab = category.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("asdf" + i, "" + lab.toString());

            try {
                mMonth[i]=lab.getString("label");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        int[] x = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 };
        int[] income = { 0,2500,2700,3000,2800,3500,3700,3800,2000,2500,2700,3000,2800,3500,3700,3800};
//        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        // Creating an  XYSeries for Income
        XYSeries incomeSeries = new XYSeries("Income");
        // Creating an  XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Expense");
        // Adding data to Income and Expense Series
        for(int i=0;i<x.length;i++){
            incomeSeries.add(x[i], income[i]);
//            expenseSeries.add(x[i],expense[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(incomeSeries);
        // Adding Expense Series to dataset
       // dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.TRANSPARENT);
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
      //  incomeRenderer.setFillBelowLineColor(Color.parseColor("#EB465E"));
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(false);
        //incomeRenderer.setFillBelowLine(true);
        XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
        fill.setColor(Color.parseColor("#EB465E"));
        incomeRenderer.addFillOutsideLine(fill);

        // Creating XYSeriesRenderer to customize expenseSeries
//        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
//        expenseRenderer.setColor(Color.WHITE);
//        expenseRenderer.setPointStyle(PointStyle.CIRCLE);
//        expenseRenderer.setFillPoints(true);
//        expenseRenderer.setLineWidth(2);
//        expenseRenderer.setDisplayChartValues(true);
//        expenseRenderer.setFillBelowLine(true);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setXLabels(0);
        multiRenderer.setYLabels(0);
        multiRenderer.setFitLegend(true);
       // multiRenderer.setShowGridY(false);
        multiRenderer.setShowAxes(false);
        multiRenderer.setYAxisMax(4500);
        multiRenderer.setMarginsColor(Color.parseColor("#EB465E"));
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setZoomEnabled(false,false);
        multiRenderer.setMargins(new int[] {1, 1, 1, 1});
       // multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        multiRenderer.setYLabelsPadding(-10);
        multiRenderer.setXLabelsPadding(-10);
        multiRenderer.setShowLegend(false);
        multiRenderer.setPanEnabled(false);

        for(int i=0;i<x.length;i++){
            multiRenderer.addXTextLabel(i+1, mMonth[i]);
        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        //multiRenderer.addSeriesRenderer(expenseRenderer);

        // Creating an intent to plot line chart using dataset and multipleRenderer
//        Intent intent = ChartFactory.getLineChartIntent(getBaseContext(), dataset, multiRenderer);
//
//        // Start Activity
//        startActivity(intent);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_checkas);
        /*getBarChartView(android.content.Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type)
          Creates a bar chart view.*/

        mChartView = ChartFactory.getLineChartView(context, dataset, multiRenderer);
        multiRenderer.setGridColor(Color.parseColor("#EB465E"));
        mChartView.repaint();
        layout.addView(mChartView);
    }

    private void getData(){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        Log.e("TAG","sa");
        showpDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());
//                        openChart(response);
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Tag", "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            uid = (TextView)findViewById(R.id.uid);
//            name1 = (TextView)findViewById(R.id.name);
//            email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(Checkas.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONfunctions jParser = new JSONfunctions();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONfromURL(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            openChart(json);

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



//
//    DATA http://arteryindia.com/api/artistInfo
//    artist_id artist_id


}
