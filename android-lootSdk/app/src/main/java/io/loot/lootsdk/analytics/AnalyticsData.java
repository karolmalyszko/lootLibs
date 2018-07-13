package io.loot.lootsdk.analytics;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnalyticsData implements Serializable {

    @SerializedName("app_build_number")
    String buildNumber;

    @SerializedName("app_version_string")
    String appVersionString;

    @SerializedName("app_release")
    String appRelease;

    @SerializedName("app_version")
    String appVersion;

    @SerializedName("android_app_version_code")
    String androidAppVersionCode;

    @SerializedName("android_app_version")
    String androidAppVersion;

    @SerializedName("bluetooth_enabled")
    boolean isBluetooth;

    @SerializedName("bluetooth_version")
    String bluetoothVersion;

    @SerializedName("brand")
    String brand;

    @SerializedName("android_brand")
    String androidBrand;

    @SerializedName("carrier")
    String carrier;

    @SerializedName("has_nfc")
    boolean isNfc;

    @SerializedName("has_telephone")
    boolean isTelephone;


    @SerializedName("manufacturer")
    String manufacturer;


    @SerializedName("model")
    String model;


    @SerializedName("android_manufacturer")
    String androidManufacturer;


    @SerializedName("android_model")
    String androidModel;


    @SerializedName("android_os")
    String androidOs;


    @SerializedName("os")
    String system;


    @SerializedName("os_version")
    String systemVersion;


    @SerializedName("android_os_version")
    String androidSystemVersion;


    @SerializedName("screen_dpi")
    int dpi;

    @SerializedName("screen_height")
    int screenHeight;


    @SerializedName("screen_width")
    int screenWidth;


    @SerializedName("wifi")
    boolean isWifiConnected;


    @SerializedName("google_play_services")
    boolean isGooglePlay;

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getAppVersionString() {
        return appVersionString;
    }

    public void setAppVersionString(String appVersionString) {
        this.appVersionString = appVersionString;
    }

    public String getAppRelease() {
        return appRelease;
    }

    public void setAppRelease(String appRelease) {
        this.appRelease = appRelease;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAndroidAppVersionCode() {
        return androidAppVersionCode;
    }

    public void setAndroidAppVersionCode(String androidAppVersionCode) {
        this.androidAppVersionCode = androidAppVersionCode;
    }

    public String getAndroidAppVersion() {
        return androidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        this.androidAppVersion = androidAppVersion;
    }

    public boolean isBluetooth() {
        return isBluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        isBluetooth = bluetooth;
    }

    public String getBluetoothVersion() {
        return bluetoothVersion;
    }

    public void setBluetoothVersion(String bluetoothVersion) {
        this.bluetoothVersion = bluetoothVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAndroidBrand() {
        return androidBrand;
    }

    public void setAndroidBrand(String androidBrand) {
        this.androidBrand = androidBrand;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public boolean isNfc() {
        return isNfc;
    }

    public void setNfc(boolean nfc) {
        isNfc = nfc;
    }

    public boolean isTelephone() {
        return isTelephone;
    }

    public void setTelephone(boolean telephone) {
        isTelephone = telephone;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidManufacturer() {
        return androidManufacturer;
    }

    public void setAndroidManufacturer(String androidManufacturer) {
        this.androidManufacturer = androidManufacturer;
    }

    public String getAndroidModel() {
        return androidModel;
    }

    public void setAndroidModel(String androidModel) {
        this.androidModel = androidModel;
    }

    public String getAndroidOs() {
        return androidOs;
    }

    public void setAndroidOs(String androidOs) {
        this.androidOs = androidOs;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getAndroidSystemVersion() {
        return androidSystemVersion;
    }

    public void setAndroidSystemVersion(String androidSystemVersion) {
        this.androidSystemVersion = androidSystemVersion;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public boolean isWifiConnected() {
        return isWifiConnected;
    }

    public void setWifiConnected(boolean wifiConnected) {
        isWifiConnected = wifiConnected;
    }

    public boolean isGooglePlay() {
        return isGooglePlay;
    }

    public void setGooglePlay(boolean googlePlay) {
        isGooglePlay = googlePlay;
    }

    public void setBuildNumbers(String buildNumber) {
        setBuildNumber(buildNumber);
        setAppRelease(buildNumber);
        setAndroidAppVersionCode(buildNumber);
    }

    public void setAppVersions(String version) {
        setAppVersionString(version);
        setAppVersion(version);
        setAndroidAppVersion(version);
    }

    public void setBrands(String brand) {
        setBrand(brand);
        setAndroidBrand(brand);
        setManufacturer(brand);
        setAndroidManufacturer(brand);
    }

    public void setModels(String model) {
        setModel(model);
        setAndroidModel(model);
    }

    public void setOperatingSystems(String system) {
        setSystem(system);
        setAndroidOs(system);
    }

    public void setOSVersions(String version) {
        setAndroidSystemVersion(version);
        setSystemVersion(version);
    }

}
