package com.chenhao.musicplayer.mod;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.activity.MainActivity;

/**
 * Created by chenhao on 2016/11/7.
 */

public class FragmentControl {

    private static FragmentControl inst = new FragmentControl();
    private FragmentControl(){}

    public static FragmentControl getInstance(){
        return inst;
    }

    public void showSubFrag(Fragment fragment, String ftag){
        FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_fragment, fragment, ftag);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.addToBackStack(ftag);
        transaction.commitAllowingStateLoss();
    }
    public void showWithPlayBarSubFrag(Fragment fragment, String ftag){
        FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.with_play_bar_content_fragment, fragment, ftag);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.addToBackStack(ftag);
        transaction.commitAllowingStateLoss();
    }
}
