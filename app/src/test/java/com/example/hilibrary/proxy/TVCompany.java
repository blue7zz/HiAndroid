package com.example.hilibrary.proxy;

/**
 * 接口
 */
public interface TVCompany {

    /**
     * 生产电视机
     * @return
     */
    public TV produceTv();

    /**
     * 维修电视机
     * @param tv
     * @return
     */
    public TV repair(TV tv);
}
