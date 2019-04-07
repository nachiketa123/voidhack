package com.example.androidcontinuity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TextEditorActivity extends AppCompatActivity {

    private EditText et_textArea;
    static ThreadForMainText.HandlerForMainText mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
        et_textArea = findViewById(R.id.et_textArea);
        et_textArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String text = s.toString();
                // url where the data will be posted
                // Log.d("mytag", text);
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://192.168.137.121/dashboard/webapp/index.php/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                Log.d("mytag","OnResponse");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                                Log.d("mytag",""+error.getMessage());
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("text",text);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if(mHandler == null)
            mHandler = new ThreadForMainText.HandlerForMainText(this);
        else
            mHandler.setmTarget(this);

        //thread for TextEditor text
        Thread thread = new Thread(new ThreadForMainText(getApplicationContext(),et_textArea,mHandler));
        thread.start();

    }

    public void setEt_textArea(String text) {
        et_textArea.setText(text);
        et_textArea.setSelection(et_textArea.getText().length());
    }
}
