package com.kudziadawid.githubbitbucketrepos.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;

import java.util.List;

public class ReposListAdapter extends RecyclerView.Adapter {

    List<SingleRepo> reposList;

    private class ReposViewHolder extends RecyclerView.ViewHolder {

        public TextView repoNameTV;
        public TextView ownerNameTV;

        public ReposViewHolder(View pItem) {
            super(pItem);
            repoNameTV = pItem.findViewById(R.id.repoNameTV);
            ownerNameTV = pItem.findViewById(R.id.ownerNameTV);
        }
    }

    public ReposListAdapter(List<SingleRepo> reposList) {

        this.reposList = reposList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_single_repo, parent, false);

        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ReposViewHolder) holder).ownerNameTV.setText(reposList.get(position).getOwnerName());
        ((ReposViewHolder) holder).repoNameTV.setText(reposList.get(position).getRepoName());
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }
}
