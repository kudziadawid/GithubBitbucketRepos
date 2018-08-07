package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kudziadawid.githubbitbucketrepos.R;
import com.kudziadawid.githubbitbucketrepos.adapter.ReposListAdapter;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;
import com.kudziadawid.githubbitbucketrepos.presenter.RepoPresenter;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ContractMVP.View{

    private RepoPresenter repoPresenter;
    private ReposListAdapter reposListAdapter;
    private Button startButton;
    private RecyclerView reposListRV;
    private Toolbar myToolbar;
    private boolean startClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());

        if (!isOnline()) {
            Intent intent = new Intent(this, OfflineActivity.class);
            finish();
            startActivity(intent);
            return;
        }

        startButton = findViewById(R.id.startButton);
        reposListRV = findViewById(R.id.reposList);
        myToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);

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
                startClicked = true;
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

        reposListRV.setAdapter(null);
        reposListAdapter = new ReposListAdapter(reposList, this, reposListRV);
        reposListRV.setAdapter(reposListAdapter);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                if (startClicked) {
                    repoPresenter.sortOrUnsort();
                }
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repoPresenter.detach();
    }
}
