package com.endercrest.arbuild.common.data;

public class MaterialProperties {

    public final float ambient;
    public final float diffuse;
    public final float specular;
    public final float specularPower;

    public MaterialProperties(float ambient, float diffuse, float specular, float specularPower) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.specularPower = specularPower;
    }

    public float getAmbient() {
        return ambient;
    }

    public float getDiffuse() {
        return diffuse;
    }

    public float getSpecular() {
        return specular;
    }

    public float getSpecularPower() {
        return specularPower;
    }
}
