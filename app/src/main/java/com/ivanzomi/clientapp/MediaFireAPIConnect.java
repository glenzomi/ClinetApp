package com.ivanzomi.clientapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import es.dmoral.toasty.Toasty;

public class MediaFireAPIConnect {
    static String filelink;
    public static String getFileLink(String Videolink, final Context context) throws UnsupportedEncodingException {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://media-fire-api.herokuapp.com/?url="+URLEncoder.encode(Videolink,"utf-8");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject object = null;
                        try {
                            object = response.getJSONObject(0);

                             filelink = object.getString("file");

                        }
                        catch (Exception ex) {
                            Toasty.error(context,"JSON Exceptiion",Toasty.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(request);
        RetryPolicy retryPolicy = new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
        request.setRetryPolicy(retryPolicy);

        return filelink;
    }

}
