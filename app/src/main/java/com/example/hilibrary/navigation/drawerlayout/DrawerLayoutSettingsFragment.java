package com.example.hilibrary.navigation.drawerlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hilibrary.R;
import com.example.nav_annotation.Destination;


@Destination(pageUrl = "main/tabs/DrawerLayoutSettingsFragment", asStarter = false)
public class DrawerLayoutSettingsFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        return inflater.inflate(R.layout.fragment_drawer_layout_settings, container, false);
    }
}
