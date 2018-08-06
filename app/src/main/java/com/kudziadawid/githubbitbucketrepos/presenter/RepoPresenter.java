package com.kudziadawid.githubbitbucketrepos.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repos;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepoPresenter extends BasePresenter<ContractMVP.View> implements ContractMVP.Presenter {

    private static final String BITBUCKET_URL = "https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description";
    private static final String GITHUB_URL = "https://api.github.com/repositories";

    private Repos repos = new Repos();
    private RequestQueue queue;
    private final Context context;
    private JSONObject bitbucketJSON;
    private JSONObject githubJSON;

    public RepoPresenter(RequestQueue queue, Context context) {
        this.context = context;
        this.queue = queue;
    }

    @Override
    public void getRepos() {

        bitbucketNetworking();
        githubNetworking();
    }

    public void injectRepos() {
        try {
            for (int i = 0; i < bitbucketJSON.getJSONArray("values").length(); i++) {
                SingleRepo singleRepo = new SingleRepo();
                singleRepo.setOwnerName(bitbucketJSON.getJSONArray("values").getJSONObject(i).getJSONObject("owner").getString("username"));
                singleRepo.setRepoName(bitbucketJSON.getJSONArray("values").getJSONObject(i).getString("name"));
                singleRepo.setRepoDescription(bitbucketJSON.getJSONArray("values").getJSONObject(i).getString("description"));
                singleRepo.setAvatarUrl("https://1820102740.rsc.cdn77.org/png/bitbucket-6185.png");
                repos.addToRepos(singleRepo);
            }
            for (int i = 0; i < githubJSON.length(); i++) {
                SingleRepo singleRepo = new SingleRepo();
                singleRepo.setOwnerName(githubJSON.getJSONObject("owner").getString("login"));
                singleRepo.setRepoName(githubJSON.getString("name"));
                singleRepo.setRepoDescription(githubJSON.getString("description"));
                singleRepo.setAvatarUrl(githubJSON.getJSONObject("owner").getString("avatar_url"));
                repos.addToRepos(singleRepo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("App", "injs " + repos.getRepoList().size());
        view.showRepos(repos.getRepoList());
    }


    private void bitbucketNetworking() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, BITBUCKET_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bitbucketJSON = new JSONObject(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void githubNetworking() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, GITHUB_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("App", response.toString());
                            githubJSON = new JSONObject(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        queue.add(jsonObjectRequest);
    }
}
