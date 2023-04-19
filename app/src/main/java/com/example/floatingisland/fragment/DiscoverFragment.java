package com.example.floatingisland.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.floatingisland.R;
import com.example.floatingisland.utils.MyDiscoverAdapter;
import com.example.floatingisland.utils.MyPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class DiscoverFragment extends Fragment {

    private View DiscoverFragment;

    //getActivity()可能会抛出空指针异常
    private Activity activity;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        DiscoverFragment = inflater.inflate(R.layout.fragment_discover,container,false);

        SmartTabLayout tabLayout = DiscoverFragment.findViewById(R.id.tab_layout);
        ViewPager viewPager = DiscoverFragment.findViewById(R.id.view_pager);

        MyDiscoverAdapter adapter = new MyDiscoverAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);



        return DiscoverFragment;
    }

}