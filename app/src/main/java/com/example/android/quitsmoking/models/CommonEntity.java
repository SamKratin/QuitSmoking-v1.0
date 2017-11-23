package com.example.android.quitsmoking.models;

/**
 * This class was created for the purpose of learning.
 * Created by sam on 2017-11-09.
 */

abstract class CommonEntity implements Entity {
    protected int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
