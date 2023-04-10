package com.example.floatingisland.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floatingisland.R;

public class searchUsersFragment extends Fragment {

    private View searchUsersFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        searchUsersFragment = inflater.inflate(R.layout.fragment_searchusers, container, false);




        return searchUsersFragment;
    }
}