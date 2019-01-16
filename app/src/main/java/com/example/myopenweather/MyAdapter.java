package com.example.myopenweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> values;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View v){
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, String item){
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        values.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapter (List <String> myDataset){ values = myDataset;}



    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final String name = values.get(position);
        viewHolder.txtHeader.setText(name);
        viewHolder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        viewHolder.txtFooter.setText("Footer: " + name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}