package com.blogspot.duet.admissionmanagementsystem.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.duet.admissionmanagementsystem.CalcData;
import com.blogspot.duet.admissionmanagementsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class AdminCostListAdapter extends RecyclerView.Adapter<AdminCostListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CalcData> arrayList;

    public AdminCostListAdapter(Context context, ArrayList<CalcData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.single_cal_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.borderTypeTv.setText("Border type: "+arrayList.get(position).getBorderType());
        holder.groupTypeTv.setText("Border type: "+arrayList.get(position).getGroupType());
        holder.gpaTv.setText("GPA: "+arrayList.get(position).getGpa());
        holder.amountTv.setText("Amount: "+arrayList.get(position).getCost());



        holder.itemView.setOnLongClickListener(view -> {

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItem(position);
                        }
                    })
                    .setNegativeButton("No",null).create().show();


            return true;
        });

    }

    private void deleteItem(int position) {
        arrayList.get(position).getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    void setData(ArrayList<CalcData> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView borderTypeTv,groupTypeTv,gpaTv,amountTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            borderTypeTv=itemView.findViewById(R.id.singleCallListBorderTypeEt);
            groupTypeTv=itemView.findViewById(R.id.singleCallListGroupTypeEt);
            gpaTv=itemView.findViewById(R.id.singleCallListGpaEt);
            amountTv=itemView.findViewById(R.id.singleTotalCostEt);

        }
    }
}
