package com.blogspot.duet.admissionmanagementsystem.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.duet.admissionmanagementsystem.CalcData;
import com.blogspot.duet.admissionmanagementsystem.R;

import java.util.ArrayList;

public class UserQusListAdapter extends RecyclerView.Adapter<UserQusListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CalcData> arrayList;

    public UserQusListAdapter(Context context, ArrayList<CalcData> arrayList) {
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


    }


    void setData(ArrayList<CalcData> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    void addItem(CalcData calcData){
        arrayList.add(calcData);
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
