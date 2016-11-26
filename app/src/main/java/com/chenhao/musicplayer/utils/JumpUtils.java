package com.chenhao.musicplayer.utils;

import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.SongListInfo;
import com.chenhao.musicplayer.mod.FragmentControl;
import com.chenhao.musicplayer.online.SongListTabFragment;

/**
 * Created by chenhao on 2016/11/26.
 */

public class JumpUtils {
    public static void jumpFragment( OnlineInfo info){
        if(info instanceof SongListInfo){
            SongListTabFragment f = SongListTabFragment.newInstance(info.getId(), info.getName(), info.getImg());
            FragmentControl.getInstance().showSubFrag(f,SongListTabFragment.class.getName());
        }
    }
}
