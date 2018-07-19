package com.sky.scanner.ui.login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.scanner.R;
import com.sky.scanner.model.LoginRequestDto;
import com.sky.scanner.model.LoginResponseDto;
import com.sky.scanner.network.WebServiceCallHelper;
import com.sky.scanner.network.retrofit.ObserverCallBack;
import com.sky.scanner.prefernce.AppPrefs;
import com.sky.scanner.ui.FormDetails;
import com.sky.scanner.ui.search.SearchActivity;
import com.sky.scanner.utils.AppConstants;
import com.sky.scanner.utils.ConnectionUtils;
import com.sky.scanner.utils.GlobalData;
import com.sky.scanner.utils.LogManager;
import com.sky.scanner.utils.ToastUtils;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ObserverCallBack.ServiceCallback {

    /**
     * Id to identity REQUEST_CAMERA permission request.
     */
    private static final int REQUEST_CAMERA = 0;
    private static final String EXTRA_DATA_LOGIN = "login_data";

    // UI references.
    private EditText mUserNameView, mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserNameView = findViewById(R.id.user_name);
        checkRuntimePermission();

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.btn_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void checkRuntimePermission() {
        if (!mayRequestPermissions()) {
            return;
        }
    }

    private boolean mayRequestPermissions() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            }
            if (checkSelfPermission(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            if (shouldShowRequestPermissionRationale(ACCESS_NETWORK_STATE)
                    && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
                    && shouldShowRequestPermissionRationale(CAMERA)) {
                Snackbar.make(mUserNameView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{ACCESS_NETWORK_STATE, READ_EXTERNAL_STORAGE, CAMERA, GET_ACCOUNTS}, AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE);
                            }
                        });
            } else {
                requestPermissions(new String[]{ACCESS_NETWORK_STATE, READ_EXTERNAL_STORAGE, CAMERA}, AppConstants.REQUEST_PERMISSION_CODE.REQUEST_ALL_PERMISSION_CODE);
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
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkRuntimePermission();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid username address.
        if (TextUtils.isEmpty(email)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }
        // Check for a valid password, if the use r entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        /*&& !isPasswordValid(password)
        else if (!isEmailValid(email)) {
            mUserNameView.setError(getString(R.string.error_invalid_email));
            focusView = mUserNameView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            AppPrefs.getInstance(this).saveAccessToken(this, "");
            loginUser();
            //successLoginCall();
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * method to call {@link LoginActivity} from any other Screen
     *
     * @param context
     * @param data
     */
    public static void switchToLoginScreen(Context context, String data) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (!data.equals("")) {
            intent.putExtra(EXTRA_DATA_LOGIN, data);
        }
        context.startActivity(intent);
    }


    private void loginUser() {
        try {
            if (ConnectionUtils.isNetworkAvailable(this)) {
                ObserverCallBack myObserver = new ObserverCallBack(this);
                myObserver.setLoading(true);
                myObserver.setListener(this);
                myObserver.setRequestTag(1);

                WebServiceCallHelper.loginUser(myObserver, createRequestModel());
            } else {
                ToastUtils.shortToast(this, getString(R.string.server_error));
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }

    private LoginRequestDto createRequestModel() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername(mUserNameView.getText().toString());
        requestDto.setPassword(mPasswordView.getText().toString());
        requestDto.setGrant_type(AppConstants.GRANT_TYPE);
        return requestDto;
    }


    @Override
    public void onSuccess(Object response, int tag) {
        try {
            if (tag == 1) {
                LoginResponseDto responseModel = (LoginResponseDto) response;
                System.out.println("Login Response - "+responseModel);
                if (responseModel != null) {
                    if (responseModel.getAccessToken() != null) {
                        successLoginCall(responseModel);
                    } else {
                        ToastUtils.shortToast(this, getString(R.string.something_went_wrong));
                    }
                }
            }
        } catch (Exception ex) {
            LogManager.printStackTrace(ex);
        }
    }

    private void successLoginCall(LoginResponseDto responseModel) {
        AppPrefs.getInstance(this).saveUserName(this, mUserNameView.getText().toString());
        AppPrefs.getInstance(this).saveAccessToken(this, responseModel.getAccessToken());
        AppPrefs.getInstance(this).saveUserId(this, responseModel.getUserId());

       SearchActivity.switchToSearchScreen(this, "");
        //   startActivity(new Intent(LoginActivity.this, FormDetails.class));
        finish();
    }

    @Override
    public void onError(String msg, Throwable error) {
        System.out.println("Login Response ERROR  - "+msg);
        ToastUtils.shortToast(this, "The user name or password is incorrect.");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


}

