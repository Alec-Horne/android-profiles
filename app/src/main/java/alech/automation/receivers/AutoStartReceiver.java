package alech.automation.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.os.IBinder;
import android.util.Log;

public class AutoStartReceiver extends BroadcastReceiver {
    private final String TAG = "AutoStartReceiver";

    public AutoStartReceiver() {
        super();
        Log.i(TAG, "Created");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, LocationListener.class);
            context.startService(intent);
            Log.d(TAG, "Location service started on boot");
        }
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
