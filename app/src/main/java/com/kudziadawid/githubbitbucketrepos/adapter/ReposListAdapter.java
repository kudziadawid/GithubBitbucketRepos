package com.kudziadawid.githubbitbucketrepos.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;
import com.kudziadawid.githubbitbucketrepos.view.DetailActivity;

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter {

    private List<SingleRepo> reposList;
    private Activity activity;
    private final RecyclerView reposListRV;

    private class ReposViewHolder extends RecyclerView.ViewHolder {

        private TextView repoNameTV;
        private TextView ownerNameTV;
        private ImageView avatarImage;

        private ReposViewHolder(View pItem) {
            super(pItem);
            repoNameTV = pItem.findViewById(R.id.repoNameTV);
            ownerNameTV = pItem.findViewById(R.id.ownerNameTV);
            avatarImage = pItem.findViewById(R.id.avatarImage);
        }
    }

    public ReposListAdapter(List<SingleRepo> reposList, Activity activity, RecyclerView reposListRV) {

        this.reposList = reposList;
        this.activity = activity;
        this.reposListRV = reposListRV;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_single_repo, parent, false);

        //todo check connection on click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailActivity.class);
                int itemPosition = reposListRV.getChildAdapterPosition(v);
                intent.putExtra("repoName", reposList.get(itemPosition).getRepoName());
                intent.putExtra("ownerName", reposList.get(itemPosition).getOwnerName());
                intent.putExtra("avatarUrl", reposList.get(itemPosition).getAvatarUrl());
                intent.putExtra("repoDescription", reposList.get(itemPosition).getRepoDescription());
                activity.startActivity(intent);
            }
        });

        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ReposViewHolder) holder).ownerNameTV.setText(reposList.get(position).getOwnerName());
        ((ReposViewHolder) holder).repoNameTV.setText(reposList.get(position).getRepoName());
        Glide.with(activity).load(reposList.get(position).getAvatarUrl()).into(((ReposViewHolder) holder).avatarImage);
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }
}
