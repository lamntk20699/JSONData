package com.example.jsonassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<ItemModel> items;
    ItemClickListener itemClickListener;

    public ItemAdapter(List<ItemModel> items, ItemClickListener itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = items.get(position);
        holder.textUsername.setText(item.getUsername());
        holder.textEmail.setText(item.getEmail());
        Picasso.get()
                .load("https://lebavui.github.io" + item.getAvatar())
                .into(holder.avatarImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView textUsername;
        TextView textEmail;
        ItemClickListener _itemClickListener;

        public ItemViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            avatarImage = itemView.findViewById(R.id.avatar_image);
            textUsername = itemView.findViewById(R.id.text_username);
            textEmail = itemView.findViewById(R.id.text_email);
            _itemClickListener = itemClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (_itemClickListener != null)
                        _itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
