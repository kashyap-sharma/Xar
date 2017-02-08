package co.jlabs.xar.activity_area;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.fragments.FragmentBrowse;
import co.jlabs.xar.fragments.FragmentDash;
import co.jlabs.xar.fragments.FragmentMyCollection;
import co.jlabs.xar.fragments.FragmentNotification;

public class KindaHome extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinda_home);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
