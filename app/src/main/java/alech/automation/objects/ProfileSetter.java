package alech.automation.objects;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import alech.automation.constants.ProfileConstants;
import alech.automation.objects.Profile.status;
import alech.automation.objects.Profile.volume;

/**
 * Created by alech on 3/17/2017.
 */

public class ProfileSetter {
    private final String TAG = "ProfileSetter";

    private BluetoothAdapter bluetoothAdapter;
    private WifiManager wifiManager;
    private AudioManager audioManager;
    private ContentResolver contentResolver;

    public ProfileSetter(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        contentResolver = context.getApplicationContext().getContentResolver();
    }


    public void setBluetooth(status stat) {
        switch (stat) {
            case unchanged:
                break;
            case off:
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                }
                break;
            case on:
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                }
                break;
            default:
                Log.d(TAG, "Error changing bluetooth setting");
        }
    }


    public void setWifi(status stat) {
        switch (stat) {
            case unchanged:
                break;
            case off:
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
                break;
            case on:
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
                break;
            default:
                Log.d(TAG, "Error changing WiFi setting");
                break;
        }
    }


    public void setRingtoneVolume(volume vol) {
        switch (vol) {
            case unchanged:
                break;
            case vibrate:
                if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_VIBRATE) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
                break;
            case silent:
                if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                break;
            case normal:
                if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                break;
            default:
                Log.d(TAG, "Error setting ringtone volume");
                break;
        }
    }


    public void setMediaVolume(int vol) {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (vol < 0 || vol > audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) {
            Log.d(TAG, "Media volume invalid");
            return;
        }

        if (vol == ProfileConstants.DEFAULT) {
            return;
        }

        else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_SHOW_UI);
        }
    }


    public void setScreenBrightness(int brightness) {
        if (brightness < 0) {
            brightness = 0;
        }
        else if (brightness > 255) {
            brightness = 255;
        }

        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }


    public void setAutoScreenBrightness(status stat) {
        int brightnessMode = 0;

        try {
             brightnessMode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        }
        catch (Settings.SettingNotFoundException e) {
            Log.d(TAG, "Setting not found exception: " + e.toString());
            return;
        }

        switch (stat) {
            case unchanged:
                break;
            case off:
                if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                }
                break;
            case on:
                if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                }
                break;
            default:
                Log.d(TAG, "Error setting auto screen brightness");
                break;
        }
    }


    public void setScreenDisplayTimeout(int screenTimeout) {
        if (screenTimeout < 0) {
            screenTimeout = 0;
        }
        else if (screenTimeout > 5) {
            screenTimeout = 5;
        }

        int time;

        try {
            time = android.provider.Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_OFF_TIMEOUT);
        }
        catch (Settings.SettingNotFoundException e) {
            Log.d(TAG, "Setting not found exception: " + e.toString());
            return;
        }

        switch (screenTimeout) {
            case 1:
                time = 15000;
                break;
            case 2:
                time = 30000;
                break;
            case 3:
                time = 60000;
                break;
            case 4:
                time = 180000;
                break;
            case 5:
                time = 600000;
                break;
            default:
                Log.d(TAG, "Screen timeout value invalid");
                break;
        }

        android.provider.Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT, time);
    }

}
