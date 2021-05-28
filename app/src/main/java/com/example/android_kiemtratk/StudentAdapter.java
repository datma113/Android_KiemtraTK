package com.example.android_kiemtratk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    List<Student> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    String url = "https://60ae49ae80a61f0017332eed.mockapi.io/students";

    public StudentAdapter(List<Student> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new StudentViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        Student student = list.get(position);

        holder.tvName.setText(student.getName());
        holder.tvDepartment.setText(student.getDepartment());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.PUT,
                        url + "/" + student.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                                list.clear();
                                getData();

                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    protected Map<String, String> getParams() {
                        HashMap map = new HashMap();
                        map.put("name","Đạt NguyễnUp");
                        map.put("department","KTPM14");
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        StudentAdapter studentAdapter;
        TextView tvName, tvDepartment;
        ImageButton imageButton;

        public StudentViewHolder(@NonNull View itemView, StudentAdapter studentAdapter) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tv_name);
            this.tvDepartment = itemView.findViewById(R.id.tvDepartment);
            this.imageButton = itemView.findViewById(R.id.imgbUpdate);
            this.studentAdapter = studentAdapter;
        }
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
                                notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
}
