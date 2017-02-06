package co.jlabs.xar.activity_area;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueEdit;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.FlipCheckBox;

public class Login extends AppCompatActivity {

    private ImageView imageView2;
    private LinearLayout linearLayout;
    private BebasNeueTextView textView;
    private BebasNeueTextView textView2;
    private RelativeLayout rel;
    private RelativeLayout rel2;
    private BebasNeueEdit email;
    private BebasNeueEdit password;
    private RelativeLayout signup1;
    private LinearLayout signup;
    private BebasNeueTextView textView3;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);
        initView();
    }

    private void initView() {
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        textView = (BebasNeueTextView) findViewById(R.id.textView);
        textView2 = (BebasNeueTextView) findViewById(R.id.textView2);
        rel = (RelativeLayout) findViewById(R.id.rel);
        rel2 = (RelativeLayout) findViewById(R.id.rel2);
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
    }
}
