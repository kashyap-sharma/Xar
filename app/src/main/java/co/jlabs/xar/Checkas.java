package co.jlabs.xar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

import java.util.ArrayList;
import java.util.List;

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
    private GraphicalView mChartView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkas);

        context=this;
        drawChart();
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
        int[] x = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16 };
//        List<double[]> values = new ArrayList<double[]>();
//        values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
//                13.9 });
        int[] income = { 0,2500,2700,3000,2800,3500,3700,3800,2000,2500,2700,3000,2800,3500,3700,3800};
     //   int[] expense = {22000, 27000, 29000, 28000, 26000, 30000, 33000, 34000 ,22000, 27000, 29000, 28000, 26000, 30000, 33000, 34000};

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
        incomeRenderer.setColor(Color.parseColor("#EB465E"));
        incomeRenderer.setPointStyle(PointStyle.CIRCLE);
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setFillBelowLineColor(Color.parseColor("#EB465E"));
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(false);
        //incomeRenderer.setFillBelowLine(true);
//        XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
//        fill.setColor(Color.parseColor("#EB465E"));
//        incomeRenderer.addFillOutsideLine(fill);

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

//
//        mChartView1 = ChartFactory.getBarChartView(context, dataset, multiRenderer, BarChart.Type.DEFAULT);
//        multiRenderer.setGridColor(Color.parseColor("#EB465E"));
//        mChartView1.repaint();
        mChartView = ChartFactory.getLineChartView(context, dataset, multiRenderer);
        multiRenderer.setGridColor(Color.parseColor("#EB465E"));
        mChartView.repaint();
        layout.addView(mChartView);

       // layout.addView(mChartView1);


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
    private void drawChart(){
        int[] x1= {  1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16 };
        int[] expenseq = { 2900,2500,2700,3000,2800,3500,3700,3800,2000,2500,2700,3000,2800,3500,3700,3800};

// Creating an XYSeries for Expense
        XYSeries expenseSeries = new XYSeries("Expense");

        for(int i=0;i<x1.length;i++){

            expenseSeries.add(i,expenseq[i]);
        }
        Log.e("datas",""+x1.length);
// Creating a dataset to hold series
        XYMultipleSeriesDataset dataset1 = new XYMultipleSeriesDataset();
// Adding Income Series to the dataset
        dataset1.addSeries(expenseSeries);

// Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.CYAN); //color of the graph set to cyan
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);
        expenseRenderer.setDisplayChartValues(true);


// Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Expense Chart");
        multiRenderer.setXTitle("Year 2016");
        multiRenderer.setYTitle("Amount in Dollars");
        multiRenderer.setBarWidth(20);
/***
 * Customizing graphs
 */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
//setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
//setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);

//setting zoom buttons visiblity


        multiRenderer.setShowAxes(false);
        //multiRenderer.setZoomButtonsVisible(false);
//setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
//setting click false on graph
        multiRenderer.setClickEnabled(false);
//setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
//setting lines to display on y axis
     //   multiRenderer.setShowGridY(false);
//setting lines to display on x axis
        multiRenderer.setShowGridX(false);
//setting legend to fit the screen size
        multiRenderer.setPanEnabled(false);
        multiRenderer.setFitLegend(true);
//setting displaying line on grid
        multiRenderer.setShowGrid(false);
//setting zoom to false
        multiRenderer.setZoomEnabled(false);
//setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
//setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
//setting to in scroll to false
        multiRenderer.setInScroll(false);




//setting to set legend height of the graph

//setting x axis label align
//        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
////setting y axis label to align
//        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
//setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
//setting no of values to display in y axis
        multiRenderer.setYLabels(10);
//        multiRenderer.setYLabelsPadding(-10);
//        multiRenderer.setXLabelsPadding(-10);
/* setting y axis max value, Since i'm using static values inside the graph
* so i'm setting y max value to 4000.
*/
// if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(4000);
//setting used to move the graph on xaxiz to .5 to the right

//setting max values to be display in x axis

//setting bar size or space between two bars
        //multiRenderer.setBarSpacing(0.0);
//Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
//Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(Color.parseColor("#EB465E"));
        multiRenderer.setApplyBackgroundColor(true);

//setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{1, 1, 1, 1});

// Adding expenseRenderer to multipleRenderer
        multiRenderer.addSeriesRenderer(expenseRenderer);

//this part is used to display graph on the xml
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_checka);
//remove any views before u paint the chart
//        chartContainer.removeAllViews();
//drawing bar chart

        mChartView1 = ChartFactory.getBarChartView(Checkas.this, dataset1,
                multiRenderer, BarChart.Type.DEFAULT);
        multiRenderer.setGridColor(Color.parseColor("#EB465E"));
        mChartView1.repaint();
//adding the view to the linearlayout
        layout.addView(mChartView1);

    }



//
//    DATA http://arteryindia.com/api/artistInfo
//    artist_id artist_id


}
