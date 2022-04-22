package com.realdo.com;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    themeColor();
  }

  private void themeColor() {
    getWindow().setStatusBarColor(
        ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
    getWindow().setNavigationBarColor(
        ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
  }
}
