package com.aviv.capturehelper.controller;

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

    @Override
    public List<AlbumData> getAll() {

        List<AlbumData> result = getDao().loadAll();
        invaildData(result);
        return result;
    }

    private void invaildData(List<AlbumData> list)
    {
        Iterator<AlbumData> it = snapshotIterator(list);
        while (it.hasNext())
        {
            AlbumData album = it.next();
            if(!new File(album.getPath()).exists())
            {
                getDao().delete(album);
                list.remove(album);
            }
        }
    }

    private Iterator snapshotIterator(Collection collection) {

        return new ArrayList(collection).iterator();

    }

    @Override
    public void insert(AlbumData data) {
        getDao().insert(data);
    }

    @Override
    void insert(ArrayList<AlbumData> arr) {

    }
}
