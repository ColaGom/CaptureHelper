package com.aviv.capturehelper.controller;

import com.aviv.capturehelper.common.Util;
import com.aviv.capturehelper.model.dao.AlbumData;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by Colabear on 2016-03-22.
 */
public class AlbumDataLoader extends Loader<AlbumData> {

    private int mImageCount;

    @Override
    AbstractDao<AlbumData, Long> getDao() {
        return mSession.getAlbumDataDao();
    }

    @Override
    AlbumData get(long key) {
        return getDao().load(key);
    }

    @Override
    List<AlbumData> getRange(int start, int count) {
        return null;
    }

    public int getAlbumCount()
    {
        return getAll().size();
    }


    public int getImageCount()
    {
        return mImageCount;
    }

    @Override
    public List<AlbumData> getAll() {
        if(mLoadedList.size() == 0 && getDao().count() > 0) {
            mLoadedList = getDao().loadAll();
        }

        invaildData(mLoadedList);
        return mLoadedList;
    }

    private void invaildData(List<AlbumData> list)
    {
        Iterator<AlbumData> it = snapshotIterator(list);

        mImageCount = 0;

        while (it.hasNext())
        {
            AlbumData album = it.next();
            File dir = new File(album.getPath());
            if(!dir.exists())
            {
                getDao().delete(album);
                list.remove(album);
            }else{
                mImageCount += Util.getListOfImageFiles(dir).length;
            }
        }
    }

    private Iterator snapshotIterator(Collection collection) {

        return new ArrayList(collection).iterator();
    }

    @Override
    public void insert(AlbumData data) {
        if(!mLoadedList.contains(data))
            mLoadedList.add(data);
        getDao().insertOrReplace(data);
    }

    @Override
    void insert(ArrayList<AlbumData> arr) {
    }
}
