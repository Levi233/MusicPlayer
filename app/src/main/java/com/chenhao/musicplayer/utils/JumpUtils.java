package com.chenhao.musicplayer.utils;

import com.chenhao.musicplayer.bean.AdArInfo;
import com.chenhao.musicplayer.bean.AdInfo;
import com.chenhao.musicplayer.bean.OnlineInfo;
import com.chenhao.musicplayer.bean.SongListInfo;
import com.chenhao.musicplayer.mod.FragmentControl;
import com.chenhao.musicplayer.online.SongListTabFragment;
import com.chenhao.musicplayer.online.WebFragment;

/**
 * Created by chenhao on 2016/11/26.
 */

public class JumpUtils {
    public static void jumpFragment( OnlineInfo info){
        if(info instanceof SongListInfo){
            SongListTabFragment f = SongListTabFragment.newInstance(info.getId(), info.getName(), info.getImg(),info.getDigest());
            FragmentControl.getInstance().showWithPlayBarSubFrag(f,SongListTabFragment.class.getSimpleName());
        }else if(info instanceof AdArInfo){
            WebFragment f = WebFragment.newInstance(((AdArInfo) info).getUrl(),info.getDesc());
            FragmentControl.getInstance().showWithPlayBarSubFrag(f,WebFragment.class.getSimpleName());
        }else if(info instanceof AdInfo){
            WebFragment f = WebFragment.newInstance(((AdInfo) info).getUrl(),info.getName());
            FragmentControl.getInstance().showWithPlayBarSubFrag(f,WebFragment.class.getSimpleName());
        }
    }
}
