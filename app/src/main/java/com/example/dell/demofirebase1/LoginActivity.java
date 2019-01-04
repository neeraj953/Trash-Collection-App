package com.example.dell.demofirebase1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button butttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),Dustbin_Selection.class));
        }

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        butttonSignin=(Button)findViewById(R.id.buttonRegister);
        textViewSignUp=(TextView)findViewById(R.id.textViewSignUp);

        butttonSignin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }
    private void userLogin(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            //stop executing further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            //stop executing further
            return;
        }
        // if all ok
        // first show progres dialogue
        progressDialog.setMessage("User Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //user is registered
                            //move to profile
                            finish();
                            startActivity(new Intent(getApplicationContext(),Dustbin_Selection.class));
                        }else {
                            Toast.makeText(LoginActivity.this,"Login Error! Please enter again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==butttonSignin){
            userLogin();
        }
        if (view == textViewSignUp) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
