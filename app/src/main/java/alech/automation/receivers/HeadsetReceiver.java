package alech.automation.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by alech on 2/20/2017.
 */

public class HeadsetReceiver extends BroadcastReceiver {

    private final String TAG = "HeadsetReceiver";

    public HeadsetReceiver() {
        super();
        Log.i(TAG, "Created");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case (0):
                    Log.d(TAG, "Headset unplugged");
                    break;
                case (1):
                    Log.d(TAG, "Headset plugged");
                    break;
                default:
                    Log.d(TAG, "Error");
            }
        }
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
