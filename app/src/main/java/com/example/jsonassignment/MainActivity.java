package com.example.jsonassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    RecyclerView recyclerView;
    List<ItemModel> items;
    ItemAdapter adapter;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            Log.v("TAG", "Connected");
        else
            Log.v("TAG", "Not connected");

        items = new ArrayList<>();
        adapter = new ItemAdapter(items, this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new DownloadTask(this).execute("https://lebavui.github.io/jsons/users.json");
    }

    @Override
    public void onItemClick(int position) {
        Log.v("TAG", items.get(position).getUsername());

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        intent.putExtra("username", items.get(position).getUsername());
        intent.putExtra("name", items.get(position).getName());
        intent.putExtra("email", items.get(position).getEmail());
        intent.putExtra("address", items.get(position).getAddress());
        intent.putExtra("phone", items.get(position).getPhone());
        intent.putExtra("company", items.get(position).getCompany());
        intent.putExtra("photo", items.get(position).getPhoto());

        startActivity(intent);
    }

    class DownloadTask extends AsyncTask<String, Void, List<ItemModel>> {

        ProgressDialog dialog;
        Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Loading data... ");
            dialog.show();
        }

        @Override
        protected List<ItemModel> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                Log.v("TAG", "Sending 'GET' request to URL : " + url.toString());
                Log.v("TAG", "Response Code : " + responseCode);

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null)
                    builder.append(line);
                reader.close();

                String jsonString = builder.toString();
//                Log.v("TAG", jsonString);
                Log.v("TAG", "Check");

//                List<ItemModel> items = new ArrayList<>();
                jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String username = jsonObject.getString("username");
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    JSONObject buff = jsonObject.getJSONObject("address");
                    String address = buff.getString("suite") + ", " + buff.getString("street") + ", " + buff.getString("city");
                    String phone = jsonObject.getString("phone");
                    String company = jsonObject.getJSONObject("company").getString("name");
                    String avatar = jsonObject.getJSONObject("avatar").getString("thumbnail");
                    String photo = jsonObject.getJSONObject("avatar").getString("photo");

                    ItemModel item = new ItemModel(id, username, name, email, address, phone, company, avatar, photo);
                    items.add(item);
                }
                return  items;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ItemModel> items) {
            dialog.dismiss();

            if (items != null) {
                Log.v("TAG", "Size :" + items.size());
                adapter.notifyDataSetChanged();
            }
        }
    }

}