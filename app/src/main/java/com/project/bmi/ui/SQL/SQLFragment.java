package com.project.bmi.ui.SQL;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.bmi.R;
import com.project.bmi.SqlDataBaseHelper;
import com.project.bmi.databinding.FragmentSqlBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

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
    private RecyclerView rec_view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSqlBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initView();
//        Log.d("DEBUG","time="+time[0].toString()+" Bmi="+BMI[0]+" status="+status[0]);
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
        int count=getSQLData();
        ArrayList<String> log=DataCombination(count);
        setListView(log);

    }

    private int getSQLData(){
        Cursor c=null;
        try {
            c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);

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
        }finally {
            assert c != null;
            return c.getCount();
        }
    }

    private void setListView(ArrayList<String> data){
        ListAdapter adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,data);
        binding.listView.setAdapter(adapter);
    }

    private ArrayList<String> DataCombination(int count){
        ArrayList<String> result=new ArrayList<String>();
        for(int i=0;i<count;i++){
            result.add("日期:"+timeFormat(i)+"   BMI:"+String.format("%,.2f", BMI[i])+"   狀態:"+status[i]);
        }
        return result;
    }

    private String timeFormat(int pos){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(time[pos]);

        return c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}