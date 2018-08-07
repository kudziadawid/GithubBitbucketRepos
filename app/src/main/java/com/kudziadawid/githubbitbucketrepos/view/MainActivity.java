package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.adapter.ReposListAdapter;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repos;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;
import com.kudziadawid.githubbitbucketrepos.presenter.RepoPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContractMVP.View{

    private RepoPresenter repoPresenter;
    private ReposListAdapter reposListAdapter;
    private Button startButton;
    private RecyclerView reposListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isOnline()) {
            Intent intent = new Intent(this, OfflineActivity.class);
            finish();
            startActivity(intent);
            return;
        }

        startButton = findViewById(R.id.startButton);
        reposListRV = findViewById(R.id.reposList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reposListRV.setLayoutManager(layoutManager);

        RequestQueue queue = Volley.newRequestQueue(this);

        repoPresenter = new RepoPresenter(queue, this);
        repoPresenter.attach(this);
        repoPresenter.getRepos();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButton.setVisibility(View.GONE);
                if (!isOnline()) {
                    Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                    finish();
                    startActivity(intent);
                    return;
                }
                repoPresenter.injectRepos();
            }
        });
    }

    @Override
    public void showRepos(List<SingleRepo> reposList) {

        reposListAdapter = new ReposListAdapter(reposList, this, reposListRV);
        reposListRV.setAdapter(reposListAdapter);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
