package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further.Description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tourismfyp.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptioFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String desc;

    public DescriptioFragment(String desc) {
        this.desc = desc;
    }

    public DescriptioFragment() {
    }
 public static DescriptioFragment newInstance(String param1, String param2) {
        DescriptioFragment fragment = new DescriptioFragment();
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
        View view= inflater.inflate(R.layout.fragment_descriptio, container, false);

        TextView textView=view.findViewById(R.id.Description);
        desc = desc.replaceAll("<(.*?)\\>"," ");//Removes all items in brackets
        desc = desc.replaceAll("<(.*?)\\\n"," ");//Must be undeneath
        desc = desc.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
        desc = desc.replaceAll("&nbsp;"," ");
        desc = desc.replaceAll("&amp;"," ");
        textView.setText(desc);
        return view;
    }
}