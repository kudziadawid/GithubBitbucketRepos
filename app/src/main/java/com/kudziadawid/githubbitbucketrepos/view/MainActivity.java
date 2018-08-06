package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repos;
import com.kudziadawid.githubbitbucketrepos.presenter.RepoPresenter;

public class MainActivity extends AppCompatActivity implements ContractMVP.View{

    private RepoPresenter repoPresenter;
    private TextView textView;
    private Repos repos;

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

        textView = findViewById(R.id.textView);
//        RequestQueue queue = Volley.newRequestQueue(this);

        repos = new Repos();

        repoPresenter = new RepoPresenter(repos, this);
        repoPresenter.attach(this);
        repoPresenter.getRepos(); //provide an adapter
    }

    @Override
    public void showRepos() {
        textView.setText(repos.getRepoList().get(0).getOwnerName());
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
