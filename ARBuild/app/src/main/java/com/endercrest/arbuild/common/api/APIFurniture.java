package com.endercrest.arbuild.common.api;

import java.io.InputStream;

public class APIFurniture {

    private String barcode;
    private String name;
    private InputStream objFile;
    private InputStream imgFile;

    public APIFurniture(String barcode, String name, InputStream objFile, InputStream imgFile) {
        this.barcode = barcode;
        this.name = name;
        this.objFile = objFile;
        this.imgFile = imgFile;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public InputStream getObjFile() {
        return objFile;
    }

    public InputStream getImgFile() {
        return imgFile;
    }
}
