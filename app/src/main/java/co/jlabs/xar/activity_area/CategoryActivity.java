package co.jlabs.xar.activity_area;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueButton;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.functions.Static_Catelog;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView2;
    private LinearLayout linearLayout;
    private BebasNeueTextView textView;
    private BebasNeueTextView textView4;
    private BebasNeueTextView textView5;
    private BebasNeueButton collector;
    private BebasNeueButton finance;
    private BebasNeueButton artist;
    private BebasNeueButton gallerist;
    private BebasNeueButton art_student;
    private BebasNeueButton general_browser;
    private BebasNeueButton academic;
    Context context;

    String string_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        context=this;
        initView();
    }

    private void initView() {
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        textView = (BebasNeueTextView) findViewById(R.id.textView);
        textView4 = (BebasNeueTextView) findViewById(R.id.textView4);
        textView5 = (BebasNeueTextView) findViewById(R.id.textView5);
        collector = (BebasNeueButton) findViewById(R.id.collector);
        finance = (BebasNeueButton) findViewById(R.id.finance);
        artist = (BebasNeueButton) findViewById(R.id.artist);
        gallerist = (BebasNeueButton) findViewById(R.id.gallerist);
        art_student = (BebasNeueButton) findViewById(R.id.art_student);
        general_browser = (BebasNeueButton) findViewById(R.id.general_browser);
        academic = (BebasNeueButton) findViewById(R.id.academic);

        collector.setOnClickListener(this);
        finance.setOnClickListener(this);
        artist.setOnClickListener(this);
        gallerist.setOnClickListener(this);
        art_student.setOnClickListener(this);
        general_browser.setOnClickListener(this);
        academic.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.collector:
                string_category="COLLECTOR";
                sendCategory();
                break;
            case R.id.finance:
                string_category="FINANCE";
                sendCategory();
                break;
            case R.id.artist:
                string_category="ARTIST";
                sendCategory();
                break;
            case R.id.gallerist:
                string_category="GALLERIST";
                sendCategory();
                break;
            case R.id.art_student:
                string_category="ART STUDENT";
                sendCategory();
                break;
            case R.id.general_browser:
                string_category="GENERAL BROWSER";
                sendCategory();
                break;
            case R.id.academic:
                string_category="ACADEMIC";
                sendCategory();
                break;
        }
    }





    private void sendCategory() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/auth/updatecategory",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject respo=new JSONObject(response);
                            if(respo.getBoolean("success")){
                                Intent intent=new Intent(context,ChooseFive.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("email", Static_Catelog.getStringProperty(context,"email"));
                params.put("category", string_category);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


}
