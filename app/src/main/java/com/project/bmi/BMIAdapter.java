package com.project.bmi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.Calendar;


public class BMIAdapter extends RecyclerView.Adapter<BMIAdapter.holder> {
    public static Long[] time;
    public static Double[] BMI;
    public static String[] status;

    class holder extends RecyclerView.ViewHolder {
        public TextView c;
        public TextView b;
        public TextView s;

        public holder(View view) {
            super(view);
            c=view.findViewById(R.id.textView);
            b=view.findViewById(R.id.textView2);
            s=view.findViewById(R.id.textView3);

        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        Log.d("DEBUG","holder get");
        return new holder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(holder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.c.setText(timeFormat(position));
        viewHolder.b.setText(BMI[position].toString());
        viewHolder.s.setText(status[position]);
        Log.d("DEBUG","holder");
    }

    private String timeFormat(int pos){
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(time[pos]);

        return c.get(Calendar.YEAR)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 0;
    }
}
