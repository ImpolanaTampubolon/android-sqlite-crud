package com.venpillar.testprogrammer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.venpillar.testprogrammer.model.PersonModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ViewHolder> {
    List<PersonModel> list;

    public GithubAdapter(List<PersonModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_github, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gitId.setText(list.get(position).getId());
        holder.gitName.setText(list.get(position).getName());
        holder.gitUrl.setText(list.get(position).getHtmlUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        TextView gitId;
        TextView gitName;
        TextView gitUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gitId = itemView.findViewById(R.id.gitId);
            gitName = itemView.findViewById(R.id.gitName);
            gitUrl = itemView.findViewById(R.id.gitUrl);
        }
    }
}
