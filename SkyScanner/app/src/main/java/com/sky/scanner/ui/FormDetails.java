package com.sky.scanner.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sky.scanner.BuildConfig;
import com.sky.scanner.ModelsNew.FamilyDet;
import com.sky.scanner.ModelsNew.FamilyDetNewModel;
import com.sky.scanner.ModelsNew.FamilyStatus;
import com.sky.scanner.ModelsNew.LDocNewModel;
import com.sky.scanner.ModelsNew.LGender;
import com.sky.scanner.ModelsNew.LRelation;
import com.sky.scanner.ModelsNew.SubmitDataNewModel;
import com.sky.scanner.ModelsNew.UserDetailsModel;
import com.sky.scanner.R;
import com.sky.scanner.model.FamilyDetModel;
import com.sky.scanner.model.FamilyDetModel_SkybeneficiaryFDocObj;
import com.sky.scanner.model.FamilyMembersDetailModel;
import com.sky.scanner.model.ScanInfoRequestDto;
import com.sky.scanner.model.SearchDetailRequest;
import com.sky.scanner.model.SearchDetailResponseModel;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.searchdetails.SearchDetailActivity;
import com.sky.scanner.utils.AppConstants;
import com.sky.scanner.utils.ConnectionUtils;
import com.sky.scanner.utils.GlobalData;
import com.sky.scanner.utils.LogManager;
import com.sky.scanner.utils.ToastUtils;
import com.sky.scanner.xml_parser.XMLDOMParser;
import com.sky.scanner.xml_parser.XMLParsingConstant;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class FormDetails extends Activity implements ObserverCallBack.ServiceCallback,View.OnClickListener{

    TextView tin_no_house_hold,member_txt_tin,txt_village,txt_GP,txt_block,txt_district;
    EditText txt_smartcard_u,txt_pds_u,txt_manrega_u1,txt_manrega_u2,txt_aadhar_no,txt_aadhar_name,txt_name,txt_dob,txt_age;
    Button btn_next,add_new_member,scan_aadhar;
    Spinner gender_spinner,status_spinner,relation_spinner;
    ScanInfoRequestDto adharCardInfoDto;
    String id = "";
    private int position = 0;
    private int family_member_count = 0;
    private int total_family_member_count = 0;
    UserDetailsModel tin_detail_data;
    SubmitDataNewModel submit_request_obj;
    ArrayList<LGender>  family_gender;
    ArrayList<FamilyStatus> family_status;
    ArrayList<LRelation> family_relation;

    String family_gender_id,family_status_id,family_relation_id;
    private String yob="0";
    ArrayList<FamilyDetNewModel> familyDet;
    private static final String TAG = SearchDetailActivity.class.getSimpleName();
    // location last updated time
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private double global_lattitude=0.0;
    private double global_longitude=0.0;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    int bday_year=0;

    String AadhaarXML="";
    List<String> spinnerArray_relation_male,spinnerArray_relation_female,spinnerArray_relation_other;
    ArrayAdapter<String> adapter_relation;
    boolean add_new_member_active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.form_details);
        init();

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                bday_year = year;
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                try {
                    updateLabel();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        try{
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("id")!=null) {
                id = bundle.getString("id");
                searchTransaction();
            }
        }
        catch (Exception e){
            LogManager.printStackTrace(e);
        }

        // searchTransaction();
    }

    private void searchTransaction() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(1);
                WebServiceCallHelper.search_tin_detail_data_new(myObserver, createRequestModel_NEW());
            } else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }

    private SearchDetailRequest createRequestModel_NEW() {
        SearchDetailRequest requestDto = new SearchDetailRequest();
        requestDto.setId(id);
        // requestDto.setId("51220100101400000018500021001");
        return requestDto;
    }


    @Override
    public void onSuccess(Object response, int tag) {
        try {
            if (tag == 1) {

                tin_detail_data = (UserDetailsModel) response;
                System.out.println("Ankit Response tin_detail_data - " + tin_detail_data);

                if (tin_detail_data != null) {
                    // declared on top..........

                    ArrayList<FamilyDet>  FamilyDet_arr = new ArrayList<FamilyDet>();

                    for (int i = 0; i < tin_detail_data.getFamilyDet().size(); i++) {
                        FamilyDet FamilyDet_ob2 = new FamilyDet();
                        FamilyDet_ob2.setGenderId(tin_detail_data.getFamilyDet().get(i).getGenderId());
                        FamilyDet_ob2.setMasterBenificiaryId(tin_detail_data.getFamilyDet().get(i).getMasterBenificiaryId());
                        FamilyDet_ob2.setMemberTIN(tin_detail_data.getFamilyDet().get(i).getMemberTIN());
                        FamilyDet_ob2.setTIN(tin_detail_data.getFamilyDet().get(i).getTIN());
                        FamilyDet_ob2.setUniqueTin(tin_detail_data.getFamilyDet().get(i).getUniqueTin());
                        FamilyDet_ob2.setName(tin_detail_data.getFamilyDet().get(i).getName());
                        FamilyDet_ob2.setAadhaarName(tin_detail_data.getFamilyDet().get(i).getAadhaarName());
                        FamilyDet_ob2.setRelationId(tin_detail_data.getFamilyDet().get(i).getRelationId());
                        FamilyDet_ob2.setYOB(tin_detail_data.getFamilyDet().get(i).getYOB());
                        FamilyDet_ob2.setAadhaar(tin_detail_data.getFamilyDet().get(i).getAadhaar());
                        FamilyDet_ob2.setFamilyStatus(tin_detail_data.getFamilyDet().get(i).getFamilyStatus());
                        FamilyDet_ob2.setDOB(tin_detail_data.getFamilyDet().get(i).getDOB());
                        FamilyDet_ob2.setDD(tin_detail_data.getFamilyDet().get(i).getDD());
                        FamilyDet_ob2.setMM(tin_detail_data.getFamilyDet().get(i).getMM());
                        FamilyDet_ob2.setYYYY(tin_detail_data.getFamilyDet().get(i).getYYYY());
                        FamilyDet_ob2.setSmartCard(tin_detail_data.getFamilyDet().get(i).getSmartCard());
                        FamilyDet_ob2.setPDS(tin_detail_data.getFamilyDet().get(i).getPDS());
                        FamilyDet_ob2.setMANREGA(tin_detail_data.getFamilyDet().get(i).getMANREGA());
                        FamilyDet_ob2.setMANREGA1(tin_detail_data.getFamilyDet().get(i).getMANREGA1());

                        FamilyDet_arr.add(FamilyDet_ob2);
                    }
                    total_family_member_count = FamilyDet_arr.size();

                    System.out.println("total_family_member_count -"+total_family_member_count);

                   // ToastUtils.shortToast(getApplicationContext(), "total_family_member_count -"+total_family_member_count);


                    tin_detail_data.setUniqueTIN(tin_detail_data.getFamilyDet().get(0).getUniqueTin());

                    member_txt_tin.setText(tin_detail_data.getFamilyDet().get(0).getTIN());
                    txt_name.setText(tin_detail_data.getFamilyDet().get(0).getName());
                    txt_dob.setText(tin_detail_data.getFamilyDet().get(0).getDOB());
                    txt_age.setText(tin_detail_data.getFamilyDet().get(0).getYOB());

                    txt_smartcard_u.setText(tin_detail_data.getFamilyDet().get(0).getSmartCard());
                    txt_pds_u.setText(tin_detail_data.getFamilyDet().get(0).getPDS());
                    txt_manrega_u1.setText(tin_detail_data.getFamilyDet().get(0).getMANREGA());
                    txt_manrega_u2.setText(tin_detail_data.getFamilyDet().get(0).getMANREGA1());

                    txt_aadhar_no.setText(tin_detail_data.getFamilyDet().get(0).getAadhaar());
                    txt_aadhar_name.setText(tin_detail_data.getFamilyDet().get(0).getAadhaarName());

                    tin_no_house_hold.setText(tin_detail_data.getHH_ID());
                    txt_village.setText(tin_detail_data.getVName());
                    txt_GP.setText(tin_detail_data.getGPName());
                    txt_block.setText(tin_detail_data.getBName());
                    txt_district.setText(tin_detail_data.getDName());

                    family_gender = new ArrayList<LGender>();
                    List<String> spinnerArray_gender =  new ArrayList<String>();

                    for (int i = 0; i < tin_detail_data.getLgender().size(); i++) {
                        LGender ob = new LGender();
                        ob.setGender(tin_detail_data.getLgender().get(i).getGender());
                        ob.setGenderId(tin_detail_data.getLgender().get(i).getGenderId());
                        family_gender.add(ob);
                        spinnerArray_gender.add(tin_detail_data.getLgender().get(i).getGender());
                    }

                    ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray_gender);
                    adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gender_spinner.setAdapter(adapter_gender);



                    family_status = new ArrayList<FamilyStatus>();
                    List<String> spinnerArray__status =  new ArrayList<String>();

                    for (int i = 0; i < tin_detail_data.getLfamilystatus().size(); i++) {
                        FamilyStatus ob1 = new FamilyStatus();
                        ob1.setFamilyStatusid(tin_detail_data.getLfamilystatus().get(i).getFamilyStatusid());
                        ob1.setFamilyStatusText(tin_detail_data.getLfamilystatus().get(i).getFamilyStatusText());
                        family_status.add(ob1);
                        spinnerArray__status.add(tin_detail_data.getLfamilystatus().get(i).getFamilyStatusText());
                    }
                    ArrayAdapter<String> adapter_status = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray__status);
                    adapter_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    status_spinner.setAdapter(adapter_status);

                    //status_spinner

                    family_relation = new ArrayList<LRelation>();

                    spinnerArray_relation_male =  new ArrayList<String>();
                    spinnerArray_relation_female =  new ArrayList<String>();
                    spinnerArray_relation_other =  new ArrayList<String>();

                    for (int i = 0; i < tin_detail_data.getLrelation().size(); i++) {
                        LRelation ob2 = new LRelation();
                        ob2.setGenderId(tin_detail_data.getLrelation().get(i).getGenderId());
                        ob2.setRelationID(tin_detail_data.getLrelation().get(i).getRelationID());
                        ob2.setRelation(tin_detail_data.getLrelation().get(i).getRelation());
                        family_relation.add(ob2);

                        if(tin_detail_data.getLrelation().get(i).getRelationID().equals("1")){
                            spinnerArray_relation_male.add(tin_detail_data.getLrelation().get(i).getRelation());
                            spinnerArray_relation_female.add(tin_detail_data.getLrelation().get(i).getRelation());
                        }

                        if(tin_detail_data.getLrelation().get(i).getGenderId().equals("1")){
                            spinnerArray_relation_male.add(tin_detail_data.getLrelation().get(i).getRelation());
                        }
                        else if(tin_detail_data.getLrelation().get(i).getGenderId().equals("2")){
                            spinnerArray_relation_female.add(tin_detail_data.getLrelation().get(i).getRelation());
                        }
                        spinnerArray_relation_other.add(tin_detail_data.getLrelation().get(i).getRelation());
                    }
                } else {
                    ToastUtils.shortToast(this, getString(R.string.server_no_data));
                }
            }
        }
        catch(Exception e){
        }
    }

    @Override
    public void onError(String msg, Throwable error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_next:

                //   ToastUtils.shortToast(getApplicationContext(),""+position);
                try {
                    if (ConnectionUtils.isNetworkAvailable(this)) {
                        if (txt_name.getText().toString().trim().length() > 0) {

                            if (family_status_id.equals("1")) {

                                if (txt_dob.getText().toString().trim().length() > 0 && txt_age.getText().toString().trim().length() > 0 &&
                                        txt_age.getText().toString().trim().length() != 0) {
                                    if (txt_smartcard_u.getText().toString().trim().length() > 0) {
                                        if (txt_pds_u.getText().toString().trim().length() > 0) {
                                            if (txt_manrega_u1.getText().toString().trim().length() > 0 &&
                                                    txt_manrega_u2.getText().toString().trim().length() > 0) {
                                                if (txt_aadhar_no.getText().toString().trim().length() > 0 &&
                                                        txt_aadhar_name.getText().toString().trim().length() > 0) {

                                                    if (add_new_member.getVisibility() == View.VISIBLE) {

                                                        if(add_new_member_active)
                                                            add_new_member_SubmitInfo();

                                                        submit_request_obj.setFamilyDet(familyDet);

                                                        GlobalData.getInstance().setFamilyDetarray(submit_request_obj);

                                                        Intent in = new Intent(this, HousePicActivity.class);
                                                        in.putExtra("global_lattitude", "" + global_lattitude);
                                                        in.putExtra("global_longitude", "" + global_longitude);
                                                        startActivity(in);
                                                        finish();

                                                    } else {

                                                        createRequestModelForSubmitInfo(position);
                                                        position = position + 1;

                                                        if (total_family_member_count == 1) {

                                                            add_new_member.setVisibility(View.VISIBLE);

                                                        } else {
                                                            member_txt_tin.setText(tin_detail_data.getFamilyDet().get(position).getTIN());
                                                            txt_name.setText(tin_detail_data.getFamilyDet().get(position).getName());
                                                            //txt_age.setText(tin_detail_data.getFamilyDet().get(position).getDOB());
                                                            txt_dob.setText(tin_detail_data.getFamilyDet().get(position).getDOB());
                                                            txt_age.setText(tin_detail_data.getFamilyDet().get(position).getYOB());

                                                            txt_age.setText(tin_detail_data.getFamilyDet().get(position).getDOB());

                                                            txt_smartcard_u.setText(tin_detail_data.getFamilyDet().get(position).getSmartCard());
                                                            txt_pds_u.setText(tin_detail_data.getFamilyDet().get(position).getPDS());
                                                            txt_manrega_u1.setText(tin_detail_data.getFamilyDet().get(position).getMANREGA());
                                                            txt_manrega_u2.setText(tin_detail_data.getFamilyDet().get(position).getMANREGA1());

                                                            txt_aadhar_no.setText(tin_detail_data.getFamilyDet().get(position).getAadhaar());
                                                            txt_aadhar_name.setText(tin_detail_data.getFamilyDet().get(position).getAadhaarName());

                                                            total_family_member_count = total_family_member_count - 1;
                                                        }
                                                    }
                                                } else
                                                    ToastUtils.shortToast(this, getString(R.string.add_aadhar));
                                            } else
                                                ToastUtils.shortToast(this, getString(R.string.add_mgn));
                                        } else
                                            ToastUtils.shortToast(this, getString(R.string.add_pds));
                                    } else
                                        ToastUtils.shortToast(this, getString(R.string.add_smartcard));
                                } else
                                    ToastUtils.shortToast(this, getString(R.string.add_dob));

                            } else {

                                if (add_new_member.getVisibility() == View.VISIBLE) {

                                    if(add_new_member_active)
                                        add_new_member_SubmitInfo();

                                    submit_request_obj.setFamilyDet(familyDet);

                                    GlobalData.getInstance().setFamilyDetarray(submit_request_obj);

                                    Intent in = new Intent(this, HousePicActivity.class);
                                    in.putExtra("global_lattitude", "" + global_lattitude);
                                    in.putExtra("global_longitude", "" + global_longitude);
                                    startActivity(in);
                                    finish();

                                } else {

                                    createRequestModelForSubmitInfo(position);
                                    position = position + 1;


                                    if (total_family_member_count == 1) {

                                        add_new_member.setVisibility(View.VISIBLE);

                                    } else {
                                        member_txt_tin.setText(tin_detail_data.getFamilyDet().get(position).getTIN());
                                        txt_name.setText(tin_detail_data.getFamilyDet().get(position).getName());
                                        txt_dob.setText(tin_detail_data.getFamilyDet().get(position).getDOB());
                                        txt_age.setText(tin_detail_data.getFamilyDet().get(position).getYOB());

                                        txt_age.setText(tin_detail_data.getFamilyDet().get(position).getDOB());

                                        txt_smartcard_u.setText(tin_detail_data.getFamilyDet().get(position).getSmartCard());
                                        txt_pds_u.setText(tin_detail_data.getFamilyDet().get(position).getPDS());
                                        txt_manrega_u1.setText(tin_detail_data.getFamilyDet().get(position).getMANREGA());
                                        txt_manrega_u2.setText(tin_detail_data.getFamilyDet().get(position).getMANREGA1());

                                        txt_aadhar_no.setText(tin_detail_data.getFamilyDet().get(position).getAadhaar());
                                        txt_aadhar_name.setText(tin_detail_data.getFamilyDet().get(position).getAadhaarName());

                                        total_family_member_count = total_family_member_count - 1;
                                    }
                                }
                            }
                        }
                        else
                            ToastUtils.shortToast(this, getString(R.string.enter_name));
                    }
                    else
                        ToastUtils.shortToast(this, getString(R.string.server_error));
                }
                catch(Exception ex){
                    LogManager.printStackTrace(ex);
                }
                break;


            case R.id.scan_aadhar:
                checkPermission();
                break;

            case R.id.add_new_member:
                add_new_member_active = true;
                clearForm();
                break;

            case R.id.txt_dob:
                //  hideKeyboard(FormDetails.this);

                DatePickerDialog dd =   new DatePickerDialog(FormDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dd.show();

                break;

        }
    }

    private void updateLabel() throws ParseException {
        String myFormat = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txt_dob.setText(sdf.format(myCalendar.getTime()));
        //  txt_dob.setFocusable(false);
        //   txt_dob.setCursorVisible(false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String newDateStr = dateFormat.format(date);

        int age =  Integer.parseInt(newDateStr) - bday_year;

        if(age < 0 || age==0){
            ToastUtils.shortToast(getApplicationContext(),"Please select valid date");
            txt_dob.setText("");
            txt_age.setText("");
        }
        else{
            txt_age.setText(""+age);
        }
    }


    private void clearForm() {
        txt_smartcard_u.setText("");
        txt_pds_u.setText("");
        txt_manrega_u1.setText("");
        txt_manrega_u2.setText("");
        txt_aadhar_no.setText("");
        txt_aadhar_name.setText("");
        txt_name.setText("");
        txt_dob.setText("");
        txt_age.setText("");
        member_txt_tin.setText("");
    }


    private void createRequestModelForSubmitInfo(int pos) {

        if(pos==0) {
            submit_request_obj = new SubmitDataNewModel();
            familyDet = new ArrayList<>();

            submit_request_obj.setTIN(tin_detail_data.getTIN());
            submit_request_obj.setMasterBenificiaryId(tin_detail_data.getMasterBenificiaryId());
            submit_request_obj.setUniqueTin(tin_detail_data.getUniqueTIN());
            submit_request_obj.setHH_ID(tin_detail_data.getHH_ID());
            submit_request_obj.setCreatedBy(AppPrefs.getInstance(getApplicationContext()).getUserId(getApplicationContext()));
            submit_request_obj.setErrorMessage("");
            submit_request_obj.setVName(tin_detail_data.getVName());
            submit_request_obj.setGPName(tin_detail_data.getGPName());
            submit_request_obj.setBName(tin_detail_data.getBName());
            submit_request_obj.setDName(tin_detail_data.getDName());
        }

        FamilyDetNewModel requestDto = new FamilyDetNewModel();

        requestDto.setMemberTIN(tin_detail_data.getFamilyDet().get(pos).getMemberTIN());
        requestDto.setTIN(tin_detail_data.getFamilyDet().get(pos).getMemberTIN());
        requestDto.setRefKey("");
        requestDto.setUniqueTin(tin_detail_data.getFamilyDet().get(pos).getUniqueTin());
        requestDto.setName(txt_name.getText().toString().trim());
        requestDto.setGenderId(Integer.parseInt(family_gender_id));
        requestDto.setRelationId(family_relation_id);
        requestDto.setFamilyStatus(Integer.parseInt(family_status_id));
        if(txt_dob.getText().toString().length()==0) {
            requestDto.setDOB(null);
        }
        else{
            requestDto.setDOB(txt_dob.getText().toString());
        }
        requestDto.setDD(yob);
        requestDto.setMM(yob);
        requestDto.setYYYY(yob);
        requestDto.setSmartCard(txt_smartcard_u.getText().toString().trim());
        requestDto.setPDS(txt_pds_u.getText().toString().trim());
        requestDto.setMANREGA(txt_manrega_u1.getText().toString().trim());
        requestDto.setMANREGA1(txt_manrega_u2.getText().toString().trim());
        requestDto.setMasterBenificiaryId(tin_detail_data.getFamilyDet().get(pos).getMasterBenificiaryId());
        requestDto.setYOB(txt_age.getText().toString().trim());
        requestDto.setAadhaar(txt_aadhar_no.getText().toString().trim());
        requestDto.setAadhaarName(txt_aadhar_name.getText().toString().trim());
        requestDto.setAadhaarXML(AadhaarXML);

        familyDet.add(requestDto);
    }



    private void add_new_member_SubmitInfo() {

        FamilyDetNewModel requestDto = new FamilyDetNewModel();
        requestDto.setMemberTIN("");
        requestDto.setTIN(null);
        requestDto.setRefKey("");
        requestDto.setUniqueTin(tin_detail_data.getUniqueTIN());
        requestDto.setName(txt_name.getText().toString().trim());
        requestDto.setGenderId(Integer.parseInt(family_gender_id));
        requestDto.setRelationId(family_relation_id);
        requestDto.setFamilyStatus(Integer.parseInt(family_status_id));
        if(txt_dob.getText().toString().length()==0) {
            requestDto.setDOB(null);
        }
        else{
            requestDto.setDOB(txt_dob.getText().toString());
        }
        requestDto.setDD("0");
        requestDto.setMM("0");
        requestDto.setYYYY("0");
        requestDto.setSmartCard(txt_smartcard_u.getText().toString().trim());
        requestDto.setPDS(txt_pds_u.getText().toString().trim());
        requestDto.setMANREGA(txt_manrega_u1.getText().toString().trim());
        requestDto.setMANREGA1(txt_manrega_u2.getText().toString().trim());
        requestDto.setMasterBenificiaryId(0);
        requestDto.setYOB(txt_age.getText().toString().trim());
        requestDto.setAadhaar(txt_aadhar_no.getText().toString().trim());
        requestDto.setAadhaarName(txt_aadhar_name.getText().toString().trim());
        requestDto.setAadhaarXML(AadhaarXML);

        familyDet.add(requestDto);
    }


    /**
     * check all needed permissions are allowed or not
     */
    private void checkPermission() {
        if (!mayRequestPermissions()) {
            return;
        } else {
            scanBarCode();
        }
    }

    private void scanBarCode() {
        try {
            //clearScanInfo();
            new IntentIntegrator(this).initiateScan();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
    }

    /**
     * receives the result of  QR Code Scanner and set the result on EditText
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if(requestCode ==REQUEST_CHECK_SETTINGS ) {
                if (resultCode == Activity.RESULT_OK) {
                    Log.e(TAG, "User agreed to make required location settings changes.");
                }
            }
            else if(requestCode == REQUEST_CHECK_SETTINGS ) {
                if (requestCode == Activity.RESULT_CANCELED) {
                    Log.e(TAG, "User chose not to make required location settings changes.");
                    mRequestingLocationUpdates = false;
                }
            }
            else{
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    System.out.println("SCAN INFO - "+result.getContents());
                    if (result.getContents() == null) {
                    } else {
                        if (!TextUtils.isEmpty(result.getContents())) {
                            LogManager.d("SCAN INFO", result.getContents());
                            parsingXml(result.getContents());
                        } else {
                            ToastUtils.shortToast(this, getString(R.string.scan_validation));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
    }


    private boolean mayRequestPermissions() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            }
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
           /* if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(CAMERA)) {
                Snackbar.make(uid, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{READ_EXTERNAL_STORAGE, CAMERA}, AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE);
                            }
                        }).show();
            }*/ else {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE, CAMERA}, AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE);
            }
            return false;
        } catch (Exception e) {
            LogManager.printStackTrace(e);
            return false;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
        }
    }

    /*method to parse xml string after getting from QR code scan*/



    private void parsingXml(String xmlString) {

        AadhaarXML = xmlString;
        System.out.println("AadhaarXML 1 - "+AadhaarXML );
        AadhaarXML=AadhaarXML.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
        System.out.println("AadhaarXML 2 - "+AadhaarXML );

        XMLDOMParser parser = new XMLDOMParser();
        InputStream stream;
        try {
            stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            Document doc = parser.getDocument(stream);

            // Get elements by name employee
            if(doc!=null) {
                NodeList nodeList = doc.getElementsByTagName(XMLParsingConstant.NODE_ADHAR);
                /*
                 * for each <ScanInfoRequestDto> element get text.
                 */
                if (nodeList != null) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Element e = (Element) nodeList.item(i);

                        adharCardInfoDto = new ScanInfoRequestDto();
                        adharCardInfoDto.setUid(e.getAttribute(XMLParsingConstant.ATTR_UID));
                        adharCardInfoDto.setName(e.getAttribute(XMLParsingConstant.ATTR_NAME));
                        adharCardInfoDto.setGender(e.getAttribute(XMLParsingConstant.ATTR_GENDER));
                        adharCardInfoDto.setYob(e.getAttribute(XMLParsingConstant.ATTR_YOB));
                        adharCardInfoDto.setGname(e.getAttribute(XMLParsingConstant.ATTR_GNAME));
                        adharCardInfoDto.setCo(e.getAttribute(XMLParsingConstant.ATTR_CO));
                        adharCardInfoDto.setHouse(e.getAttribute(XMLParsingConstant.ATTR_HOUSE));
                        adharCardInfoDto.setStreet(e.getAttribute(XMLParsingConstant.ATTR_STREET));
                        adharCardInfoDto.setLm(e.getAttribute(XMLParsingConstant.ATTR_LM));
                        adharCardInfoDto.setLoc(e.getAttribute(XMLParsingConstant.ATTR_LOC));
                        adharCardInfoDto.setVtc(e.getAttribute(XMLParsingConstant.ATTR_VTC));
                        adharCardInfoDto.setPo(e.getAttribute(XMLParsingConstant.ATTR_PO));
                        adharCardInfoDto.setDist(e.getAttribute(XMLParsingConstant.ATTR_DIST));
                        adharCardInfoDto.setSubdist(e.getAttribute(XMLParsingConstant.ATTR_SUBDIST));
                        adharCardInfoDto.setState(e.getAttribute(XMLParsingConstant.ATTR_STATE));
                        adharCardInfoDto.setPc(e.getAttribute(XMLParsingConstant.ATTR_PC));
                        adharCardInfoDto.setDob(e.getAttribute(XMLParsingConstant.ATTR_DOB));
                        adharCardInfoDto.setLoggedInUserID(AppPrefs.getInstance(this).getUserName(this));
                    }

                    displayOutput(adharCardInfoDto);
                } else {
                    ToastUtils.shortToast(this, getString(R.string.invalid_scan));
                }
            }else {
                ToastUtils.shortToast(this,getString(R.string.invalid_scan));
            }
        } catch (IOException e1) {
            LogManager.printStackTrace(e1);
        }
    }
    private void displayOutput(ScanInfoRequestDto adharCardInfoDto) {
        txt_aadhar_no.setText(adharCardInfoDto.getUid());
        txt_aadhar_name.setText(adharCardInfoDto.getName());
        // co  + house +  street + lm + vtc + dist + state + pc
       /* String address = "";
        if(adharCardInfoDto.getCo().length()>0) {
            address = address  + adharCardInfoDto.getCo() +", ";
        }
        if(adharCardInfoDto.getHouse().length()>0) {
            address = address  + adharCardInfoDto.getHouse() +", ";
        }
        if(adharCardInfoDto.getStreet().length()>0) {
            address = address  + adharCardInfoDto.getStreet() +", ";
        }
        if(adharCardInfoDto.getLm().length()>0) {
            address = address  + adharCardInfoDto.getLm() +", ";
        }
        if(adharCardInfoDto.getDist().length()>0) {
            address = address  + adharCardInfoDto.getDist() +", ";
        }
        if(adharCardInfoDto.getState().length()>0) {
            address = address  + adharCardInfoDto.getState() +", ";
        }
        if(adharCardInfoDto.getPc().length()>0) {
            address = address  + adharCardInfoDto.getPc() +", ";
        }

        txt_aadhar_address.setText(address);
        yob = adharCardInfoDto.getYob();
*/
        txt_aadhar_no.setFocusable(false);
        txt_aadhar_no.setEnabled(false);
        txt_aadhar_no.setCursorVisible(false);
        txt_aadhar_no.setTextColor(getResources().getColor(R.color.colorBlack));

        txt_aadhar_name.setFocusable(false);
        txt_aadhar_name.setEnabled(false);
        txt_aadhar_name.setCursorVisible(false);
        txt_aadhar_name.setTextColor(getResources().getColor(R.color.colorBlack));
    }
    // Get Current Location Work...........................

    // Get Current Location Work...........................
    void init_for_GPS(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void GetCurruntLattitudeLongitude() {
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else {
            startLocationButtonClick();
        }
    }

    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        // Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());
                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " + "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(FormDetails.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +"fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(FormDetails.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                        updateLocationUI();
                    }
                });
    }
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            // txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude() + ", " +"Lng: " + mCurrentLocation.getLongitude());
            // txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);
            //  Toast.makeText(getApplicationContext(), "Lat "+mCurrentLocation.getLatitude(),Toast.LENGTH_LONG).show();
            global_lattitude = mCurrentLocation.getLatitude();
            global_longitude = mCurrentLocation.getLongitude();

            if (mRequestingLocationUpdates) {
                // pausing location updates
                stopLocationUpdates();
            }
        }
    }
    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mRequestingLocationUpdates) {
                // pausing location updates
                stopLocationUpdates();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void init(){
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                init_for_GPS();
                GetCurruntLattitudeLongitude();
            }
            else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        }
        catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
        tin_no_house_hold = (TextView)findViewById(R.id.tin_no);
        member_txt_tin = (TextView)findViewById(R.id.member_txt_tin);
        txt_GP = (TextView)findViewById(R.id.txt_GP);
        txt_village = (TextView)findViewById(R.id.txt_village);
        txt_block = (TextView)findViewById(R.id.txt_block);
        txt_district = (TextView)findViewById(R.id.txt_district);
        txt_smartcard_u = (EditText)findViewById(R.id.txt_smartcard_u);
        txt_pds_u = (EditText)findViewById(R.id.txt_pds_u);
        txt_manrega_u1 = (EditText)findViewById(R.id.txt_manrega_u1);
        txt_manrega_u2 = (EditText)findViewById(R.id.txt_manrega_u2);
        txt_aadhar_no = (EditText)findViewById(R.id.txt_aadhar_no);
        txt_aadhar_name = (EditText)findViewById(R.id.txt_aadhar_name);
        txt_name = (EditText)findViewById(R.id.txt_name);
        txt_dob = (EditText)findViewById(R.id.txt_dob);

        txt_dob.setOnClickListener(this);

        txt_age = (EditText)findViewById(R.id.txt_age);

        btn_next = (Button)findViewById(R.id.btn_next);
        scan_aadhar = (Button)findViewById(R.id.scan_aadhar);
        add_new_member = (Button)findViewById(R.id.add_new_member);
        btn_next.setOnClickListener(this);
        add_new_member.setOnClickListener(this);
        scan_aadhar.setOnClickListener(this);

        gender_spinner =  (Spinner) findViewById(R.id.gender);
        status_spinner=  (Spinner) findViewById(R.id.status);
        relation_spinner=  (Spinner) findViewById(R.id.relation);

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                family_gender_id = family_gender.get(myPosition).getGenderId();
                if (family_gender_id.equals("1")) {
                    adapter_relation = new ArrayAdapter<String>(FormDetails.this, android.R.layout.simple_spinner_item, spinnerArray_relation_male);
                    adapter_relation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    relation_spinner.setAdapter(adapter_relation);
                } else if (family_gender_id.equals("2")) {
                    adapter_relation = new ArrayAdapter<String>(FormDetails.this, android.R.layout.simple_spinner_item, spinnerArray_relation_female);
                    adapter_relation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    relation_spinner.setAdapter(adapter_relation);
                } else if (family_gender_id.equals("3")) {
                    adapter_relation = new ArrayAdapter<String>(FormDetails.this, android.R.layout.simple_spinner_item, spinnerArray_relation_other);
                    adapter_relation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    relation_spinner.setAdapter(adapter_relation);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}});
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                family_status_id=family_status.get(myPosition).getFamilyStatusid();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}});
        relation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {
                family_relation_id =  family_relation.get(myPosition).getRelationID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}});
    }
    public static void hideKeyboard(Activity activity)
    {
        try
        {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        catch (Exception e)
        {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }
}