package com.example.jsonassignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    TextView textName;
    TextView textUsername;
    TextView textEmail;
    TextView textPhone;
    TextView textAddress;
    TextView textCompany;
    ImageView photoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        textName = findViewById(R.id.text_name);
        textUsername = findViewById(R.id.text_personal_username);
        textEmail = findViewById(R.id.text_personal_email);
        textPhone = findViewById(R.id.text_phone);
        textAddress = findViewById(R.id.text_address);
        textCompany = findViewById(R.id.text_company);
        photoImage = findViewById(R.id.photo_image);

        textName.setText(getIntent().getStringExtra("name"));
        textUsername.setText(getIntent().getStringExtra("username"));
        textEmail.setText(getIntent().getStringExtra("email"));
        textPhone.setText(getIntent().getStringExtra("phone"));
        textAddress.setText(getIntent().getStringExtra("address"));
        textCompany.setText(getIntent().getStringExtra("company"));
        Picasso.get()
                .load("https://lebavui.github.io" + getIntent().getStringExtra("photo"))
                .into(photoImage);
    }

}