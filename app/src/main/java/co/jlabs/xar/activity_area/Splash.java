package co.jlabs.xar.activity_area;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import co.jlabs.xar.R;
import co.jlabs.xar.functions.JSONfunctions;
import test.jinesh.easypermissionslib.EasyPermission;

public class Splash extends AppCompatActivity implements EasyPermission.OnPermissionResult{
    Intent myIntent;
    public static int splash_time = 1000;
    Context context;
    CoordinatorLayout coordinatorLayout;
    private static final int INTERNET_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    EasyPermission easyPermission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        context = this;
        setContentView(R.layout.activity_splash);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        printHashKey();
        easyPermission = new EasyPermission();
        easyPermission.requestPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        easyPermission.requestPermission(this, Manifest.permission.INTERNET);
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isInternetAvailable()) {

                    myIntent = new Intent(Splash.this, Login.class);
                    startActivity(myIntent);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            startActivity(myIntent);
                            finish();
                        }
                    }, splash_time);

                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                    //  ll.setVisibility(View.VISIBLE);


                    ha.postDelayed(this, 10000);
                }
            }
        }, 500);

    }




    public boolean isInternetAvailable() {

        return JSONfunctions.isNetworkAvailable(context);

    }
    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "co.jlabs.xar",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        easyPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionResult(String permission, boolean isGranted) {
        switch (permission) {
            case Manifest.permission.ACCESS_NETWORK_STATE:
                if (isGranted) {
                    Log.e("readContacts", "granted");
                    easyPermission.requestPermission(Splash.this,Manifest.permission.ACCESS_NETWORK_STATE);
                } else {
                    Log.e("readContacts", "denied");
                    easyPermission.requestPermission(Splash.this,Manifest.permission.ACCESS_NETWORK_STATE);
                }
                break;
            case Manifest.permission.INTERNET:
                if (isGranted) {
                    Log.e("location", "granted");
                } else {
                    Log.e("location", "denied");
                }
                break;
        }
    }
}
