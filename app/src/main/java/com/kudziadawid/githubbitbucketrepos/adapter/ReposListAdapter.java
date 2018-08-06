package com.kudziadawid.githubbitbucketrepos.adapter;

import android.app.Activity;
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

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter {

    List<SingleRepo> reposList;
    Activity activity;

    private class ReposViewHolder extends RecyclerView.ViewHolder {

        public TextView repoNameTV;
        public TextView ownerNameTV;
        public ImageView avatarImage;

        public ReposViewHolder(View pItem) {
            super(pItem);
            repoNameTV = pItem.findViewById(R.id.repoNameTV);
            ownerNameTV = pItem.findViewById(R.id.ownerNameTV);
            avatarImage = pItem.findViewById(R.id.avatarImage);
        }
    }

    public ReposListAdapter(List<SingleRepo> reposList, Activity activity) {

        this.reposList = reposList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_single_repo, parent, false);

        //todo check connection on click
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
