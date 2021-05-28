package com.example.android_kiemtratk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    List<Student> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

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
}
