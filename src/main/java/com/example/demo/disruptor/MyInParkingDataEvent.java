package com.example.demo.disruptor;

/**
 * @author Steve
 */
public class MyInParkingDataEvent {

    // 车牌号
    private String carLicense;

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

}