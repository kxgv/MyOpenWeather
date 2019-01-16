package com.example.myopenweather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "tempAdapter";

    private List<Temp> mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView data;
        private final TextView tempe;
        private final ImageView imatge;


        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            data = (TextView) v.findViewById(R.id.firstLine);
            tempe = (TextView) v.findViewById(R.id.secondLine);
            imatge = (ImageView) v.findViewById(R.id.icon);


        }

        public TextView getTextView() {
            // return textView;
            return null;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<Temp> dataSet) {
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_layout, viewGroup, false);

        return new ViewHolder(v);
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        //viewHolder.getTextView().setText(mDataSet[position]);
        final Temp name = mDataSet.get(position);
        holder.data.setText(name.getData());
        holder.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        if(name.getImatge().equals("cold")) {
            //           setImageResource(R.drawable.ic_cloud);
            //image asset - clip art
            holder.imatge.setImageResource(R.drawable.ic_action_cold);}
        else
            holder.imatge.setImageResource(R.drawable.ic_action_sun);

        holder.tempe.setText("Temp: " + name.getTempe() + "ªC, humidity:" + name.getHumidity() );

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataSet.size();
    }
}