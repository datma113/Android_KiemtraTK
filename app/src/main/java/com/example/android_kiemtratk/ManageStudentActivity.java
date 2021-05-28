package com.example.android_kiemtratk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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