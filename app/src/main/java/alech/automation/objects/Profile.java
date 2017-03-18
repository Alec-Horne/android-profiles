package alech.automation.objects;

import alech.automation.constants.ProfileConstants;

/**
 * Created by alech on 3/6/2017.
 */

public class Profile {

    public enum status {
        on, off, unchanged
    }

    public enum volume {
        silent, vibrate, normal, unchanged
    }


    private String name;
    private volume ringerVolume = volume.unchanged;
    private int mediaVolume = ProfileConstants.DEFAULT;
    private int screenBrightness = ProfileConstants.DEFAULT;
    private status wifi = status.unchanged;
    private status bluetooth = status.unchanged;
    private status automaticBrightness = status.unchanged;
    private int screenDisplayTimeout = ProfileConstants.DEFAULT;


    public int getDisplayTimeout() {
        return screenDisplayTimeout;
    }

    public void setScreenDisplayTimeout(int screenDisplayTimeout) {
        this.screenDisplayTimeout = screenDisplayTimeout;
    }

    public status getAutomaticBrightness() {
        return automaticBrightness;
    }

    public void setAutomaticBrightness(status automaticBrightness) {
        this.automaticBrightness = automaticBrightness;
    }

    public void setName(String name) {
        this.name = name;
    }

    public volume getRingerVolume() {
        return ringerVolume;
    }

    public void setRingerVolume(volume ringerVolume) {
        this.ringerVolume = ringerVolume;
    }

    public int getMediaVolume() {
        return mediaVolume;
    }

    public void setMediaVolume(int mediaVolume) {
        this.mediaVolume = mediaVolume;
    }

    public int getScreenBrightness() {
        return screenBrightness;
    }

    public void setScreenBrightness(int screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public status getWifi() {
        return wifi;
    }

    public void setWifi(status wifi) {
        this.wifi = wifi;
    }

    public status getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(status bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getName() {
        return name;
    }

}
