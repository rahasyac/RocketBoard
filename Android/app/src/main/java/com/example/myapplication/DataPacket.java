package com.example.myapplication;

public class DataPacket {
    private String GPS_time;
    private float  GPS_latitude;
    private char   GPS_northSouth;
    private float  GPS_longitude;
    private char   GPS_eastWest;
    private float  IMU_accelerationX;
    private float  IMU_accelerationY;
    private float  IMU_accelerationZ;
    private float  IMU_temperature;
    private float  IMU_gyroX;
    private float  IMU_gyroY;
    private float  IMU_gyroZ;
    private float  Alt_altitude;

    public DataPacket(String input){
        String values[] = input.split(",");

        GPS_time          = values[0];
        GPS_latitude      = (Float.valueOf(values[1])).floatValue();
        GPS_northSouth    = values[2].charAt(0);
        GPS_longitude     = (Float.valueOf(values[3])).floatValue();
        GPS_eastWest      = values[4].charAt(0);
        IMU_accelerationX = (Float.valueOf(values[5])).floatValue();
        IMU_accelerationY = (Float.valueOf(values[6])).floatValue();
        IMU_accelerationZ = (Float.valueOf(values[7])).floatValue();
        IMU_temperature   = (Float.valueOf(values[8])).floatValue();
        IMU_gyroX         = (Float.valueOf(values[9])).floatValue();
        IMU_gyroY         = (Float.valueOf(values[10])).floatValue();
        IMU_gyroZ         = (Float.valueOf(values[11])).floatValue();
        Alt_altitude      = (Float.valueOf(values[12])).floatValue();

        if(GPS_northSouth == 'S'){
            GPS_latitude = GPS_latitude * -1;
        }

        if(GPS_eastWest == 'W'){
            GPS_longitude = GPS_longitude * -1;
        }

    }

    //Getters and Setters

    public String getGPS_time() {
        return GPS_time;
    }

    public void setGPS_time(String GPS_time) {
        this.GPS_time = GPS_time;
    }

    public float getGPS_latitude() {
        return GPS_latitude;
    }

    public void setGPS_latitude(float GPS_latitude) {
        this.GPS_latitude = GPS_latitude;
    }

    public char getGPS_northSouth() {
        return GPS_northSouth;
    }

    public void setGPS_northSouth(char GPS_northSouth) {
        this.GPS_northSouth = GPS_northSouth;
    }

    public float getGPS_longitude() {
        return GPS_longitude;
    }

    public void setGPS_longitude(float GPS_longitude) {
        this.GPS_longitude = GPS_longitude;
    }

    public char getGPS_eastWest() {
        return GPS_eastWest;
    }

    public void setGPS_eastWest(char GPS_eastWest) {
        this.GPS_eastWest = GPS_eastWest;
    }

    public float getIMU_accelerationX() {
        return IMU_accelerationX;
    }

    public void setIMU_accelerationX(float IMU_accelerationX) {
        this.IMU_accelerationX = IMU_accelerationX;
    }

    public float getIMU_accelerationY() {
        return IMU_accelerationY;
    }

    public void setIMU_accelerationY(float IMU_accelerationY) {
        this.IMU_accelerationY = IMU_accelerationY;
    }

    public float getIMU_accelerationZ() {
        return IMU_accelerationZ;
    }

    public void setIMU_accelerationZ(float IMU_accelerationZ) {
        this.IMU_accelerationZ = IMU_accelerationZ;
    }

    public float getIMU_temperature() {
        return IMU_temperature;
    }

    public void setIMU_temperature(float IMU_temperature) {
        this.IMU_temperature = IMU_temperature;
    }

    public float getIMU_gyroX() {
        return IMU_gyroX;
    }

    public void setIMU_gyroX(float IMU_gyroX) {
        this.IMU_gyroX = IMU_gyroX;
    }

    public float getIMU_gyroY() {
        return IMU_gyroY;
    }

    public void setIMU_gyroY(float IMU_gyroY) {
        this.IMU_gyroY = IMU_gyroY;
    }

    public float getIMU_gyroZ() {
        return IMU_gyroZ;
    }

    public void setIMU_gyroZ(float IMU_gyroZ) {
        this.IMU_gyroZ = IMU_gyroZ;
    }

    public float getAlt_altitude() {
        return Alt_altitude;
    }

    public void setAlt_altitude(float alt_altitude) {
        Alt_altitude = alt_altitude;
    }

    public String toString(){

        String returnString = "";
        returnString += GPS_time + ",";
        returnString += Float.toString(GPS_latitude) + ",";
        returnString += GPS_northSouth + ",";
        returnString += Float.toString(GPS_longitude) + ",";
        returnString += GPS_eastWest + ",";
        returnString += Float.toString(IMU_accelerationX) + ",";
        returnString += Float.toString(IMU_accelerationY) + ",";
        returnString += Float.toString(IMU_accelerationZ) + ",";
        returnString += Float.toString(IMU_temperature) + ",";
        returnString += Float.toString(IMU_gyroX) + ",";
        returnString += Float.toString(IMU_gyroY) + ",";
        returnString += Float.toString(IMU_gyroZ) + ",";

        return returnString;

    }

}
