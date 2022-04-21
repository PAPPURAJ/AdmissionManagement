package com.blogspot.duet.admissionmanagementsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.duet.admissionmanagementsystem.CalcData;
import com.blogspot.duet.admissionmanagementsystem.LoginActivity;
import com.blogspot.duet.admissionmanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    private String firebaseField = "Calculations";
    private ArrayList<CalcData> arrayList=new ArrayList<>();
    private AdminCostListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView=findViewById(R.id.adminRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter=new AdminCostListAdapter(this,arrayList);
        loadData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Signed out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);

    }


    void loadData() {
        FirebaseFirestore.getInstance().collection(firebaseField)
                .get().addOnSuccessListener(qns -> {
            arrayList.clear();

            for (int i = 0; i < qns.size(); i++) {
                String borderType = qns.getDocuments().get(i).getString("borderType");
                String groupType = qns.getDocuments().get(i).getString("groupType");
                double gpa=qns.getDocuments().get(i).getDouble("gpa");

                CalcData myUserData = new CalcData(
                        qns.getDocuments().get(i).getReference(),
                        borderType,
                        groupType,
                        gpa
                );

                arrayList.add(myUserData);
                adapter.setData(arrayList);
                recyclerView.setAdapter(adapter);

            }

        });
    }


}