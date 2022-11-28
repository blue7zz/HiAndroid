package com.example.hilibrary.proxy;

/**
 * 商品
 */
public class TV {

    private String name;//名称

    private String address;//生产地

    public TV(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TV{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
