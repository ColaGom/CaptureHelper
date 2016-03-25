package com.aviv.capturehelper.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.aviv.capturehelper.model.dao.ImageData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IMAGE_DATA".
*/
public class ImageDataDao extends AbstractDao<ImageData, Long> {

    public static final String TABLENAME = "IMAGE_DATA";

    /**
     * Properties of entity ImageData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Path = new Property(2, String.class, "path", false, "PATH");
        public final static Property AlbumIdx = new Property(3, Integer.class, "AlbumIdx", false, "ALBUM_IDX");
        public final static Property Date = new Property(4, java.util.Date.class, "date", false, "DATE");
        public final static Property Jsonobject = new Property(5, String.class, "jsonobject", false, "JSONOBJECT");
    };


    public ImageDataDao(DaoConfig config) {
        super(config);
    }
    
    public ImageDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IMAGE_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"PATH\" TEXT," + // 2: path
                "\"ALBUM_IDX\" INTEGER," + // 3: AlbumIdx
                "\"DATE\" INTEGER," + // 4: date
                "\"JSONOBJECT\" TEXT);"); // 5: jsonobject
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IMAGE_DATA\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ImageData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(3, path);
        }
 
        Integer AlbumIdx = entity.getAlbumIdx();
        if (AlbumIdx != null) {
            stmt.bindLong(4, AlbumIdx);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(5, date.getTime());
        }
 
        String jsonobject = entity.getJsonobject();
        if (jsonobject != null) {
            stmt.bindString(6, jsonobject);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ImageData readEntity(Cursor cursor, int offset) {
        ImageData entity = new ImageData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // path
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // AlbumIdx
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // date
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // jsonobject
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImageData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAlbumIdx(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setJsonobject(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ImageData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ImageData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
