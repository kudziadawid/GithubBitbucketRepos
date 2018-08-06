package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repo;
import com.kudziadawid.githubbitbucketrepos.presenter.RepoPresenter;

public class MainActivity extends AppCompatActivity implements ContractMVP.View{

    private RepoPresenter repoPresenter;

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

        Repo repo = new Repo();

        repoPresenter = new RepoPresenter(repo);
        repoPresenter.attach(this);
        repoPresenter.getRepos(); //provide an adapter
    }

    @Override
    public void showRepos() {
        Toast.makeText(this, "Is online: " + isOnline(), Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
