package com.aviv.capturehelper.model.wrapper;

import com.aviv.capturehelper.model.dao.AlbumData;

/**
 * Created by Colabear on 2016-03-23.
 */
public class WrapAlbumData implements IWrapper<AlbumData> {

    private boolean isEmpty = false;
    private AlbumData mData = null;

    public WrapAlbumData()
    {
        isEmpty = true;
    }

    public WrapAlbumData(AlbumData data)
    {
        mData = data;
        isEmpty = false;
    }


    public boolean isEmpty()
    {
        return  isEmpty;
    }

    @Override
    public AlbumData getValue() {
        return mData;
    }
}
