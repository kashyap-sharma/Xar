package co.jlabs.xar.fragments;

import android.support.v4.app.Fragment;



/**
 * Created by kashyap on 6/6/14.
 */
public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}
