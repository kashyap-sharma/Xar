package co.jlabs.xar.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.jlabs.xar.R;

import co.jlabs.xar.coordinatorlayouthelper.CoordinatorLayoutHelperViewPager;
import co.jlabs.xar.custom_views.BebasNeueTextView;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseProfile extends RootFragment {
    private TabLayout tabLayout;
    CoordinatorLayoutHelperViewPager viewPager;
    ViewPagerAdapter adapter;
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    public FragmentBrowseProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dash, container, false);
        View rootView = inflater.inflate(R.layout.fragment_artist_detail, container, false);
        tabLayout=(TabLayout)rootView.findViewById(R.id.tab_layout);
        viewPager=(CoordinatorLayoutHelperViewPager)rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

//        rootView.findViewById(R.id.by_artist).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addFragB();
//            }
//        });

        return rootView;
    }
    private void setupTabIcons() {

        RelativeLayout ll1 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab1, null);
        BebasNeueTextView tabOne1=(BebasNeueTextView)ll1.findViewById(R.id.taba) ;
        tabOne1.setText("Master profile");
        tabLayout.getTabAt(0).setCustomView(ll1);

        RelativeLayout ll2 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab1, null);
        BebasNeueTextView tabOne2=(BebasNeueTextView)ll2.findViewById(R.id.taba) ;
        tabOne2.setText("works");
        TextView danda=(TextView) ll2.findViewById(R.id.danda) ;
        danda.setVisibility(View.GONE);
        tabLayout.getTabAt(1).setCustomView(ll2);


    }

    private void setupViewPager(ViewPager viewPager) {
        Log.e("now m at","dash");
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentDash(), "BROWSE");
        adapter.addFragment(new FragmentMyCollection(), "DASHBOARD");

        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("now m at","dash");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}