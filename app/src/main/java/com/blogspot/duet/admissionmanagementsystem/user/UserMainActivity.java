package com.blogspot.duet.admissionmanagementsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.duet.admissionmanagementsystem.CalcData;
import com.blogspot.duet.admissionmanagementsystem.LoginActivity;
import com.blogspot.duet.admissionmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {

    private String firebaseField = "Calculations";
    private ArrayList<CalcData> arrayList=new ArrayList<>();
    private UserQusListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView=findViewById(R.id.userRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter=new UserQusListAdapter(this,arrayList);
        loadData();


        findViewById(R.id.userFab).setOnClickListener(view->addCalculation());

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

      void addCalculation(){
        View view= LayoutInflater.from(this).inflate(R.layout.single_add_user_calc,null);

        EditText gpaEt=view.findViewById(R.id.addCalGpaEt);
        Spinner borderTypeEt=view.findViewById(R.id.addCalBorderTypeSp);
        Spinner groupTypeEt=view.findViewById(R.id.addCalGroupTypeSp);



        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String gpa=gpaEt.getText().toString();
                        String borderType=borderTypeEt.getSelectedItem().toString();
                        String groupType=groupTypeEt.getSelectedItem().toString();


                        if(gpa.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"Please enter first!",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        CalcData InfoData=new CalcData(null,borderType,groupType,Double.parseDouble(gpa));

                        FirebaseFirestore.getInstance().collection(firebaseField)
                                .add(InfoData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference docRef) {
                                Toast.makeText(getApplicationContext(), "Calculation added!", Toast.LENGTH_SHORT).show();
                                adapter.addItem(InfoData);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Calculation not added!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).setNegativeButton("Cancel",null)
                .create().show();

    }

}