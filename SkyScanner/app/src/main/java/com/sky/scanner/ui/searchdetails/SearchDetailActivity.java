package com.sky.scanner.ui.searchdetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sky.scanner.BuildConfig;
import com.sky.scanner.R;
import com.sky.scanner.adapter.FamilyAdapter;
import com.sky.scanner.interfaces.AlertMagnatic;
import com.sky.scanner.model.AppSKYBenificiaryDocument;
import com.sky.scanner.model.AppSKYBenificiaryDocumentRequest;
import com.sky.scanner.model.AppSKYBenificiaryDocumentRequest_Request;
import com.sky.scanner.model.FamilyMembersDetailModel;
import com.sky.scanner.model.FilterModel;
import com.sky.scanner.model.LoginRequestDto;
import com.sky.scanner.model.ScanInfoRequestDto;
import com.sky.scanner.model.SearchDetailRequest;
import com.sky.scanner.model.SearchDetailResponse;
import com.sky.scanner.model.SearchDetailResponseModel;
import com.sky.scanner.model.Submit_All_Data_Request;
import com.sky.scanner.model.Submit_All_Data_Response;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.FamilyDetails;
import com.sky.scanner.ui.MyDividerItemDecoration;
import com.sky.scanner.ui.RecyclerTouchListener;
import com.sky.scanner.ui.SearchListActivity;
import com.sky.scanner.ui.search.SearchActivity;
import com.sky.scanner.utils.AppConstants;
import com.sky.scanner.utils.ConnectionUtils;
import com.sky.scanner.utils.DialogUtils;
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

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import okhttp3.ResponseBody;

public class SearchDetailActivity extends Activity implements ObserverCallBack.ServiceCallback,View.OnClickListener{
    private Button scan_aadhar,btn_next;
    private TextView txt_name,txt_tin,txt_block,txt_GP,txt_village,txt_dob,txt_gender,txt_district;
    private EditText txt_aadhar_no,txt_aadhar_name,txt_aadhar_fathername,txt_aadhar_address;
    private TextView loggedInUser;
    private ScanInfoRequestDto adharCardInfoDto;
    private SearchDetailResponse tin_detail_data ;

    private Bitmap FixBitmap;
    private ByteArrayOutputStream byteArrayOutputStream ;
    private byte[] byteArray ;
    private String ConvertImage1,ConvertImage2,ConvertImage3 ;
    private long id;
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
    private String yob="0";

    private EditText txt_smartcard,txt_PDS,txt_manrega1,txt_manrega2;
    private Button btn_img_smartcard,btn_img_pds,btn_img_mgnrega;
    private static final int CAMERA_PIC_REQUEST1 = 25001;
    private static final int CAMERA_PIC_REQUEST2 = 25002;
    private static final int CAMERA_PIC_REQUEST3 = 25003;

    private ArrayList<AppSKYBenificiaryDocumentRequest_Request> skybeneficiaryDocBObj;
    private ArrayList<FamilyMembersDetailModel> family_detail_model_fetch_data ;
    private Submit_All_Data_Request submit_request_obj;
  //  private Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_search_detail);
        init();

        try{
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("id")!=null) {
                id = Long.parseLong(bundle.getString("id"));
                searchTransaction();
            }
        }
        catch (Exception e){
            LogManager.printStackTrace(e);
        }
    }

    private void searchTransaction() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(1);
                WebServiceCallHelper.search_tin_detail_data(myObserver, createRequestModel());
            } else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }

    private SearchDetailRequest createRequestModel() {
        SearchDetailRequest requestDto = new SearchDetailRequest();
        //requestDto.setId(id);
        return requestDto;
    }

    @Override
    public void onSuccess(Object response, int tag) {
        try {
            if (tag == 1) {
                SearchDetailResponseModel responseModel = (SearchDetailResponseModel) response;
                if (responseModel != null) {
                    // declared on top..........
                   // tin_detail_data = responseModel.getResults();
                    System.out.println("tin_detail_data - "+tin_detail_data);
                    txt_name.setText(""+tin_detail_data.getName());txt_tin.setText(""+tin_detail_data.getTIN());
                    txt_block.setText(""+tin_detail_data.getBlock());txt_GP.setText(""+tin_detail_data.getGP());
                    txt_village.setText(""+tin_detail_data.getVillage());txt_dob.setText(""+tin_detail_data.getDOB());
                    txt_gender.setText(""+tin_detail_data.getGender());
                    txt_district.setText(""+tin_detail_data.getDistrict());
                    family_detail_model_fetch_data = new ArrayList<FamilyMembersDetailModel>();

                    for(int i =0 ; i<tin_detail_data.getFamilyDet().size(); i++){

                        FamilyMembersDetailModel ob = new FamilyMembersDetailModel();
                        ob.setTIN(tin_detail_data.getFamilyDet().get(i).getTIN());
                        ob.setName(tin_detail_data.getFamilyDet().get(i).getName());
                        ob.setGender(tin_detail_data.getFamilyDet().get(i).getGender());
                        ob.setGenderId(tin_detail_data.getFamilyDet().get(i).getGenderId());
                        ob.setAadhaar(tin_detail_data.getFamilyDet().get(i).getAadhaar());
                        ob.setCreatedBy(tin_detail_data.getCreatedBy());
                        ob.setYOB(tin_detail_data.getFamilyDet().get(i).getYOB());
                        ob.setFamilyStatus(tin_detail_data.getFamilyDet().get(i).getFamilyStatus());
                        ob.setSKYBenificiaryfamilyId(tin_detail_data.getFamilyDet().get(i).getSKYBenificiaryfamilyId());

                        family_detail_model_fetch_data.add(ob);
                    }
                    // ToastUtils.shortToast(getApplicationContext(),"total_family_member_count- "+family_detail_model_fetch_data.size());
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_aadhar:
                checkPermission();
                break;
            case R.id.btn_next:
                try {
                    if (ConnectionUtils.isNetworkAvailable(this)) {
                        if (global_lattitude != 0.0 && global_longitude != 0.0) {

                            if(txt_smartcard.getText().toString().trim().length()>0){

                                if(txt_PDS.getText().toString().trim().length()>0){

                                    if(txt_manrega1.getText().toString().trim().length()>0 &&
                                            txt_manrega2.getText().toString().trim().length()>0){

                                        if (txt_aadhar_no.getText().toString().trim().length()>0 &&
                                                txt_aadhar_name.getText().toString().trim().length()>0 &&
                                                txt_aadhar_fathername.getText().toString().trim().length()>0 &&
                                                txt_aadhar_address.getText().toString().trim().length()>0 ) {

                                            if (ConvertImage1 !=null && ConvertImage1.length()>0 && ConvertImage2 !=null
                                                    && ConvertImage2.length()>0 &&
                                                    ConvertImage3 !=null && ConvertImage3.length()>0) {

                                                add_Sky_Benificiary();

                                                System.out.println("submitScanInfo family details TIN 1 -  " +submit_request_obj.getTIN());
                                                System.out.println("submitScanInfo family details size 1 -  " + skybeneficiaryDocBObj.size());

                                                Intent in = new Intent(this, FamilyDetails.class);
                                                in.putExtra("submit_request_obj",submit_request_obj);
                                                in.putParcelableArrayListExtra("skybeneficiaryDocBObj",skybeneficiaryDocBObj);
                                                in.putParcelableArrayListExtra("response_data",family_detail_model_fetch_data);
                                                startActivity(in);
                                                finish();

                                            }
                                            else {
                                                ToastUtils.shortToast(this, getString(R.string.add_img));
                                            }
                                        }
                                        else
                                            ToastUtils.shortToast(this, getString(R.string.add_aadhar));
                                    }
                                    else
                                        ToastUtils.shortToast(this, getString(R.string.add_mgn));
                                }
                                else
                                    ToastUtils.shortToast(this, getString(R.string.add_pds));
                            }
                            else
                                ToastUtils.shortToast(this, getString(R.string.add_smartcard));

                        } else {
                            GetCurruntLattitudeLongitude();
                        }
                    } else {
                        ToastUtils.shortToast(this, getString(R.string.server_error));
                    }
                } catch (Exception ex) {
                    LogManager.printStackTrace(ex);
                }
                break;

            case R.id.btn_img_smartcard_u:
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, CAMERA_PIC_REQUEST1);
                break;

            case R.id.btn_img_pds_u:
                Intent cameraIntent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent2, CAMERA_PIC_REQUEST2);
                break;

            case R.id.btn_img_mgnrega_u:
                Intent cameraIntent3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent3, CAMERA_PIC_REQUEST3);
                break;
        }
    }

    private void add_Sky_Benificiary() {
        try{
            submit_request_obj = new  Submit_All_Data_Request();

            submit_request_obj.setSKYBenificiaryId(tin_detail_data.getSKYBenificiaryId());
            submit_request_obj.setTIN(tin_detail_data.getTIN());
//            if(switch1.isChecked()) {
//                submit_request_obj.setIsdisable(1);
//            }
//            else{
//                submit_request_obj.setIsdisable(0);
//            }
            submit_request_obj.setDistrictId(tin_detail_data.getDistrictId());
            submit_request_obj.setBlockId(tin_detail_data.getBlockId());
            submit_request_obj.setGPId(tin_detail_data.getGPId());
            submit_request_obj.setVillageId(tin_detail_data.getVillageId());
            submit_request_obj.setGenderId(tin_detail_data.getGenderId());
            if(yob!=null && yob.length()>0) {
                submit_request_obj.setYOB(Integer.parseInt(yob));
            }
            submit_request_obj.setAadhaar(txt_aadhar_no.getText().toString().trim());
            submit_request_obj.setAadhaarName(txt_aadhar_name.getText().toString().trim());
            submit_request_obj.setAFatherName(txt_aadhar_fathername.getText().toString().trim());
            submit_request_obj.setAddress(txt_aadhar_address.getText().toString().trim());
            submit_request_obj.setCreatedBy(tin_detail_data.getCreatedBy());
            submit_request_obj.setApppds(txt_PDS.getText().toString().trim());
            submit_request_obj.setAppSmartCard(txt_smartcard.getText().toString().trim());
            submit_request_obj.setAppmanrega(txt_manrega1.getText().toString().trim());
            submit_request_obj.setAppmanregA1(txt_manrega2.getText().toString().trim());

            skybeneficiaryDocBObj = new ArrayList<AppSKYBenificiaryDocumentRequest_Request>();

            for(int i = 0 ; i<4 ; i++) {
                AppSKYBenificiaryDocumentRequest_Request skybeneficiaryDocBObj_ = new AppSKYBenificiaryDocumentRequest_Request();
                skybeneficiaryDocBObj_.setCreatedBy(tin_detail_data.getCreatedBy());
                skybeneficiaryDocBObj_.setSkyBenificiaryDocumentId(0);
                skybeneficiaryDocBObj_.setSkyBenificiaryId(tin_detail_data.getSKYBenificiaryId());
                skybeneficiaryDocBObj_.setDescription("Testing doc");
                skybeneficiaryDocBObj_.setUrl("");
                skybeneficiaryDocBObj_.setActive(true);
                skybeneficiaryDocBObj_.setLatitude(global_lattitude + "");
                skybeneficiaryDocBObj_.setLongitude(global_longitude + "");
                skybeneficiaryDocBObj_.setApp(true);
                skybeneficiaryDocBObj_.setExtImage("jpg");
                if(i==0) {
                    skybeneficiaryDocBObj_.setName("Smart Card");
                    skybeneficiaryDocBObj_.setImage(ConvertImage1);
                    skybeneficiaryDocBObj_.setType(8);
                }
                if(i==1) {
                    skybeneficiaryDocBObj_.setName("PDS Card");
                    skybeneficiaryDocBObj_.setImage(ConvertImage2);
                    skybeneficiaryDocBObj_.setType(7);
                }
                if(i==2) {
                    skybeneficiaryDocBObj_.setName("Mgnrega Card");
                    skybeneficiaryDocBObj_.setImage(ConvertImage3);
                    skybeneficiaryDocBObj_.setType(6);
                }
                if(i==3) {
                    skybeneficiaryDocBObj_.setName("House Hold");
                    skybeneficiaryDocBObj_.setType(4);
                    // add this image on House Hold Screen...
                    //skybeneficiaryDocBObj_.setImage(ConvertImage3);
                }
                skybeneficiaryDocBObj.add(skybeneficiaryDocBObj_);
            }

        }
        catch (Exception e){
            LogManager.printStackTrace(e);
        }
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
            if (requestCode == CAMERA_PIC_REQUEST1) {
                FixBitmap = (Bitmap) data.getExtras().get("data");
                //imageview.setImageBitmap(FixBitmap);
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage1 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                System.out.println("ConvertImage1 - "+ConvertImage1);

            }
            else if (requestCode == CAMERA_PIC_REQUEST2) {
                FixBitmap = (Bitmap) data.getExtras().get("data");
                //imageview.setImageBitmap(FixBitmap);
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                System.out.println("ConvertImage2 - "+ConvertImage2);

            }
            else if (requestCode == CAMERA_PIC_REQUEST3) {
                FixBitmap = (Bitmap) data.getExtras().get("data");
                //imageview.setImageBitmap(FixBitmap);
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage3 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                System.out.println("ConvertImage3 - "+ConvertImage3);

            }
            else if(requestCode ==REQUEST_CHECK_SETTINGS ) {
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
            else {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        if (requestCode == AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
        }
    }

    /*method to parse xml string after getting from QR code scan*/



    private void parsingXml(String xmlString) {
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
        txt_aadhar_fathername.setText(adharCardInfoDto.getGname());
        // co  + house +  street + lm + vtc + dist + state + pc
        String address = "";
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

        txt_aadhar_no.setFocusable(false);
        txt_aadhar_no.setEnabled(false);
        txt_aadhar_no.setCursorVisible(false);
        txt_aadhar_no.setTextColor(getResources().getColor(R.color.colorBlack));

        txt_aadhar_name.setFocusable(false);
        txt_aadhar_name.setEnabled(false);
        txt_aadhar_name.setCursorVisible(false);
        txt_aadhar_name.setTextColor(getResources().getColor(R.color.colorBlack));

        txt_aadhar_fathername.setFocusable(false);
        txt_aadhar_fathername.setEnabled(false);
        txt_aadhar_fathername.setCursorVisible(false);
        txt_aadhar_fathername.setTextColor(getResources().getColor(R.color.colorBlack));

        txt_aadhar_address.setFocusable(false);
        txt_aadhar_address.setEnabled(false);
        txt_aadhar_address.setCursorVisible(false);
        txt_aadhar_address.setTextColor(getResources().getColor(R.color.colorBlack));
    }
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
                                    rae.startResolutionForResult(SearchDetailActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +"fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(SearchDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
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
        loggedInUser  =  (TextView)findViewById(R.id.txt_loggedinuser);
        loggedInUser.setText("Welcome : "+ AppPrefs.getInstance(getApplicationContext()).getUserName(getApplicationContext()));
        //switch1 = (Switch)findViewById(R.id.switch1);

        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_tin  = (TextView)findViewById(R.id.txt_tin);
        txt_block = (TextView)findViewById(R.id.txt_block);
        txt_GP = (TextView)findViewById(R.id.txt_GP );
        txt_village  = (TextView)findViewById(R.id.txt_village);
        txt_dob  = (TextView)findViewById(R.id.txt_dob );
        txt_gender  = (TextView)findViewById(R.id.txt_gender );
        txt_district  = (TextView)findViewById(R.id.txt_district );


        txt_PDS  = (EditText)findViewById(R.id.txt_pds_u);
        txt_smartcard   = (EditText) findViewById(R.id.txt_smartcard_u);
        txt_manrega1  = (EditText) findViewById(R.id.txt_manrega_u1);
        txt_manrega2  = (EditText) findViewById(R.id.txt_manrega_u2);


        txt_aadhar_no  = (EditText)findViewById(R.id.txt_aadhar_no );
        txt_aadhar_name  = (EditText)findViewById(R.id.txt_aadhar_name );
        txt_aadhar_fathername  = (EditText)findViewById(R.id.txt_aadhar_fathername );
        txt_aadhar_address   = (EditText)findViewById(R.id.txt_aadhar_address );


        scan_aadhar = (Button)findViewById(R.id.scan_aadhar) ;
        btn_next= (Button)findViewById(R.id.btn_next) ;
        btn_img_smartcard  = (Button)findViewById(R.id.btn_img_smartcard_u) ;
        btn_img_pds = (Button)findViewById(R.id.btn_img_pds_u) ;
        btn_img_mgnrega  = (Button)findViewById(R.id.btn_img_mgnrega_u) ;
        scan_aadhar.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_img_smartcard.setOnClickListener(this);
        btn_img_pds.setOnClickListener(this);
        btn_img_mgnrega.setOnClickListener(this);

    }
}
