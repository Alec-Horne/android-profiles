package alech.automation.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.*;
import android.widget.TextView;

import alech.automation.R;
import alech.automation.constants.ServiceConstants;
import alech.automation.objects.Profile;
import alech.automation.objects.ProfileSetter;
import alech.automation.receivers.AutoStartReceiver;
import alech.automation.receivers.HeadsetReceiver;
import alech.automation.services.LocationFinder;


//TODO: CREATE FIRST RUN FRAGMENT THAT WILL LOAD THE DEFAULT PROFILES INTO SHARED PREFS
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the users sdk version
        int sdkVersion = Build.VERSION.SDK_INT;

        // request initial needed permissions
        if (sdkVersion >= 23) {
            requestInitialPermissions();
        }

        //TODO: try using google play geofencing instead of LocationFinder service
        // start location service to get latitude/longitude on location change
        Intent intent = new Intent(MainActivity.this, LocationFinder.class);
        startService(intent);


        Button editProfileButton = (Button) findViewById(R.id.editProfile);
        editProfileButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, EditProfilesActivity.class));
            }
        });

        Button tabbedViewButton = (Button) findViewById(R.id.tabbedView);
        tabbedViewButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


        BroadcastReceiver locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TextView longText = (TextView) findViewById(R.id.longTextView);
                TextView latText = (TextView) findViewById(R.id.latTextView);
                double lat = intent.getDoubleExtra("Latitude", 0);
                double lon = intent.getDoubleExtra("Longitude", 0);
                longText.setText(Double.toString(lon));
                latText.setText(Double.toString(lat));
            }
        };
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction("LocationUpdate");
        registerReceiver(locationReceiver, iFilter);



        //TODO: register receivers in a separate file
        // register headset receiver to know when the headset is plugged in/removed
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        HeadsetReceiver headsetReceiver = new HeadsetReceiver();
        registerReceiver(headsetReceiver, intentFilter);

        // register auto start receiver to start services on boot
        intentFilter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        AutoStartReceiver autoStartReceiver = new AutoStartReceiver();
        registerReceiver(autoStartReceiver, intentFilter);

    }

    public void requestInitialPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                ServiceConstants.REQUEST_LOCATION_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    // called when the requestPermissions() function is called
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case ServiceConstants.REQUEST_LOCATION_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    Intent intent = new Intent(MainActivity.this, LocationFinder.class);
                    startService(intent);

                } else {
                    // permission denied, disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
