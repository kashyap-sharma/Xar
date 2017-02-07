package co.jlabs.xar.activity_area;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.Arrays;

import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueEdit;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.FlipCheckBox;
import co.jlabs.xar.functions.JSONfunctions;

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
    private PendingAction pendingAction = PendingAction.NONE;
    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    Context context;
    private static final String SAVED_PROGRESS = "sign_in_progress";
    String string_emails;
    String string_first_name;
    String string_last_name;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login_signup);
        initView();
        if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState
                    .getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }
    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        loginButton=(LoginButton)findViewById(R.id.loginButton);
        callbackManager = CallbackManager.Factory.create();
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_avant));
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
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        Log.d("user2q", "hello1");



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


                                        SendFbData();

                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,name,first_name,last_name,birthday");
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

    private void SendFbData() {
       Log.e("Success:"+string_first_name,"FB___"+string_emails);
        Intent intent=new Intent(this,CategoryActivity.class);
        startActivity(intent);
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
            Log.e("googlee",""+acct.getDisplayName());
            Intent intent=new Intent(this,CategoryActivity.class);
            startActivity(intent);
           // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
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
                        updateUI(false);
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
                        updateUI(false);
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

    private void updateUI(boolean signedIn) {
//        if (signedIn) {
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
//        }
    }

}
