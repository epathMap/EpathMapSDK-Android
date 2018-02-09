package com.shitu.epathmapguide.data;

/**
 * author:lfei
 */
public class MapData {

    /**
     * objectId : NRWmm5k5j9
     * picture : http://ipsmap.oss-cn-shanghai.aliyuncs.com/%E5%8C%BB%E6%85%A7.png
     * buildingId : 58f40844cba5c23cd9e30065
     * token : 9f09bb39bfe949b18defcf2d4326b34f
     * defaultFloorNumber : 1
     * defaultFloorName : 0
     * zoom : 22
     * angle : 54
     * powerThreshold : -100
     * inToOut : 3
     * outToIn : 5
     * gpsFloorlayer : 1
     */

    private String objectId;
    private String name;
    private String picture;
    private String buildingId;
    private String token;
    private String buildingIdBg;
    private String tokenBg;
    private int floorNumber;
    private String floorName;
    private float zoom;
    private float angle;
    private int powerThreshold;
    private int inToOut;
    private int outToIn;
    private int gpsFloorlayer;
    private float navigationZoom;

    public String getBuildingIdBg() {
        return buildingIdBg;
    }

    public void setBuildingIdBg(String buildingIdBg) {
        this.buildingIdBg = buildingIdBg;
    }

    public String getTokenBg() {
        return tokenBg;
    }

    public void setTokenBg(String tokenBg) {
        this.tokenBg = tokenBg;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getPowerThreshold() {
        return powerThreshold;
    }

    public void setPowerThreshold(int powerThreshold) {
        this.powerThreshold = powerThreshold;
    }

    public int getInToOut() {
        return inToOut;
    }

    public void setInToOut(int inToOut) {
        this.inToOut = inToOut;
    }

    public int getOutToIn() {
        return outToIn;
    }

    public void setOutToIn(int outToIn) {
        this.outToIn = outToIn;
    }

    public int getGpsFloorlayer() {
        return gpsFloorlayer;
    }

    public void setGpsFloorlayer(int gpsFloorlayer) {
        this.gpsFloorlayer = gpsFloorlayer;
    }

    public float getNavigationZoom() {
        return navigationZoom;
    }

    public void setNavigationZoom(float navigationZoom) {
        this.navigationZoom = navigationZoom;
    }
}
