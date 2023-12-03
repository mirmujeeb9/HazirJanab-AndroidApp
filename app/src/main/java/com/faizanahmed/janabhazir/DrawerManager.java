package com.faizanahmed.janabhazir;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerManager {
    private Activity activity;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public DrawerManager(Activity activity, DrawerLayout drawerLayout, NavigationView navigationView) {
        this.activity = activity;
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        setupDrawer();
    }

    private void setupDrawer() {
        Toast.makeText(activity, "Profile", Toast.LENGTH_SHORT).show();
        navigationView.setNavigationItemSelectedListener(item -> {
            Toast.makeText(activity, "LALA", Toast.LENGTH_SHORT).show();
            if(item.getItemId()==R.id.iProfile) {
                    openProfile();
                //make a toast here
                Toast.makeText(activity, "Profile", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void openProfile() {
        Intent intent = new Intent(activity, userProfile_6.class);
        activity.startActivity(intent);
    }

    // Additional methods to manage the drawer...
}
