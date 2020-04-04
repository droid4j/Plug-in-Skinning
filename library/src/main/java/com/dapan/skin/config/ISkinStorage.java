package com.dapan.skin.config;

/**
 * Created by per4j
 * on 2020-04-04
 */
public interface ISkinStorage {

    void saveSkinPath(String path);
    String getSkinPath();
    boolean checkSkin();
    void clearSkin();
}
