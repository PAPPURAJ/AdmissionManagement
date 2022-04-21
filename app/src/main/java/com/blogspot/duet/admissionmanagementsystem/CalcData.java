package com.blogspot.duet.admissionmanagementsystem;

import com.google.firebase.firestore.DocumentReference;

public class CalcData {

    private DocumentReference reference;
    private String borderType,groupType;
    private Double gpa,cost;

    public CalcData() {
    }

    public CalcData(DocumentReference reference, String borderType, String groupType, Double gpa) {
        this.reference = reference;
        this.borderType = borderType;
        this.groupType = groupType;
        this.gpa = gpa;
                if(borderType.equalsIgnoreCase("hostel")){
                    if(groupType.equalsIgnoreCase("science")){
                        if(gpa==5.00)
                            cost=4000.00;
                        else if(gpa>=4.00)
                            cost=5000.00;
                        else if(gpa>=3.00)
                            cost=6000.00;
                        else
                            cost=8000.00;
                    }else{
                        if(gpa==5.00)
                            cost=3500.00;
                        else if(gpa>=4.00)
                            cost=4000.00;
                        else if(gpa>=3.00)
                            cost=5000.00;
                        else
                            cost=7000.00;
                    }
                }else{
                    if(groupType.equalsIgnoreCase("science")){
                        if(gpa==5.00)
                            cost=1500.00;
                        else if(gpa>=4.00)
                            cost=2000.00;
                        else if(gpa>=3.00)
                            cost=2500.00;
                        else
                            cost=3000.00;
                    }else{
                        if(gpa==5.00)
                            cost=1000.00;
                        else if(gpa>=4.00)
                            cost=1200.00;
                        else if(gpa>=3.00)
                            cost=1500.00;
                        else
                            cost=2000.00;
                    }
                }
    }

    public DocumentReference getReference() {
        return reference;
    }

    public String getBorderType() {
        return borderType;
    }

    public String getGroupType() {
        return groupType;
    }

    public Double getGpa() {
        return gpa;
    }

    public Double getCost() {
        return cost;
    }
}
