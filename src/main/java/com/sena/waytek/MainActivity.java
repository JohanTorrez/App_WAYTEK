package com.sena.waytek;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  BottomNavigationView bottomNavigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    bottomNavigationView =  findViewById(R.id.bottom_navigation_view);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
    setInitialFragment();
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Fragment fragment = null;
    switch (item.getItemId()) {
      case R.id.action_device:
        fragment = new ProductosFragment();
        break;
      case R.id.action_share:
        fragment = new ShareFragment();
        break;
      case R.id.action_settings:
        fragment = new SettingsFragment();
        break;
    }
    replaceFragment(fragment);
    return true;
  }

  private void setInitialFragment() {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.main_fragment_placeholder, new ProductosFragment());
    fragmentTransaction.commit();
  }

  private void replaceFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.main_fragment_placeholder, fragment);
    fragmentTransaction.commit();
  }
}
