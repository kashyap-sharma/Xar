package co.jlabs.xar.activity_area;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueButton;
import co.jlabs.xar.custom_views.BebasNeueTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
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

                break;
            case R.id.finance:

                break;
            case R.id.artist:

                break;
            case R.id.gallerist:

                break;
            case R.id.art_student:

                break;
            case R.id.general_browser:

                break;
            case R.id.academic:

                break;
        }
    }
}
