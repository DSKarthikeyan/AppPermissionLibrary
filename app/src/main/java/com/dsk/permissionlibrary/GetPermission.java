package com.dsk.permissionlibrary;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetPermission extends AppCompatActivity {

    String[] requiredAppPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SEND_SMS,
    };
    public static final int RequestPermissionCode = 7;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_layout);


        if (checkAppPermission(requiredAppPermissions)) {

        } else {
            checkAppPermission(requiredAppPermissions);
        }
    }

    public boolean checkAppPermission(String[] requiredAppPermissions) {
        int result;
        List<String> appPermission = new ArrayList<>();
        for (String p : requiredAppPermissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                appPermission.add(p);
            }
        }
        if (!appPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this, appPermission.toArray(new String[appPermission.size()]), RequestPermissionCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("DSK ", "Permission callback called-------");
        switch (requestCode) {
            case RequestPermissionCode: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    // Now you call here what ever you want :)
                } else {
                    String perStr = "";
                    String[] requestList = new String[permissions.length];
                    for (int i=0;i<permissions.length;i++) {
                        perStr += "\n" + permissions[i];
                        String tet = permissions[i];
                        requestList[i]=tet;
                        Log.d("DSK ", "Permission Denied list " + permissions[i]);
                    }
                    // permissions list of don't granted permission
//                    openSettings();
                    Snackbar.make(mLayout,  "Permission Denied",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                return;
            }
        }

    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
