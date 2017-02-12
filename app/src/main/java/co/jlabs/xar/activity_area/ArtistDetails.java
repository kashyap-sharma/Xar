package co.jlabs.xar.activity_area;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import co.jlabs.xar.R;

public class ArtistDetails extends AppCompatActivity {

    private ConstraintLayout activity_artist_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);
        initView();
    }

    private void initView() {
        activity_artist_details = (ConstraintLayout) findViewById(R.id.activity_artist_details);
    }
}
