package com.example.newsapplication.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.example.newsapplication.R;


public class TransactionFragmentHelper {

  public static void addFragment(int layoutID, Fragment openFrag, String tag, FragmentManager supportfm){
    supportfm.beginTransaction()
        .add(layoutID, openFrag, tag)
        .commit();
  }
  public static void replaceFragment(int layoutID, Fragment openFrag, String tag, FragmentManager supportfm, boolean addToBackStack){
    if(addToBackStack) {
      supportfm.beginTransaction()
          .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
          .replace(layoutID, openFrag, tag)
          .addToBackStack(tag)
          .commit();
    }
    else{
      supportfm.beginTransaction()
          .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
          .replace(layoutID, openFrag, tag)
          .addToBackStack(null)
          .commit();
    }

  }

}
