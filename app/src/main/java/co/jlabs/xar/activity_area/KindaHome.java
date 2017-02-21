package co.jlabs.xar.activity_area;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.CustomViewPager;
import co.jlabs.xar.fragments.FragmentBrowse;
import co.jlabs.xar.fragments.FragmentDash;
import co.jlabs.xar.fragments.FragmentMyCollection;
import co.jlabs.xar.fragments.FragmentNotification;
import co.jlabs.xar.fragments.OnBackPressListener;

public class KindaHome extends AppCompatActivity {
    private TabLayout tabLayout;
    private CustomViewPager  viewPager;
    ViewPagerAdapter adapter;
    ImageView back;
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinda_home);
        back=(ImageView)findViewById(R.id.back);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setupTabIcons() {

        RelativeLayout ll1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        BebasNeueTextView tabOne1=(BebasNeueTextView)ll1.findViewById(R.id.taba) ;
        tabOne1.setText("BROWSE");
        tabLayout.getTabAt(0).setCustomView(ll1);

        RelativeLayout ll2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        BebasNeueTextView tabOne2=(BebasNeueTextView)ll2.findViewById(R.id.taba) ;
        tabOne2.setText("DASHBOARD");
        tabLayout.getTabAt(1).setCustomView(ll2);

        RelativeLayout ll3 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        BebasNeueTextView tabOne3=(BebasNeueTextView)ll3.findViewById(R.id.taba) ;
        tabOne3.setText("MY COLLECTION");
        tabLayout.getTabAt(2).setCustomView(ll3);
        RelativeLayout ll4 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        BebasNeueTextView tabOne4=(BebasNeueTextView)ll4.findViewById(R.id.taba) ;
        TextView danda=(TextView) ll4.findViewById(R.id.danda) ;
        danda.setVisibility(View.GONE);
        tabOne4.setText("NOTIFICATION");
        tabLayout.getTabAt(3).setCustomView(ll4);
    }

    private void setupViewPager(CustomViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentBrowse(), "BROWSE");
        adapter.addFragment(new FragmentDash(), "DASHBOARD");
        adapter.addFragment(new FragmentMyCollection(), "MY COLLECTION");
        adapter.addFragment(new FragmentNotification(), "NOTIFICATION");
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
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public boolean onBackPresseds() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

        if (currentFragment != null) {
           // Log.e("not","sa");
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }
        Log.e("not","sad");
        // this Fragment couldn't handle the onBackPressed call
        return false;
    }
    @Override
    public void onBackPressed() {

        if (!onBackPresseds()) {
            Log.e("not","sad");
            // container Fragment or its associates couldn't handle the back pressed task
            // delegating the task to super class
            super.onBackPressed();

        } else {
            Log.e("not","sa");
            // carousel handled the back pressed task
            // do not call super
        }
    }
}
