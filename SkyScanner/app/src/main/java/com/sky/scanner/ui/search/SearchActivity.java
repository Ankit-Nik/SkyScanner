package com.sky.scanner.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sky.scanner.R;
import com.sky.scanner.model.FilterModel;
import com.sky.scanner.model.SearchTinRequeest;
import com.sky.scanner.model.SearchTinResponse;
import com.sky.scanner.model.SearchTinResponseModel;
import com.sky.scanner.model.SearchVillageResponseDto;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.ui.SearchListActivity;
import com.sky.scanner.utils.ConnectionUtils;
import com.sky.scanner.utils.GlobalData;
import com.sky.scanner.utils.LogManager;
import com.sky.scanner.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ObserverCallBack.ServiceCallback ,AdapterView.OnItemSelectedListener {

    private static final String EXTRA_DATA_DASHBOARD = "search_data";
    private ArrayList<String> villages_date;
    private ArrayList<String> villages_id;
    private ArrayAdapter aa;
    private Button btn_search;
    private Spinner spin;
    private String village_id ="", tin_number="";
    private EditText enter_tin_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_search);
        init();
        serverTransactionForVillages();
    }


    private void serverTransactionForVillages() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(1);
                WebServiceCallHelper.village_list(myObserver);
            } else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }


    public static void switchToSearchScreen(Context context, String data) {
        Intent intent = new Intent(context, SearchActivity.class);
        if (!data.equals("")) {
            intent.putExtra(EXTRA_DATA_DASHBOARD, data);
        }
        context.startActivity(intent);
    }

    private void init() {
        villages_date = new ArrayList<String>();
        villages_id = new ArrayList<String>();
        //villages_date.add("Select Village");

        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,villages_date);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin = (Spinner) findViewById(R.id.spinner_village_data);
        btn_search   = (Button) findViewById(R.id.btn_search);
        enter_tin_no = (EditText) findViewById(R.id.enter_tin_no);

        spin.setOnItemSelectedListener(this);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    tin_number = enter_tin_no.getText().toString().trim();
                    if(tin_number.length()>0) {
                        searchTransaction();
                    }
                    else if(village_id.length()>0 && villages_id!=null && villages_id.size()>0) {
                        searchTransaction();
                    }
                    else{
                        ToastUtils.shortToast(SearchActivity.this, getString(R.string.enter_tin));
                    }
                }
                catch (Exception e){
                    LogManager.printStackTrace(e);
                }
            }
        });
    }

    private void searchTransaction() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(2);

                WebServiceCallHelper.search_data(myObserver, createRequestModel());
            }
            else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }

    }

    private SearchTinRequeest createRequestModel() {
        SearchTinRequeest requestDto = new SearchTinRequeest();
        requestDto.setStart("0");
        requestDto.setLength("10");

        ArrayList<FilterModel> fm_ara = new ArrayList<FilterModel>();
        FilterModel fm1 = new FilterModel();
        fm1.setName("VillageId");
        fm1.setValue(village_id);
        fm_ara.add(fm1);

        FilterModel fm2 = new FilterModel();
        fm2.setName("TIN");
        fm2.setValue(tin_number);
        fm_ara.add(fm2);

        requestDto.setFilters(fm_ara);
        return requestDto;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            village_id = villages_id.get(position);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onSuccess(Object response, int tag) {
        try {

            System.out.println("response - "+response);
            if (tag == 1) {
                SearchVillageResponseDto responseModel = (SearchVillageResponseDto) response;


                if (responseModel.getResults() != null && responseModel.getResults().size()>0) {
                    for(int i=0 ; i < responseModel.getResults().size() ; i++) {
                        villages_date.add(responseModel.getResults().get(i).getText());
                        villages_id.add(responseModel.getResults().get(i).getValue());
                    }
                    spin.setAdapter(aa);
                }
            }
            if (tag == 2) {
                SearchTinResponse responseModel = (SearchTinResponse) response;

                List<SearchTinResponseModel> data = responseModel.getResults();

                if (data != null && data.size()>0) {
                    Intent i = new Intent(SearchActivity.this, SearchListActivity.class);
                    i.putParcelableArrayListExtra("key", (ArrayList<? extends Parcelable>) data);
                    startActivity(i);
                    // finish();
                }
                else{
                    ToastUtils.shortToast(this, getString(R.string.server_no_data));

                }
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }

    @Override
    public void onError(String msg, Throwable error) {
        ToastUtils.shortToast(this, getString(R.string.server_error_occured));
    }


}