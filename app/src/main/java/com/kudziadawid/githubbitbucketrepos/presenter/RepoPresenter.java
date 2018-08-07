package com.kudziadawid.githubbitbucketrepos.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kudziadawid.githubbitbucketrepos.comparator.SingleRepoComparator;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repos;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoPresenter extends BasePresenter<ContractMVP.View> implements ContractMVP.Presenter {

    private static final String BITBUCKET_URL = "https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description";
    private static final String GITHUB_URL = "https://api.github.com/repositories";

    private Repos repos = new Repos();
    private RequestQueue queue;
    private final Context context;
    private JSONObject bitbucketJSON;
    private JSONArray githubArray = new JSONArray();
    private List<SingleRepo> unsortedRepoList;
    private List<SingleRepo> sortedRepoList;
    private boolean sortedListDisplayed = false;

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
                singleRepo.setAvatarUrl(bitbucketJSON.getJSONArray("values").getJSONObject(i).getJSONObject("owner").getJSONObject("links")
                        .getJSONObject("avatar").getString("href"));
                singleRepo.setBitbucket(true);
                repos.addToRepos(singleRepo);
            }

            for (int i = 0; i < githubArray.length(); i++) {
                SingleRepo singleRepo = new SingleRepo();
                singleRepo.setOwnerName(githubArray.getJSONObject(i).getJSONObject("owner").getString("login"));
                singleRepo.setRepoName(githubArray.getJSONObject(i).getString("name"));
                singleRepo.setRepoDescription(githubArray.getJSONObject(i).getString("description"));
                singleRepo.setAvatarUrl(githubArray.getJSONObject(i).getJSONObject("owner").getString("avatar_url"));
                repos.addToRepos(singleRepo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        unsortedRepoList = new ArrayList<>(repos.getRepoList());
        sortRepoList();
        view.showRepos(unsortedRepoList);
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, GITHUB_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        githubArray = response;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("RepoApp", error.getMessage());
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void sortRepoList() {
        sortedRepoList = new ArrayList<>(unsortedRepoList);
        SingleRepoComparator singleRepoComparator = new SingleRepoComparator();
        Collections.sort(sortedRepoList, singleRepoComparator);
    }

    public void sortunsort() {
        if (sortedListDisplayed) {
            view.showRepos(unsortedRepoList);
            sortedListDisplayed = false;
        } else {
            view.showRepos(sortedRepoList);
            sortedListDisplayed = true;
        }
    }
}
