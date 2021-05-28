package com.example.android_kiemtratk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    List<Student> list = new ArrayList<>();

    String url = "https://60ae49ae80a61f0017332eed.mockapi.io/students";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);
        recyclerView = findViewById(R.id.rcvStudent);


        getData();



        studentAdapter = new StudentAdapter(list, this);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        EditText edtName = findViewById(R.id.manage_edtName);
        Button btnAdd = findViewById(R.id.manage_btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                if(name.length()>0) {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(ManageStudentActivity.this, "Create Successfully", Toast.LENGTH_SHORT).show();
                                    getData();
                                    edtName.setText("");
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ManageStudentActivity.this, "Create failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        protected Map<String, String> getParams() {
                            HashMap map = new HashMap();
                            map.put("name",name);
                            map.put("department","KTPM14");
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ManageStudentActivity.this);
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(ManageStudentActivity.this, "length > 0", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    public void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Student student = new Student(
                                        Integer.parseInt(jsonObject.getString("id")),
                                        jsonObject.getString("name").toString(),
                                        jsonObject.getString("department").toString()
                                        );
                                list.add(student);
                                studentAdapter = new StudentAdapter(list, ManageStudentActivity.this);
                                recyclerView.setAdapter(studentAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}