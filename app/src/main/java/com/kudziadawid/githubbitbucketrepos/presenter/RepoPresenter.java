package com.kudziadawid.githubbitbucketrepos.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repos;
import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cz.msebera.android.httpclient.Header;

public class RepoPresenter extends BasePresenter<ContractMVP.View> implements ContractMVP.Presenter {

    private static final String BITBUCKET_URL = "https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description";

    private Repos repos;
    private RequestQueue queue;
    private final Context context;
    private JsonObjectRequest jsObjRequest;
    private JSONArray bitbucketArray;

    public RepoPresenter(Repos repos, Context context) {
        this.repos = repos;
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    @Override
    public void getRepos() {

        bitbucketNetworking();
//        new GetAsync().execute();
//        try {
//            SingleRepo singleRepo = new SingleRepo();
//            singleRepo.setOwnerName(bitbucketArray.getJSONObject(0).getJSONObject("owner").getString("username"));
//            repos.addToRepos(singleRepo);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        view.showRepos();
    }

//    private JSONArray networking() {
//
//        final JSONArray[] jsonArray = new JSONArray[1];
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        client.get("https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description",
//                null,
//                new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                        super.onSuccess(statusCode, headers, response);
//                        jsonArray[0] = response;
//                        Log.d("App", jsonArray[0].toString());
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray response) {
//                        //Timber log
//                    }
//                });
//        return jsonArray[0];
//    }
//
//    public void bitbucketNetworking() {
//        jsObjRequest = new JsonObjectRequest(Request.Method.GET, BITBUCKET_URL, null,
//                new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    bitbucketArray = response.getJSONArray("values");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                new AlertDialog.Builder(context)
//                        .setTitle("Loading")
//                        .setMessage("Click OK to load")
//                        .setPositiveButton(android.R.string.ok, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        queue.add(jsObjRequest);
//    }

//    public void bitbucketNetworking() {
//        RequestFuture<JSONObject> future = RequestFuture.newFuture();
//        JsonObjectRequest request = new JsonObjectRequest("https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description", new JSONObject(), future, future);
//        queue.add(request);
//
//        try {
//            JSONObject response = future.get(); // this will block
//            bitbucketArray = response.getJSONArray("values");
//        } catch (InterruptedException e) {
//            // exception handling
//        } catch (ExecutionException e) {
//            // exception handling
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    public void bitbucketNetworking() {
//        final RequestQueue queue = Volley.newRequestQueue(context);
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Object[] responseHolder = new Object[1];
//
//        final StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                responseHolder[0] = response;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                responseHolder[0] = error;
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        queue.add(stringRequest);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (responseHolder[0] instanceof VolleyError) {
//            final VolleyError volleyError = (VolleyError) responseHolder[0];
//            //TODO: Handle error...
//        } else {
//            final String response = (String) responseHolder[0];
//            try {
//                bitbucketArray = new JSONArray(response);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void bitbucketNetworking() {

        RequestFuture<JSONObject> requestFuture=RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                BITBUCKET_URL,new JSONObject(),requestFuture,requestFuture);
        queue.add(request);

        try {
            JSONObject object = requestFuture.get(10,TimeUnit.SECONDS);
            Log.d("App", "object: " + object.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        Log.d("App", "endof bn");
    }
}
