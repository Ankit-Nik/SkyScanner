package com.sky.scanner.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sky.scanner.ModelsNew.LDocNewModel;
import com.sky.scanner.ModelsNew.SubmitDataNewModel;
import com.sky.scanner.R;
import com.sky.scanner.model.AppSKYBenificiaryDocumentRequest_Request;
import com.sky.scanner.model.FamilyDetModel;
import com.sky.scanner.model.Submit_All_Data_Request;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.search.SearchActivity;
import com.sky.scanner.utils.ConnectionUtils;
import com.sky.scanner.utils.GlobalData;
import com.sky.scanner.utils.LogManager;
import com.sky.scanner.utils.ToastUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class HousePicActivity extends Activity  implements ObserverCallBack.ServiceCallback,View.OnClickListener{
    private ImageView imageView2;
    private static final int CAMERA_PIC_REQUEST = 2500;
    private Bitmap FixBitmap;
    private ByteArrayOutputStream byteArrayOutputStream ;
    private  byte[] byteArray ;
    private String ConvertImage ;
    private TextView loggedInUser;
    // private Submit_All_Data_Request submit_request;
    private Button btn_next;
    private ArrayList<AppSKYBenificiaryDocumentRequest_Request> skybeneficiaryDocBObj;
    // private ArrayList<FamilyDetModel> getSingeltonData_fmilyarr;
    private SubmitDataNewModel getSingeltonData_fmilyarr;
    String global_lattitude,global_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_house_pic);
        init();
        //      System.out.println("House Activity");

        try {
            if (getIntent().getExtras() != null) {
                global_lattitude = getIntent().getExtras().getString("global_lattitude");
                global_longitude = getIntent().getExtras().getString("global_longitude");
                getSingeltonData_fmilyarr = GlobalData.getInstance().getFamilyDetarray();
                // getSingeltonData_fmilyarr = getIntent().getExtras().getParcelable("submit_request_obj");
                //getSingeltonData_fmilyarr = GlobalData.getInstance().getFamilyDetarray();
            }
        }
        catch (Exception e){
            LogManager.printStackTrace(e);
        }

    }

    void addHouseHoldImage(String img_content) {
        ArrayList<LDocNewModel> ldoc = new ArrayList<LDocNewModel>();

        LDocNewModel obj = new LDocNewModel();

        obj.setMasterBenificiaryId("");
        obj.setDocName("HouseHold");
        obj.setUrl("");
        obj.setExtension(".jpg");
        obj.setIsActive("1");
        obj.setImage(img_content);
        obj.setLatitude(global_lattitude);
        obj.setLongitude(global_longitude);
        ldoc.add(obj);

        getSingeltonData_fmilyarr.setLdoc(ldoc);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                break;

            case R.id.btn_next:
                try {
                    if (ConnectionUtils.isNetworkAvailable(this)) {
                        if (ConvertImage != null && ConvertImage.length() > 0) {
                            serverTransaction();
                        } else
                            ToastUtils.shortToast(this, getString(R.string.add_img));
                    } else
                        ToastUtils.shortToast(this, getString(R.string.server_error));
                }
                catch(Exception ex){
                    LogManager.printStackTrace(ex);
                }
                break;
        }
    }

    private void serverTransaction() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(1);
                WebServiceCallHelper.submitScanInfoImage(myObserver, getSingeltonData_fmilyarr);
            } else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }


    @Override
    public void onSuccess(Object response, int tag) {
        try {
            if (tag == 1) {
                System.out.println("Send Data Response - " + response);
                String jsonStr = ((ResponseBody) response).string();
                JSONObject jsonObj = new JSONObject(jsonStr);
                String ErrorMessage = jsonObj.getString("ErrorMessage");
                System.out.println("Send Data Response - " + jsonStr);
                ToastUtils.shortToast(this, ErrorMessage);
                Intent i = new Intent(this, SearchActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //finish();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String msg, Throwable error) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CAMERA_PIC_REQUEST) {
                FixBitmap = (Bitmap) data.getExtras().get("data");
                imageView2.setBackgroundDrawable(null);
                imageView2.setImageBitmap(FixBitmap);
                byteArrayOutputStream = new ByteArrayOutputStream();
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                addHouseHoldImage(ConvertImage);
            }

        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
    }

    void init(){
        loggedInUser  =  (TextView)findViewById(R.id.txt_loggedinuser);
        loggedInUser.setText(AppPrefs.getInstance(getApplicationContext()).getUserName(getApplicationContext()));

        imageView2 =  (ImageView)findViewById(R.id.imageView2);
        imageView2.setOnClickListener(this);
        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }


}