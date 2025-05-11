package com.example.deliverables3_databaseconnection_login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.UsernameViewHolder> {

    private List<String> usernameList;

    public Adapter(List<String> list){
        usernameList = list;
    }

    @NonNull
    @Override
    public Adapter.UsernameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recycler, parent, false);
        return new UsernameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.UsernameViewHolder holder, int position) {
        holder.usernameText.setText(usernameList.get(position));
    }

    @Override
    public int getItemCount() {
        return usernameList.size();
    }

    public class UsernameViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        public UsernameViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.tvUser);
        }
    }
}
