package com.example.dell.demofirebase1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dustbin_Selection extends AppCompatActivity implements View.OnClickListener {



    private CardView Green_card, Blue_card;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }





        Green_card=(CardView)findViewById(R.id.Green_card);
        Blue_card=(CardView)findViewById(R.id.Blue_card);

        Green_card.setOnClickListener(this);
        Blue_card.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        if(view==Green_card){
            startActivity(new Intent(this, Green_Selection.class));
            Toast.makeText(this, "Green...", Toast.LENGTH_SHORT).show();
        }
        if(view==Blue_card){
            Toast.makeText(this, "Blue...", Toast.LENGTH_SHORT).show();
        }
    }

}
