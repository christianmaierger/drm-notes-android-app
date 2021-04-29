package com.example.drmapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drmapp.R;

public class ViewLastFragment extends Fragment {

    private ViewLastViewModel viewLastViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewLastViewModel =
                new ViewModelProvider(this).get(ViewLastViewModel.class);
        View root = inflater.inflate(R.layout.fragment_viewlast, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        viewLastViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}