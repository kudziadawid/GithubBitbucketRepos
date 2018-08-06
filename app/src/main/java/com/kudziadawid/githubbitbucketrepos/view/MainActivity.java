package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button startButton;

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
        startButton = findViewById(R.id.startButton);

        RequestQueue queue = Volley.newRequestQueue(this);

        repoPresenter = new RepoPresenter(queue, this);
        repoPresenter.attach(this);
        repoPresenter.getRepos(); //provide an adapter

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repoPresenter.injectSome();
            }
        });
    }

    @Override
    public void showRepos(String name) {
        textView.setText(name);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
