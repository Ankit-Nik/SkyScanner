package com.sky.scanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.sky.scanner.R;
import com.sky.scanner.adapter.SearhTinAdapter;
import com.sky.scanner.model.SearchTinResponseModel;
import com.sky.scanner.ui.searchdetails.SearchDetailActivity;
import com.sky.scanner.utils.LogManager;

import java.util.List;

public class SearchListActivity extends AppCompatActivity {
    private List<SearchTinResponseModel> data;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_searchlist);

        try {
            data = getIntent().getParcelableArrayListExtra("key");
        }
        catch(Exception e){
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(new SearhTinAdapter(data, R.layout.tin_no_list_row, getApplicationContext()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                try{
                    String tin = String.valueOf(data.get(position).getTIN());
                    Intent i = new Intent(SearchListActivity.this, FormDetails.class);
                    i.putExtra("id",tin );
                    startActivity(i);
                }
                catch (Exception e){
                    LogManager.printStackTrace(e);
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

}
