package com.endercrest.arbuild.common.data;

import com.google.ar.core.Anchor;

import java.util.UUID;

public class ARObject {

    private UUID uuid;
    private String objectKey;
    private Anchor anchor;

    public ARObject(String objectKey, Anchor anchor) {
        this.uuid = UUID.randomUUID();
        this.objectKey = objectKey;
        this.anchor = anchor;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public Anchor getAnchor() {
        return anchor;
    }
}
