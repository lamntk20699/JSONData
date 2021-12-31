package com.example.jsonassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ItemJSONAdapter extends RecyclerView.Adapter<ItemJSONAdapter.ItemViewHolder> {

    JSONArray jsonArray;

    public ItemJSONAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            JSONObject avatarObject = jsonObject.getJSONObject("avatar");
            JSONObject addressObject = jsonObject.getJSONObject("address");
            JSONObject companyObject = jsonObject.getJSONObject("company");

            holder.textUsername.setText(jsonObject.getString("username"));
            holder.textEmail.setText(jsonObject.getString("email"));
            Picasso.get()
                    .load("https://lebavui.github.io" + avatarObject.getString("thumbnail"))
                    .into(holder.avatarImage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView textUsername;
        TextView textEmail;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatar_image);
            textUsername = itemView.findViewById(R.id.text_username);
            textEmail = itemView.findViewById(R.id.text_email);
        }
    }
}
