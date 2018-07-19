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
import com.sky.scanner.network.retrofit.GenericResponseModel;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Interface for the web services
 */

public interface WebApiInterface {

    @FormUrlEncoded
    @POST(ApiMethods.LOGIN_METHOD)
    Observable<LoginResponseDto> loginUser(@Field("grant_type") String grantType,@Field("username") String userName,@Field("password") String password);


    @POST(ApiMethods.SUBMIT_SCAN_INFO)
    Observable<ResponseBody> submitScanInfo(@Body ScanInfoRequestDto requestDto);


    @GET(ApiMethods.VillageList_METHOD)
    Observable<SearchVillageResponseDto> VillageList();


    @POST(ApiMethods.SearchList_METHOD)
    Observable<SearchTinResponse> GetSkyBeneficiariesByVillOrTin(@Body SearchTinRequeest requestDto);


    @GET(ApiMethods.UserDetailsById_)
    Observable<SearchDetailResponseModel> UserDetailsById_METHOD(@Query("Id") long Id);

    @POST(ApiMethods.UserDetailsById_New)
    Observable<UserDetailsModel> UserDetailsById_METHOD_New(@Query("TIN") String Id);


    @POST(ApiMethods.PostBenificiary)
    Observable<ResponseBody> User_Submit_all_data(@Body SubmitDataNewModel requestDto);

}
