package co.jlabs.xar.activity_area;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.appnirman.vaidationutils.ValidationUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;

import co.jlabs.xar.AppController;
import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueEdit;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.FlipCheckBox;
import co.jlabs.xar.functions.JSONfunctions;
import co.jlabs.xar.functions.Static_Catelog;

public class Login extends FragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    private ImageView imageView2;
    private LinearLayout linearLayout;
    private BebasNeueTextView textView;
    private BebasNeueTextView textView2;
    private RelativeLayout rel;
    private RelativeLayout rel2;
    private BebasNeueEdit email;
    private BebasNeueEdit password;
    private RelativeLayout signup1,rel_email,rel_password;
    private LinearLayout signup;
    private BebasNeueTextView textView3,fb_text,google_text,already,text_login;
    private FlipCheckBox viewFlipper;
    private BebasNeueTextView viewFlipper1;
    private BebasNeueTextView forgot;
    private RelativeLayout ll;
    private BebasNeueTextView signn;
    private RelativeLayout sign_in;
    private ImageView s_imageView2;
    private LinearLayout s_linearLayout;
    private BebasNeueTextView s_textView;
    private BebasNeueTextView s_textView2;
    private BebasNeueEdit s_email;
    private BebasNeueEdit s_password;
    private BebasNeueEdit s_fullname;
    private RelativeLayout s_signup1;
    private LinearLayout s_signup;
    private BebasNeueTextView s_textView3;
    private BebasNeueTextView s_signn;
    private RelativeLayout s_sign_in;
    ProgressDialog mProgressDialog;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    Boolean loginviafacebook=true;
    Boolean loginviagmail=false;
    private int mSignInProgress;
    private static final int STATE_DEFAULT = 0;
    private ViewGroup constraint_signup;
    private ViewGroup constraint_signup_page;
    private PendingAction pendingAction = PendingAction.NONE;
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    ValidationUtils validationUtils;
    Context context;
    private static final String SAVED_PROGRESS = "sign_in_progress";
    String string_emails;
    String string_first_name;
    String string_last_name;
    String string_pic_url;
    String string_fb_id;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login_signup);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initView();
        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState
                    .getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }
    }

    private void initView() {
        constraint_signup = (ViewGroup) findViewById(R.id._login);
        constraint_signup_page = (ViewGroup) findViewById(R.id._signup);
        validationUtils = new ValidationUtils(context);
        mProgressDialog = new ProgressDialog(this);
        loginButton=(LoginButton)findViewById(R.id.loginButton);
        callbackManager = CallbackManager.Factory.create();
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.artery_full_banner));
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        textView = (BebasNeueTextView) findViewById(R.id.textView);
        fb_text = (BebasNeueTextView) findViewById(R.id.fb_text);
        google_text = (BebasNeueTextView) findViewById(R.id.google_text);
        already = (BebasNeueTextView) findViewById(R.id.already);
        textView = (BebasNeueTextView) findViewById(R.id.textView);
        textView2 = (BebasNeueTextView) findViewById(R.id.textView2);
        text_login = (BebasNeueTextView) findViewById(R.id.text_login);
        rel = (RelativeLayout) findViewById(R.id.rel);
        rel2 = (RelativeLayout) findViewById(R.id.rel2);
        rel_email = (RelativeLayout) findViewById(R.id.rel_email);
        rel_password = (RelativeLayout) findViewById(R.id.rel_password);
        email = (BebasNeueEdit) findViewById(R.id.email);
        password = (BebasNeueEdit) findViewById(R.id.password);
        signup1 = (RelativeLayout) findViewById(R.id.signup1);

        signup = (LinearLayout) findViewById(R.id.signup);
        textView3 = (BebasNeueTextView) findViewById(R.id.textView3);
        viewFlipper = (FlipCheckBox) findViewById(R.id.viewFlipper);
        viewFlipper1 = (BebasNeueTextView) findViewById(R.id.viewFlipper1);
        forgot = (BebasNeueTextView) findViewById(R.id.forgot);
        ll = (RelativeLayout) findViewById(R.id.ll);
        signn = (BebasNeueTextView) findViewById(R.id.signn);
        sign_in = (RelativeLayout) findViewById(R.id.sign_in);
        s_imageView2 = (ImageView) findViewById(R.id.s_imageView2);
        s_imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.artery_full_banner));
        s_linearLayout = (LinearLayout) findViewById(R.id.s_linearLayout);
        s_textView = (BebasNeueTextView) findViewById(R.id.s_textView);
        s_textView2 = (BebasNeueTextView) findViewById(R.id.s_textView2);
        s_email = (BebasNeueEdit) findViewById(R.id.s_email);
        s_password = (BebasNeueEdit) findViewById(R.id.s_password);
        s_fullname = (BebasNeueEdit) findViewById(R.id.s_fullname);
        s_signup1 = (RelativeLayout) findViewById(R.id.s_signup1);
        s_signup = (LinearLayout) findViewById(R.id.s_signup);
        s_textView3 = (BebasNeueTextView) findViewById(R.id.s_textView3);
        s_signn = (BebasNeueTextView) findViewById(R.id.s_signn);
        s_sign_in = (RelativeLayout) findViewById(R.id.s_sign_in);
        rel.setOnClickListener(this);
        rel2.setOnClickListener(this);
        sign_in.setOnClickListener(this);
        s_sign_in.setOnClickListener(this);
        signup1.setOnClickListener(this);
        s_signup1.setOnClickListener(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        Log.d("user2q", "hello1");
        constraint_signup_page.setVisibility(View.GONE);
        constraint_signup.setVisibility(View.VISIBLE);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();







        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        loginviagmail=false;
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        string_emails = object.optString("email");
                                        string_last_name = object.optString("last_name");
                                        string_first_name = object.optString("first_name");
                                        string_fb_id = object.optString("id");
                                        JSONObject picture = object.optJSONObject("picture");
                                        JSONObject data=picture.optJSONObject("data");
                                        try {
                                            string_pic_url=data.optString("url");
                                        } catch (Exception e) {
                                            string_pic_url="http://jlabs.co/no_image.png";
                                            e.printStackTrace();
                                        }
                                        SendFbData();

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,last_name,first_name,picture.type(large),email");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }


                    @Override
                    public void onCancel() {
                        Log.d("user2", "hello2");

                    }

                    @Override
                    public void onError(FacebookException exception) {

                        if (pendingAction != PendingAction.NONE
                                && exception instanceof FacebookAuthorizationException) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }

                    }

                    private void showAlert() {
                        new AlertDialog.Builder(Login.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rel:
                loginButton.performClick();
                break;
            case R.id.rel2:
                gPlusLogin();
                break;
            case R.id.sign_in:
                updateUIForSignIn();
                break;
            case R.id.s_sign_in:
                updateUIForSignUp();
                updateUIForSignIn();
                break;
            case R.id.signup1:
                updateUIForSignUp();
                break;
            case R.id.s_signup1:
                signUpUsingEmail();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (loginviafacebook) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
//        if (!loginviafacebook) {
//
//            switch (requestCode) {
//                case RC_SIGN_IN:
//                    if (resultCode == RESULT_OK) {
//                        // If the error resolution was successful we should continue
//                        // processing errors.
//                        mSignInProgress = STATE_SIGN_IN;
//                    } else {
//                        // If the error resolution was not successful or the user canceled,
//                        // we should stop processing errors.
//                        mSignInProgress = STATE_DEFAULT;
//                    }
//
//                    if (!mGoogleApiClient.isConnecting()) {
//                        // If Google Play services resolved the issue with a dialog then
//                        // onStart is not called so we need to re-attempt connection here.
//                        mGoogleApiClient.connect();
//                    }
//                    break;
//            }
//        }


//        if (requestCode == EMAIL_ACTIVITY_REQUEST && resultCode == RESULT_OK) {
//
//            String[] to = data.getStringArrayExtra(Intent.EXTRA_EMAIL);
//            String subject = data.getStringExtra(Intent.EXTRA_SUBJECT);
//            String msg = data.getStringExtra(Intent.EXTRA_TEXT);
//
//            String email = Static_Catelog.getStringProperty(con, "email");
//            String numb = Static_Catelog.getStringProperty(con, "numb");
//
//            //API Call wake
//
//            JSONObject finalJson = new JSONObject();
//            try {
//                finalJson.put("email", email);
//                finalJson.put("vendor_id", 1);
//                finalJson.put("phone", numb);
//                finalJson.put("subject", subject);
//                finalJson.put("body", msg);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Log.i("tracer", "" + finalJson);
//            new Feedback(finalJson).execute();
//
//
//
//        }



    }



    public void gPlusLogin(){
        loginviafacebook=false;
        if(isNetworkAvailable()) {
            signIn();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Check internet connectivity", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isNetworkAvailable() {

        return JSONfunctions.isNetworkAvailable(context);

    }

    public void updateUIForSignIn(){
        if(textView3.getVisibility()==View.VISIBLE){
            textView3.setVisibility(View.GONE);
            fb_text.setText("LOG IN USING FACEBOOK");
            google_text.setText("LOG IN USING GOOGLE");
            rel_email.setVisibility(View.VISIBLE);
            rel_password.setVisibility(View.VISIBLE);
            text_login.setText("LOG IN");
            viewFlipper.setVisibility(View.VISIBLE);
            viewFlipper1.setVisibility(View.VISIBLE);
            forgot.setVisibility(View.VISIBLE);
            signn.setText("SIGN UP");
            already.setText("DON'T HAVE AN ACCOUNT?");
        }else{
            textView3.setVisibility(View.VISIBLE);
            fb_text.setText("SIGN UP USING FACEBOOK");
            google_text.setText("SIGN UP USING GOOGLE");
            rel_email.setVisibility(View.GONE);
            rel_password.setVisibility(View.GONE);
            text_login.setText("SIGN UP USING YOUR EMAIL");
            viewFlipper.setVisibility(View.GONE);
            viewFlipper1.setVisibility(View.GONE);
            forgot.setVisibility(View.GONE);
            signn.setText("SIGN IN");
            already.setText("ALREADY HAVE AN ACCOUNT?");
        }


    }
    public void updateUIForSignUp(){
        if(constraint_signup.getVisibility()==View.VISIBLE){
            if(viewFlipper.getVisibility()==View.VISIBLE){
                if (!validationUtils.isValidEmail(email.getText().toString().trim())) {
                    email.setError("Enter valid email");
                    return;
                }
                if (!validationUtils.isValidPassword(password.getText().toString().trim())) {
                    password.setError("Enter valid password");
                    return;
                }
                sendSignIn();
            }else{
                constraint_signup.setVisibility(View.GONE);
                constraint_signup_page.setVisibility(View.VISIBLE);
            }

        }else{

            constraint_signup_page.setVisibility(View.GONE);
            constraint_signup.setVisibility(View.VISIBLE);
        }

    }
    public void  signUpUsingEmail(){
        if (!validationUtils.isValidEmail(s_email.getText().toString().trim())) {
            s_email.setError("Enter valid email");
            return;
        }
        if (!validationUtils.isValidPassword(s_password.getText().toString().trim())) {
            s_password.setError("Enter valid password");
            return;
        }
        if (!validationUtils.isEmptyEditText(s_fullname.getText().toString().trim())) {
            s_fullname.setError("Enter valid name");
            return;
        }
        if (s_fullname.getText().toString().trim().length()<3) {
            s_fullname.setError("Enter valid name");
            return;
        }
        sendSignup();
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
//            Log.e("email",""+acct.getEmail());
//            Log.e("id",""+acct.getId());
//            Log.e("url",""+acct.getPhotoUrl());
//            Log.e("last_name",""+acct.getFamilyName());
//            Log.e("first_name",""+acct.getGivenName());
            string_emails = acct.getEmail();
            string_last_name = acct.getFamilyName();
            string_first_name = acct.getGivenName();
            string_fb_id = acct.getId();
            try {
                string_pic_url=acct.getPhotoUrl().toString();
            } catch (Exception e) {
                string_pic_url="http://jlabs.co/no_image.png";
                e.printStackTrace();
            }
            sendGoogle();
           // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));

        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }




    public void sendSignup(){

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
               "http://arteryindia.com/auth/signup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject respo=new JSONObject(response);
                            if(respo.getBoolean("success")){
                                Static_Catelog.setStringProperty(context,"email",string_emails);
                                JSONObject jo=respo.getJSONObject("data");
                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
                                Log.e("user_id",jo.getInt("id")+"");
                                Intent intent=new Intent(context,CategoryActivity.class);
                                startActivity(intent);
                                finish();
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
                params.put("email", s_email.getText().toString().trim());
                params.put("password", s_password.getText().toString().trim());
                params.put("first_name", s_fullname.getText().toString().trim());
                params.put("last_name", s_fullname.getText().toString().trim());

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    public void sendSignIn(){

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/auth/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject respo=new JSONObject(response);
                            if(respo.getBoolean("success")){
                                Static_Catelog.setStringProperty(context,"email",string_emails);
                                JSONObject jo=respo.getJSONObject("data");
                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
                                Log.e("user_id",jo.getInt("id")+"");
                                Intent intent=new Intent(context,CategoryActivity.class);
                                startActivity(intent);
                                finish();
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
                params.put("email", email.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }
    private void SendFbData() {

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/auth/facebook",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject respo=new JSONObject(response);
                            if(respo.getBoolean("success")){
                                Static_Catelog.setStringProperty(context,"email",string_emails);
                                JSONObject jo=respo.getJSONObject("data");
                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
                                Log.e("user_id",jo.getInt("id")+"");
                                Intent intent=new Intent(context,CategoryActivity.class);
                                startActivity(intent);
                                finish();
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
                params.put("email", string_emails);
                params.put("password", "");
                params.put("first_name",string_first_name);
                params.put("last_name", string_last_name);
                params.put("active", "0");
                params.put("created_date", "");
                params.put("fb_handle", string_fb_id);
                params.put("photo", string_pic_url);
                params.put("walkthrough", "0");
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void sendGoogle() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                "http://arteryindia.com/auth/gplus",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("QSQSQS",""+response.toString());
                        try {
                            JSONObject respo=new JSONObject(response);
                            if(respo.getBoolean("success")){
                                Static_Catelog.setStringProperty(context,"email",string_emails);
                                JSONObject jo=respo.getJSONObject("data");
                                Static_Catelog.setStringProperty(context,"user_id",jo.getInt("id")+"");
                                Log.e("user_id",jo.getInt("id")+"");
                                Intent intent=new Intent(context,CategoryActivity.class);
                                startActivity(intent);
                                finish();
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
                params.put("email", string_emails);
                params.put("password", "");
                params.put("first_name",string_first_name);
                params.put("last_name", string_last_name);
                params.put("active", "0");
                params.put("created_date", "");
                params.put("gplus_handle", string_fb_id);
                params.put("photo", string_pic_url);
                params.put("walkthrough", "0");
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


}
