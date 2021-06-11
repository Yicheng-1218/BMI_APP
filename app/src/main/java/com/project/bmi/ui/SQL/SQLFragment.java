package com.project.bmi.ui.SQL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.bmi.BMIAdapter;
import com.project.bmi.R;
import com.project.bmi.SqlDataBaseHelper;
import com.project.bmi.databinding.FragmentSqlBinding;

public class SQLFragment extends Fragment {

    private FragmentSqlBinding binding;
    private static final String DataBaseName = "BMI";
    private static final int DataBaseVersion = 4;
    private static String DataBaseTable = "mybmi";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    private Long[] time;
    private Double[] BMI;
    private String[] status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSqlBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initView();
        Log.d("DEBUG",time[0].toString());
        return root;
    }


    private void initView(){
        sqlDataBaseHelper= new SqlDataBaseHelper(this.getContext(),DataBaseName,null,DataBaseVersion);
        db=sqlDataBaseHelper.getWritableDatabase();

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDataBaseHelper.clearData(db);
            }
        });
        setRecycler();

    }

    private void getSQLData(){
        try {
            Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);

            time = new Long[c.getCount()];
            BMI = new Double[c.getCount()];
            status = new String[c.getCount()];
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                time[i] = c.getLong(0);
                BMI[i] = c.getDouble(1);
                status[i] = c.getString(2);
                c.moveToNext();
            }

        }catch (Exception e){
            Log.d("DEBUG",e.getMessage());
        }
    }

    private void setRecycler(){
        getSQLData();
        RecyclerView rec_view=binding.BmiRecycle;
        RecyclerView.Adapter adapter=new BMIAdapter();
        rec_view.setAdapter(adapter);
        BMIAdapter.time=this.time;
        BMIAdapter.BMI=this.BMI;
        BMIAdapter.status=this.status;
        rec_view.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}