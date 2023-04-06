package com.example.floatingisland.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.floatingisland.R;

public class DiscoverFragment extends Fragment {

    private View DiscoverFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        DiscoverFragment = inflater.inflate(R.layout.fragment_discover,container,false);





        return DiscoverFragment;
    }

}