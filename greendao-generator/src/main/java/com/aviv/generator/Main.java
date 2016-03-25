package com.aviv.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main {
    public static  void main(String[] args)
    {
        Schema schema = new Schema(1, "com.aviv.capturehelper.model.dao");

        Entity imageData = schema.addEntity("ImageData");
        imageData.addIdProperty();
        imageData.addStringProperty("name");
        imageData.addStringProperty("path");
        imageData.addIntProperty("AlbumIdx");
        imageData.addDateProperty("date");
        imageData.addStringProperty("jsonobject");

        Entity folderData = schema.addEntity("AlbumData");
        folderData.addIdProperty();
        folderData.addStringProperty("name");
        folderData.addStringProperty("path");
        folderData.addDateProperty("date");
        folderData.addStringProperty("jsonobject");

        try {
            DaoGenerator dg = new DaoGenerator();
            dg.generateAll(schema, "../app/src/main/java");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
