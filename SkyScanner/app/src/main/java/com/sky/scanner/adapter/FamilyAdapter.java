package com.sky.scanner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.scanner.R;
import com.sky.scanner.model.AppSKYBenificiaryFamily;

import java.util.List;

public class FamilyAdapter  extends RecyclerView.Adapter<SearhTinAdapter.MovieViewHolder> {

    private List<AppSKYBenificiaryFamily> data;
    private int rowLayout;
    private Context context;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dataLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;


        public MovieViewHolder(View v) {
            super(v);
            dataLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
        }
    }

    public FamilyAdapter(List<AppSKYBenificiaryFamily> data, int rowLayout, Context context) {
        this.data = data;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SearhTinAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SearhTinAdapter.MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearhTinAdapter.MovieViewHolder holder, final int position) {
        holder.movieTitle.setText(data.get(position).getTIN());
        holder.data.setText(data.get(position).getName());
        holder.movieDescription.setText(data.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}