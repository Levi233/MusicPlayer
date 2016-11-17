package com.chenhao.musicplayer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/11/15.
 */

public class RootInfo {
    private List<Section> sections = new ArrayList<Section>();

    public void add(Section section){
        sections.add(section);
    }

    public List<Section> getSections(){
        return sections;
    }

    @Override
    public String toString() {
        return "RootInfo{" +
                "sections=" + sections +
                '}';
    }
}
