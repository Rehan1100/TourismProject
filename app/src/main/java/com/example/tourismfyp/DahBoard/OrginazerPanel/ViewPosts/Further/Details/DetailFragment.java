package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.Details;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tourismfyp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    public TextView cinclabel,Perheadlabel,Daylabel,TitleLabel,MobileLabel;
    public TextView cnicvalue,PerheadValue,Dayvalue,TitleValue,MobileValue;
    private static  final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String city;
    String cnic;
    String currentdate ;
    String desc ;
    String id ;
    String item;
    String latitude ;
    String loginuser;
    String longitude;
    String perhead;
    String phoneNumber;
    String title ;
    String url ;
    String deadlinedate ;
    String category;


    public DetailFragment() {

    }

    public DetailFragment(String cnic, String currentdate, String desc, String id, String latitude, String longitude, String perhead, String phoneNumber, String url, String title, String deadlinedate,String city,String catrgory,String item) {
        this.cnic=cnic;
        this.currentdate=currentdate;
        this.desc=desc;
        this.id=id;
        this.item=item;
        this.latitude=latitude;
        this.city=city;
        this.longitude=longitude;
        this.perhead=perhead;
        this.phoneNumber=phoneNumber;
        this.title=title;
        this.deadlinedate=deadlinedate;
        this.category=catrgory;

    }

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        cinclabel = view.findViewById(R.id.cniclabel);
        cnicvalue = view.findViewById(R.id.cnicvalue);
        Perheadlabel = view.findViewById(R.id.Perheadlabel);
        PerheadValue = view.findViewById(R.id.Perheadvalue);
        Daylabel= view.findViewById(R.id.Daylabel);
        Dayvalue = view.findViewById(R.id.Dayvalue);
        TitleLabel=view.findViewById(R.id.titlelabel);
        TitleValue=view.findViewById(R.id.titlevalue);
        MobileLabel=view.findViewById(R.id.phonelabel);
        MobileValue=view.findViewById(R.id.phonevalue);

        cinclabel.setText("cnic"+"");
        cnicvalue.setText(cnic);

        if (category.equals("PostCars"))
        {
        Perheadlabel.setText("Rent");
        PerheadValue.setText(perhead);
        }
        else {
            Perheadlabel.setText("Perhead");
            PerheadValue.setText(perhead);
        }

        Daylabel.setText("No of Days");
        Dayvalue.setText(item);

        MobileLabel.setText("PhoneNumber");
        MobileValue.setText(phoneNumber);


        return view;
    }
}