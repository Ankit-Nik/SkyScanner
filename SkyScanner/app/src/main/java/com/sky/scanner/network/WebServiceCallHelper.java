package com.sky.scanner.network;


import com.sky.scanner.ModelsNew.SubmitDataNewModel;
import com.sky.scanner.ModelsNew.UserDetailsModel;
import com.sky.scanner.model.LoginRequestDto;
import com.sky.scanner.model.LoginResponseDto;
import com.sky.scanner.model.ScanInfoRequestDto;
import com.sky.scanner.model.ScanInfoResponseDto;
import com.sky.scanner.model.SearchDetailRequest;
import com.sky.scanner.model.SearchDetailResponse;
import com.sky.scanner.model.SearchDetailResponseModel;
import com.sky.scanner.model.SearchTinRequeest;
import com.sky.scanner.model.SearchTinResponse;
import com.sky.scanner.model.SearchVillageResponseDto;
import com.sky.scanner.model.Submit_All_Data_Request;
import com.sky.scanner.model.Submit_All_Data_Response;
import com.sky.scanner.network.retrofit.ApiClient;
import com.sky.scanner.network.retrofit.GenericResponseModel;
import com.sky.scanner.network.retrofit.ObserverCallBack;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class WebServiceCallHelper {

    private final ServiceHelperListener helperListener;
    private ApiClient service;
    WebApiInterface apiService;

    public interface ServiceHelperListener {
        public void onServiceSuccess();

        public void obServiceFailure(String msg);
    }

    public WebServiceCallHelper(ServiceHelperListener helperListener) {
        this.helperListener = helperListener;
        // apiService = new ApiClient(this).createRetrofitClient();
    }


    /**
     * Method to get login user from web  service call.
     *
     * @param myObserver Observer to get the result call backs
     * @param params     {@link LoginRequestDto}
     */
    public static void loginUser(ObserverCallBack myObserver, LoginRequestDto params) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<LoginResponseDto> responseObservable = apiService.loginUser(params.getGrant_type(),params.getUsername(),params.getPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

         myObserver.execute(responseObservable, myObserver);
        //myObserver.getListener().onSuccess(new GenericResponseModel<LoginResponseDto>(), myObserver.getRequestTag());
    }


    public static void village_list(ObserverCallBack myObserver) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<SearchVillageResponseDto> responseObservable = apiService.VillageList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        myObserver.execute(responseObservable, myObserver);
    }



    public static void search_data(ObserverCallBack myObserver, SearchTinRequeest params) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<SearchTinResponse> responseObservable = apiService.GetSkyBeneficiariesByVillOrTin(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        myObserver.execute(responseObservable, myObserver);
        //myObserver.getListener().onSuccess(new GenericResponseModel<ScanInfoResponseDto>(), myObserver.getRequestTag());
    }

    public static void search_tin_detail_data(ObserverCallBack myObserver, SearchDetailRequest params) {
//        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
//        Observable<SearchDetailResponseModel> responseObservable = apiService.UserDetailsById_METHOD(params.getId())
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        myObserver.execute(responseObservable, myObserver);
    }


    public static void search_tin_detail_data_new(ObserverCallBack myObserver, SearchDetailRequest params) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<UserDetailsModel> responseObservable = apiService.UserDetailsById_METHOD_New(params.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        myObserver.execute(responseObservable, myObserver);
        //myObserver.getListener().onSuccess(new GenericResponseModel<ScanInfoResponseDto>(), myObserver.getRequestTag());
    }
  /**
     * Method to call Web service to submit the scan information.
     *
     * @param myObserver Observer to get the result call backs
     * @param params     {@link ScanInfoRequestDto}
     */
    public static void submitScanInfoImage(ObserverCallBack myObserver,SubmitDataNewModel params) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<ResponseBody> responseObservable = apiService.User_Submit_all_data(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

         myObserver.execute(responseObservable, myObserver);
        //myObserver.getListener().onSuccess(new GenericResponseModel<ScanInfoResponseDto>(), myObserver.getRequestTag());
    }


    public static void submitScanInfo(ObserverCallBack myObserver, ScanInfoRequestDto params) {
        WebApiInterface apiService = ApiClient.getClient().create(WebApiInterface.class);
        Observable<ResponseBody> responseObservable = apiService.submitScanInfo(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        myObserver.execute(responseObservable, myObserver);
        //myObserver.getListener().onSuccess(new GenericResponseModel<ScanInfoResponseDto>(), myObserver.getRequestTag());
    }


}