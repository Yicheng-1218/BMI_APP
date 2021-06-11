package com.project.bmi.ui.dashboard;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.project.bmi.SqlDataBaseHelper;
import com.project.bmi.databinding.FragmentDashboardBinding;

import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private double BMI;
    private double height;
    private double weight;
    private static final String DataBaseName = "BMI";
    private static final int DataBaseVersion = 4;
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initView();
        return root;
    }
    private void initView(){
        sqlDataBaseHelper= new SqlDataBaseHelper(this.getContext(),DataBaseName,null,DataBaseVersion);
        db=sqlDataBaseHelper.getWritableDatabase();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    height=Double.parseDouble(binding.editTextHeight.getEditText().getText().toString());
                    if(height>2){
                        height/=100;
                    }
                    weight=Double.parseDouble(binding.editTextWeight.getEditText().getText().toString());
                    BMI=weight/(height*height);
                    Snackbar.make(view,"您的BMI: "+String.format("%,.2f", BMI),Snackbar.LENGTH_LONG).show();
                    addSqlData();


                }catch (Exception e){
                    Log.d("DEBUG",e.getMessage());
                    if (e.getMessage()=="empty String"){
                        Snackbar.make(view,"輸入框不可空白",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void addSqlData(){
        Long now=System.currentTimeMillis();
        db.execSQL("Insert into mybmi(time,BMI,status) values("+now+","+BMI+","+"'"+getStatus()+"'"+")");
        Log.d("DEBUG","insert success");
    }

    private String getStatus(){
        String status=null;
        if (BMI<18.5){
            status="體重過輕";
        }else if(BMI>=18.5&&BMI<24){
            status="體重正常";
        }else if (BMI>=24&&BMI<27) {
            status = "過重";
        }else if(BMI>=27&&BMI<30){
            status="輕度肥胖";
        }else if(BMI>=30&&BMI<35){
            status="中度肥胖";
        }else if(BMI>=35){
            status="重度肥胖";
        }
        return status;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}