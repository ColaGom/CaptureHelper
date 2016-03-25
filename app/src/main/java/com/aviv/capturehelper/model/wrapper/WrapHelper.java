package com.aviv.capturehelper.model.wrapper;

import com.aviv.capturehelper.controller.Master;
import com.aviv.capturehelper.model.dao.AlbumData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colabear on 2016-03-23.
 */
public class WrapHelper {

    public static List<WrapAlbumData> getAllAlbumData()
    {
        return convertAlbumData(Master.getInstance().getAlbumDataLoader().getAll());
    }

    public static List<WrapAlbumData> convertAlbumData(List<AlbumData> data){
        List<WrapAlbumData> result = new ArrayList<>();

        for(AlbumData ad:data){
            result.add(new WrapAlbumData(ad));
        }

        return result;
    }
}
