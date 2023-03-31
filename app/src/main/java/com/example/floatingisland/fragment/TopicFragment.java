package com.example.floatingisland.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.floatingisland.R;

public class TopicFragment extends Fragment {

    private View TopicFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        TopicFragment = inflater.inflate(R.layout.fragment_topic,container,false);





        return TopicFragment;
    }

}