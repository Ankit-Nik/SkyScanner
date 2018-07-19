package com.sky.scanner.network;

import com.sky.scanner.model.SearchDetailRequest;

/**
 * Created by karana on 5/11/2017.
 */

public interface ApiMethods {

//    String DATE_FORMATE_SERVER_REQUEST = "dd/MM/yyyy";
//    String TIME_FORMATE_SERVER_REQUEST = "HH:mm";
//    String PREFIX = "webclient.php?";
    String LOGIN_METHOD = "Token";
    String SUBMIT_SCAN_INFO = "api/Account/SaveUser";
    String VillageList_METHOD = "api/SKYApp/GetSKYVillages";
    String SearchList_METHOD      = "api/SkyServeApp/GetSkyBeneficiariesByVillOrTin";
    String UserDetailsById_ = "api/SKYApp/GetSkyBeneficiaryById";
    String PostBenificiary = "api/SkyServeApp/PostSKYSurveyApp";
    String UserDetailsById_New = "api/SkyServeApp/GetSKYSurveyApp";
   // http://skystg.cgstate.gov.in/CGSKY.Api/api/SKYApp/GetSKYSurveyApp?TIN=41221500301120000017500065001
  //  http://skystg.cgstate.gov.in/CGSKY.Api/api/SkyServeApp/GetSKYSurveyApp?TIN=88661500301120000017500064001

    ;;//?Id=403
  // http://localhost/CGSKY.Api/api/SKYApp/GetSkyBeneficiaryById?Id=380
 // public static final String BASE_URL = "http://skystg.cgstate.gov.in/CGSKY.Api/";

}
