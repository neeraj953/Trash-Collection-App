package com.example.dell.demofirebase1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private EditText editTextname;
    private EditText editTextphoneno;
    private EditText editTextpincode;
    private EditText editTextaddress;
    private Button buttonLogout;
    private Button buttonsave;
    private Button buttonMap;

    DatabaseReference databaseReference,databaseReference1;

    private String lattitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1=FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid());

        FirebaseUser user = firebaseAuth.getCurrentUser();



        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonMap = (Button) findViewById(R.id.buttonmap);
        buttonsave = (Button) findViewById(R.id.buttonmap);
        editTextname = (EditText) findViewById(R.id.editTextname);
        editTextphoneno = (EditText) findViewById(R.id.editTextphoneno);
        editTextaddress = (EditText) findViewById(R.id.editTextaddress);
        editTextpincode = (EditText) findViewById(R.id.editTextpincode);

        buttonsave.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonMap.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String Name = editTextname.getText().toString().trim();
        String Phone_No = editTextphoneno.getText().toString().trim();
        String Address = editTextaddress.getText().toString().trim();
        String Pin_Code = editTextpincode.getText().toString().trim();

        UserInformation userInformation = new UserInformation(Name, Phone_No, Address, Pin_Code);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information saved...", Toast.LENGTH_SHORT).show();
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                StoringArea storingArea=new StoringArea(lattitude,longitude);

                databaseReference1.child("Map_Location").setValue(storingArea);

                Toast.makeText(this, "Information saved...", Toast.LENGTH_SHORT).show();

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                StoringArea storingArea=new StoringArea(lattitude,longitude);

                databaseReference1.child("Map_Location").setValue(storingArea);

                Toast.makeText(this, "Information saved...", Toast.LENGTH_SHORT).show();

            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                StoringArea storingArea=new StoringArea(lattitude,longitude);

                databaseReference1.child("Map_Location").setValue(storingArea);

                Toast.makeText(this, "Information saved...", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

}


    @Override
    public void onClick(View view) {
        if(view==buttonLogout){
            finish();
            startActivity(new Intent(this , Dustbin_Selection.class));
        }
        if(view==buttonsave){
           saveUserInformation();
        }
        if(view==buttonMap){
            locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();

            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getLocation();
            }
        }
    }
}
