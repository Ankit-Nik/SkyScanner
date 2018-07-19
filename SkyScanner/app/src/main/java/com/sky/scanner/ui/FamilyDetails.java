package com.sky.scanner.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sky.scanner.R;
import com.sky.scanner.model.AppSKYBenificiaryDocumentRequest_Request;
import com.sky.scanner.model.FamilyDetModel;
import com.sky.scanner.model.FamilyDetModel_SkybeneficiaryFDocObj;
import com.sky.scanner.model.FamilyMembersDetailModel;
import com.sky.scanner.model.ScanInfoRequestDto;
import com.sky.scanner.model.Submit_All_Data_Request;
import com.sky.scanner.prefernce.AppPrefs;
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
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class FamilyDetails extends Activity implements View.OnClickListener{
    private Button btn_next,btn_img_smartcard,btn_img_pds,btn_img_mgnrega,scan_aadhar;
    private EditText txt_smartcard,txt_pds,txt_manrega1,txt_manrega2;
    private EditText txt_aadhar_no,txt_aadhar_name,txt_aadhar_fathername,txt_aadhar_address;
    private TextView loggedInUser,txt_name,txt_tin;
    private ArrayList<FamilyMembersDetailModel> family_detail_model ;
    private int family_member_count = 0;
    private int total_family_member_count = 0;
    private int position = 0;

    private Bitmap FixBitmap;
    private ByteArrayOutputStream byteArrayOutputStream ;
    private byte[] byteArray ;
    private String ConvertImage1,ConvertImage2,ConvertImage3 ;
    private static final int CAMERA_PIC_REQUEST1 = 25001;
    private static final int CAMERA_PIC_REQUEST2 = 25002;
    private static final int CAMERA_PIC_REQUEST3 = 25003;
    private String yob="0";
    private ScanInfoRequestDto adharCardInfoDto;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    private ArrayList<FamilyDetModel> familyDetarray;
    private ArrayList<AppSKYBenificiaryDocumentRequest_Request> skybeneficiaryDocBObj;
    private Submit_All_Data_Request submit_request_obj;

    private Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_family_details);
        init();

        familyDetarray = new ArrayList<FamilyDetModel>();

        try {
            if (getIntent().getExtras() != null) {
                submit_request_obj =  getIntent().getExtras().getParcelable("submit_request_obj");
                skybeneficiaryDocBObj =  getIntent().getExtras().getParcelableArrayList("skybeneficiaryDocBObj");

//        System.out.println("submitScanInfo family details TIN 2 -  " +submit_request_obj.getTIN());
//        System.out.println("submitScanInfo family details size 2 -  " + skybeneficiaryDocBObj.size());

                family_detail_model =  getIntent().getExtras().getParcelableArrayList("response_data");
                total_family_member_count = family_detail_model.size();

                txt_name.setText(""+family_detail_model.get(family_member_count).getName());
                txt_tin.setText(""+family_detail_model.get(family_member_count).getTIN());

            }
        }
        catch (Exception e){
            LogManager.printStackTrace(e);
        }

        total_family_member_count = total_family_member_count - 1;
        family_member_count = family_member_count + 1;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_next:
                try {
                    if (ConnectionUtils.isNetworkAvailable(this)) {


                        if(txt_smartcard.getText().toString().trim().length()>0){

                            if(txt_pds.getText().toString().trim().length()>0){

                                if(txt_manrega1.getText().toString().trim().length()>0 &&
                                        txt_manrega2.getText().toString().trim().length()>0){

                                    if (txt_aadhar_no.getText().toString().trim().length()>0 &&
                                            txt_aadhar_name.getText().toString().trim().length()>0 &&
                                            txt_aadhar_fathername.getText().toString().trim().length()>0 &&
                                            txt_aadhar_address.getText().toString().trim().length()>0 ) {

                                        if (ConvertImage1 !=null && ConvertImage1.length()>0 && ConvertImage2 !=null
                                                && ConvertImage2.length()>0 &&
                                                ConvertImage3 !=null && ConvertImage3.length()>0) {


                                            createRequestModelForSubmitInfo(position);
                                            position = position + 1;


                                            if(total_family_member_count == 0) {

                                             //   GlobalData.getInstance().setFamilyDetarray(familyDetarray);

                                                Intent in = new Intent(this, HousePicActivity.class);
                                                in.putExtra("submit_request_obj",submit_request_obj);
                                                in.putParcelableArrayListExtra("skybeneficiaryDocBObj",skybeneficiaryDocBObj);
                                                // in.putParcelableArrayListExtra("familyDetarray",familyDetarray);
                                                clear_form();
                                                startActivity(in);
                                                finish();
                                            }
                                            else {
                                                txt_name.setText("" + family_detail_model.get(family_member_count).getName());
                                                txt_tin.setText("" + family_detail_model.get(family_member_count).getTIN());

                                                total_family_member_count = total_family_member_count - 1;
                                                family_member_count = family_member_count + 1;

                                                clear_form();
                                            }

                                        }
                                        else {
                                            ToastUtils.shortToast(this, getString(R.string.add_img));
                                        }
                                    }
                                    else {
                                        ToastUtils.shortToast(this, getString(R.string.add_aadhar));

                                    }
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
                        ToastUtils.shortToast(this, getString(R.string.server_error));
                    }
                } catch (Exception ex) {
                    LogManager.printStackTrace(ex);
                }
                break;


            case R.id.btn_img_smartcard:
                Intent cameraIntent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent1, CAMERA_PIC_REQUEST1);
                break;

            case R.id.btn_img_pds:
                Intent cameraIntent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent2, CAMERA_PIC_REQUEST2);
                break;

            case R.id.btn_img_mgnrega:
                Intent cameraIntent3 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent3, CAMERA_PIC_REQUEST3);
                break;

            case R.id.scan_aadhar:
                checkPermission();
                break;
        }
    }

    private void createRequestModelForSubmitInfo(int pos) {
        try{
            FamilyDetModel requestDto = new FamilyDetModel();

            requestDto.setSkyBenificiaryfamilyId(family_detail_model.get(pos).getSKYBenificiaryfamilyId());
            requestDto.setTin(txt_tin.getText().toString());
            requestDto.setApppds("PDS New F");
            requestDto.setAppSmartCard(txt_smartcard.getText().toString().trim());
            requestDto.setAppmanrega(txt_manrega1.getText().toString().trim());
            requestDto.setAppmanregA1(txt_manrega2.getText().toString().trim());
            requestDto.setApp(true);
            if(switch1.isChecked()) {
                requestDto.setIsdisable(1);
            }
            else{
                requestDto.setIsdisable(0);
            }
            requestDto.setGenderId(1);
            if(yob!=null && yob.length()>0) {
                requestDto.setYOB(Integer.parseInt(yob));
            }
            requestDto.setAadhaar(txt_aadhar_no.getText().toString().trim());
            requestDto.setAadhaarName(txt_aadhar_name.getText().toString().trim());
            requestDto.setGuardianName(txt_aadhar_fathername.getText().toString().trim());
            requestDto.setAddress(txt_aadhar_address.getText().toString().trim());

            ArrayList<FamilyDetModel_SkybeneficiaryFDocObj> obj_arr = new ArrayList<FamilyDetModel_SkybeneficiaryFDocObj>();

            for(int i = 0 ; i<3 ; i++) {
                FamilyDetModel_SkybeneficiaryFDocObj ch_obj = new FamilyDetModel_SkybeneficiaryFDocObj();

                ch_obj.setSkyBenificiaryFamilyDocumentId(0);
                ch_obj.setSkyBenificiaryFamilyId(family_detail_model.get(pos).getSKYBenificiaryfamilyId());
                ch_obj.setCreatedBy(family_detail_model.get(0).getCreatedBy());
                ch_obj.setDescription("Testing doc");
                ch_obj.setUrl("");
                ch_obj.setActive(true);
                ch_obj.setApp(true);
                ch_obj.setExtImage("jpg");
                ch_obj.setRowAttachmentType(1);
                if(i==0) {
                    ch_obj.setName("Smart Card");
                    ch_obj.setImage(ConvertImage1);
                    ch_obj.setType(8);
                }
                if(i==1) {
                    ch_obj.setName("PDS Card");
                    ch_obj.setImage(ConvertImage2);
                    ch_obj.setType(7);
                }
                if(i==2) {
                    ch_obj.setName("Mgnrega Card");
                    ch_obj.setImage(ConvertImage3);
                    ch_obj.setType(6);
                }

                obj_arr.add(ch_obj);
            }

            requestDto.setSkybeneficiaryFDocObj(obj_arr);

            familyDetarray.add(requestDto);
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
        try{
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
        catch (Exception e){
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
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                System.out.println("ConvertImage2 - "+ConvertImage2);

            }
            else if (requestCode == CAMERA_PIC_REQUEST3) {
                FixBitmap = (Bitmap) data.getExtras().get("data");
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage3 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                System.out.println("ConvertImage3 - "+ConvertImage3);

            }
            else if(requestCode ==REQUEST_CHECK_SETTINGS ) {
                if (resultCode == Activity.RESULT_OK) {
                    // Log.e(TAG, "User agreed to make required location settings changes.");
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
    void clear_form(){
        txt_smartcard.setText("");
        txt_pds.setText("");
        txt_manrega1.setText("");
        txt_manrega2.setText("");
        txt_aadhar_no.setText("");
        txt_aadhar_name.setText("");
        txt_aadhar_fathername.setText("");
        txt_aadhar_address.setText("");
        ConvertImage1="";
        ConvertImage2="";
        ConvertImage3="";
    }

    void init(){
        loggedInUser  =  (TextView)findViewById(R.id.txt_loggedinuser);
        loggedInUser.setText("Welcome : "+AppPrefs.getInstance(getApplicationContext()).getUserName(getApplicationContext()));

        switch1 = (Switch)findViewById(R.id.switch1);

        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_tin = (TextView)findViewById(R.id.txt_tin);

        btn_next =  (Button)findViewById(R.id.btn_next);
        btn_img_smartcard =  (Button)findViewById(R.id.btn_img_smartcard);
        btn_img_pds =  (Button)findViewById(R.id.btn_img_pds);
        btn_img_mgnrega =  (Button)findViewById(R.id.btn_img_mgnrega);
        scan_aadhar =  (Button)findViewById(R.id.scan_aadhar);

        btn_next.setOnClickListener(this);
        btn_img_smartcard.setOnClickListener(this);
        btn_img_pds.setOnClickListener(this);
        btn_img_mgnrega.setOnClickListener(this);
        scan_aadhar.setOnClickListener(this);

        txt_smartcard = (EditText)findViewById(R.id.txt_smartcard);
        txt_pds = (EditText)findViewById(R.id.txt_pds);
        txt_manrega1= (EditText)findViewById(R.id.txt_manrega1);
        txt_manrega2= (EditText)findViewById(R.id.txt_manrega2);

        txt_aadhar_no  = (EditText)findViewById(R.id.txt_aadhar_no );
        txt_aadhar_name  = (EditText)findViewById(R.id.txt_aadhar_name );
        txt_aadhar_fathername  = (EditText)findViewById(R.id.txt_aadhar_fathername );
        txt_aadhar_address   = (EditText)findViewById(R.id.txt_aadhar_address );
    }
}
