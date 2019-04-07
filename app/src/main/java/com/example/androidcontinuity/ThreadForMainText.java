package com.example.androidcontinuity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ThreadForMainText implements Runnable
{
    public EditText editText;
    private Context context;
    HandlerForMainText mHandler;
    public ThreadForMainText(Context context, EditText editText,HandlerForMainText mHandler)
    {
        this.context = context;
        this.editText = editText;
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.d("mytag", e.getMessage());
            }
//            Log.d("mytag", "background thread started");
            final String url = "http://192.168.137.121/dashboard/webapp/getText.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                           Log.d("mytag", "OnResponse in Service " + response);
//                            editText.setText(response);
//                            editText.setSelection(editText.getText().length());
                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("text",response);
                            msg.setData(bundle);
                            mHandler.sendMessage(msg);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                            Log.d("mytag", "" + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("text", "");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }
    static class HandlerForMainText extends Handler
    {

        private WeakReference<TextEditorActivity> mTarget;
        HandlerForMainText(TextEditorActivity target)
        {
            mTarget = new WeakReference<TextEditorActivity>(target);
        }
        public void setmTarget(TextEditorActivity target)
        {
            mTarget.clear();
            mTarget = new WeakReference<TextEditorActivity>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TextEditorActivity activity = mTarget.get();
            activity.setEt_textArea(msg.getData().getString("text"));
        }
    }
}

