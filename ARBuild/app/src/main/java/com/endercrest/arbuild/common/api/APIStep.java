package com.endercrest.arbuild.common.api;

import java.io.InputStream;

public class APIStep {

    private String title;
    private String msg;
    private InputStream objStream;
    private InputStream textureStream;

    //private InputStream imageStream;


    public APIStep(String title, String msg, InputStream objStream, InputStream textureStream) {
        this.title = title;
        this.msg = msg;
        this.objStream = objStream;
        this.textureStream = textureStream;
    }

    public String getTitle() {
        return title;
    }

    public String getMsg() {
        return msg;
    }

    public InputStream getObjStream() {
        return objStream;
    }

    public InputStream getTextureStream() {
        return textureStream;
    }
}
