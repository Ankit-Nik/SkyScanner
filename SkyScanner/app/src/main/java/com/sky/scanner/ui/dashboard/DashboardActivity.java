package com.sky.scanner.ui.dashboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sky.scanner.R;
import com.sky.scanner.interfaces.AlertMagnatic;
import com.sky.scanner.model.ScanInfoRequestDto;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.login.LoginActivity;
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
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ObserverCallBack.ServiceCallback {

    private static final String EXTRA_DATA_DASHBOARD = "home_data";
    private Button btnSubmit;
    private ScanInfoRequestDto adharCardInfoDto;
    private TextView uid, name, gender, yob, co, house, street, landMark, loc, vtc, postOffice, dist,
            subDist, state, pinCode, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        setContentView(R.layout.activity_dashboard);
        init();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    private void init() {
        btnSubmit = findViewById(R.id.btn_submit);
        uid = findViewById(R.id.tv_uid);
        name = findViewById(R.id.tv_name);
        gender = findViewById(R.id.tv_gender);
        yob = findViewById(R.id.tv_yob);
        co = findViewById(R.id.tv_co);
        house = findViewById(R.id.tv_house);
        street = findViewById(R.id.tv_street);
        landMark = findViewById(R.id.tv_lm);
        loc = findViewById(R.id.tv_loc);
        vtc = findViewById(R.id.tv_vtc);
        postOffice = findViewById(R.id.tv_po);
        dist = findViewById(R.id.tv_dist);
        subDist = findViewById(R.id.tv_subdist);
        state = findViewById(R.id.tv_state);
        pinCode = findViewById(R.id.tv_pc);
        dob = findViewById(R.id.tv_dob);

        adharCardInfoDto = new ScanInfoRequestDto();
        btnSubmit.setOnClickListener(this);
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
            clearScanInfo();
            new IntentIntegrator(this).initiateScan();
        } catch (Exception e) {
            LogManager.printStackTrace(e);
        }
    }


    /**
     * receives the result of  QR Code Scanner and set the result on EditText
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (result != null) {
                if (result.getContents() == null) {
                } else {

                    if (!TextUtils.isEmpty(result.getContents())) {

                        LogManager.d("SCAN INFO", result.getContents());
                        parsingXml(result.getContents());
                    } else {

                        clearScanInfo();
                        ToastUtils.shortToast(this, getString(R.string.scan_validation));
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
            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(CAMERA)) {
                Snackbar.make(uid, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{READ_EXTERNAL_STORAGE, CAMERA}, AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE);
                            }
                        }).show();
            } else {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
        }
    }


    /**
     * method to call {@link LoginActivity} from any other Screen
     *
     * @param context
     * @param data
     */
    public static void switchToDashboardScreen(Context context, String data) {
        Intent intent = new Intent(context, DashboardActivity.class);
        if (!data.equals("")) {
            intent.putExtra(EXTRA_DATA_DASHBOARD, data);
        }
        context.startActivity(intent);
    }

    private void submitScanInfo() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(2);

                WebServiceCallHelper.submitScanInfo(myObserver, adharCardInfoDto);
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
                // Retrofit service creation code skipped here
                if (response != null) {
                    String text = ((ResponseBody) response).string();
                    System.out.println(text);//convert reponse to string
                    ToastUtils.shortToast(this, text);
                    clearScanInfo();
                   /* LoginActivity.switchToLoginScreen(this,"");
                    finish();*/
                } else {

                    ToastUtils.shortToast(this, getString(R.string.server_error_occured));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:

/*Check for validation*/
                if (TextUtils.isEmpty(uid.getText())) {
                    ToastUtils.shortToast(this, getString(R.string.required_all_fields));

                } else {
                    callConformation(getString(R.string.app_name), getString(R.string.confirm_submition));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

        callConformation(getString(R.string.exit_title), getString(R.string.exit_conformation));
    }

    /**
     * show alert dialog to confirm on submit click
     * if Yes: call web service
     * OR
     * ask for confirmation to exit from Home Screen
     */
    private void callConformation(final String title, String msz) {
        DialogUtils.getConfirmDialog(this, title, msz, getString(R.string.yes), getString(R.string.no), true,
                new AlertMagnatic() {
                    @Override
                    public void onButtonClicked(boolean value, String id) {
                        if (value) {
                            if (title.equals(getString(R.string.exit_title))) {
                                setIntent(null);
                                finish();
                            } else {
                                submitScanInfo();
                            }
                        }
                    }
                });
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
        uid.setText(adharCardInfoDto.getUid());
        name.setText(adharCardInfoDto.getName());
        gender.setText(adharCardInfoDto.getGender());
        yob.setText(adharCardInfoDto.getYob());
        co.setText(adharCardInfoDto.getCo());
        house.setText(adharCardInfoDto.getHouse());
        street.setText(adharCardInfoDto.getStreet());
        landMark.setText(adharCardInfoDto.getLm());
        loc.setText(adharCardInfoDto.getLoc());
        vtc.setText(adharCardInfoDto.getVtc());
        postOffice.setText(adharCardInfoDto.getPo());
        dist.setText(adharCardInfoDto.getDist());
        subDist.setText(adharCardInfoDto.getSubdist());
        state.setText(adharCardInfoDto.getState());
        pinCode.setText(adharCardInfoDto.getPc());
        dob.setText(adharCardInfoDto.getDob());

    }

    private void clearScanInfo() {
        uid.setText("");
        name.setText("");
        gender.setText("");
        yob.setText("");
        co.setText("");
        house.setText("");
        street.setText("");
        landMark.setText("");
        loc.setText("");
        vtc.setText("");
        postOffice.setText("");
        dist.setText("");
        subDist.setText("");
        state.setText("");
        pinCode.setText("");
        dob.setText("");

    }

}
