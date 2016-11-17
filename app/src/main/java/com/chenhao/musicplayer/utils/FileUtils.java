package com.chenhao.musicplayer.utils;

import com.chenhao.musicplayer.bean.MusicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2016/11/3.
 */

public class FileUtils {

    public static List<MusicInfo> getFileList(String dir) {
        List<MusicInfo> listFile = new ArrayList<>();
        File dirFile = new File(dir);
        //如果不是目录文件，则直接返回
        if (dirFile.isDirectory()) {
            //获得文件夹下的文件列表，然后根据文件类型分别处理
            File[] files = dirFile.listFiles();
            if (null != files && files.length > 0) {
                //根据时间排序
//                Arrays.sort(files, new Comparator<File>() {
//                    public int compare(File f1, File f2) {
//                        return (int) (f1.lastModified() - f2.lastModified());
//                    }
//
//                    public boolean equals(Object obj) {
//                        return true;
//                    }
//                });
                for (File file : files) {
                    if(file.isHidden()){
                        continue;
                    }
                    MusicInfo musicInfo = new MusicInfo();
                    //如果不是目录，直接添加
                    if (!file.isDirectory()) {
                        if("mp3".equals(getExtensionName(file))){
                            String path = file.getAbsolutePath();
                            String name = file.getName().substring(0, file.getName().length() - 4);
                            musicInfo.setName(name);
                            musicInfo.setUrl(path);
                            listFile.add(musicInfo);
                        }
                    } else {
                        listFile.addAll(getFileList(file.getAbsolutePath()));
                    }
                }
            }
        }
        return listFile;
    }

    private static String getExtensionName(final File file){
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if(idx > 0){
            suffix = name.substring(idx + 1);
            }
        return suffix;
    }
}
