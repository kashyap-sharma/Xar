package co.jlabs.xar.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

import co.jlabs.xar.R;
import co.jlabs.xar.custom_views.BebasNeueTextView;
import co.jlabs.xar.custom_views.EasyMoneyTextView;

/**
 * Created by JLabs on 02/08/17.
 */

public class FragmentBrowseListsEntered extends RootFragment {

    ImageView painting;
    JSONObject jsonObject;
    BebasNeueTextView painting_title,size,type,medium,markings,year_created,aution_house,lot_info,date_of_auction,price_realised;
    EasyMoneyTextView presale_estimate;


    public FragmentBrowseListsEntered() {
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
//        addFragB();
        // Inflate the layout for this fragment
        Bundle arguments = getArguments();
        try {
            jsonObject=new JSONObject(arguments.getString("jsonArray"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        View rootView = inflater.inflate(R.layout.painting_detail, container, false);
        painting_title =(BebasNeueTextView)rootView.findViewById(R.id.painting_title) ;
        size=(BebasNeueTextView)rootView.findViewById(R.id.size) ;
        type=(BebasNeueTextView)rootView.findViewById(R.id.type) ;
        medium=(BebasNeueTextView)rootView.findViewById(R.id.medium) ;
        markings=(BebasNeueTextView)rootView.findViewById(R.id.markings) ;
        year_created=(BebasNeueTextView)rootView.findViewById(R.id.year_created) ;
        aution_house=(BebasNeueTextView)rootView.findViewById(R.id.aution_house) ;
        lot_info=(BebasNeueTextView)rootView.findViewById(R.id.lot_info) ;
        date_of_auction=(BebasNeueTextView)rootView.findViewById(R.id.date_of_auction) ;
        presale_estimate=(EasyMoneyTextView)rootView.findViewById(R.id.presale_estimate) ;
        presale_estimate.showCurrencySymbol();
        presale_estimate.showCommas();
        price_realised=(BebasNeueTextView)rootView.findViewById(R.id.price_realised) ;
        painting=(ImageView)rootView.findViewById(R.id.painting);
        initView();


        return rootView;
    }

    public void addFragB() {
//        FragmentManager childFragMan = getChildFragmentManager();
//
//        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
//        FragmentDash fragB = new FragmentDash();
//        childFragTrans.add(R.id.fragA_LinearLayout, fragB);
//        childFragTrans.addToBackStack("B");
//        childFragTrans.commit();

        FragmentBrowse1 a2Fragment = new FragmentBrowse1();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("B");
        transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();

    }
    public void addFragBList() {
//        FragmentManager childFragMan = getChildFragmentManager();
//
//        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
//        FragmentDash fragB = new FragmentDash();
//        childFragTrans.add(R.id.fragA_LinearLayout, fragB);
//        childFragTrans.addToBackStack("B");
//        childFragTrans.commit();

        FragmentBrowseLists a2Fragment = new FragmentBrowseLists();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack("B");
        transaction.replace(R.id.fragA_LinearLayout, a2Fragment).commit();

    }



    public void initView(){
        Log.e("jasonBourn",jsonObject.toString());
        try {
            painting_title.setText(jsonObject.getString("title"));
            size.setText(jsonObject.getInt("size_w")+" X "+jsonObject.getInt("size_h")+" in.");
            type.setText(jsonObject.getString("material"));
            medium.setText(jsonObject.getString("medium"));
            markings.setText("unknown");
            year_created.setText(jsonObject.getString("yearofcreation"));
            aution_house.setText(jsonObject.getString("auction_house"));
            lot_info.setText("unknown");
            date_of_auction.setText(jsonObject.getString("sale_date_2"));
            String formattedString = getDecoratedStringFromNumber(jsonObject.getInt("sold_price_usd"));
            presale_estimate.setText(formattedString);
            price_realised.setText(jsonObject.getInt("sold_price_usd")+" USD");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String picas= null;
        try {
            picas = jsonObject.getString("painting_hd_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("dadada",""+picas);
        try {
            Picasso.with(getContext())
                    .load(picas)
                    .into(painting);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDecoratedStringFromNumber(long number)
    {
        String numberPattern = "#,###,###,###";
        String decoStr = "";

        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
        formatter.applyPattern("$" + " " + numberPattern);
        decoStr = formatter.format(number);

        return decoStr;
    }

}