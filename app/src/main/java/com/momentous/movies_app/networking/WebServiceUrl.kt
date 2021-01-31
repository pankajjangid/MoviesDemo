package com.momentous.movies_app.networking


object WebServiceUrl {


    const val HIDE_DEBUG_LOGS = false
    const val STATUS_SUCCESS = 200 //Success

    const val STATUS_AUTH_FAILED =
        402 // This error occurs when either "api_access_key" in headers is missing or incorrect.
    const val STATUS_PARAMETER_MISSING =
        403  // This error occurs when any required parameter is missing in request params.
    const val STATUS_SESSION_EXPIRE =
        401

    //This error occurs when login user session has been expired. On this error, call "user_logout" api .
    const val STATUS_SERVER_ERROR =
        500  //This error occurs when node server crashed due to any of reason.
    const val STATUS_SERVER_400 =
        400  //This error occurs when node server crashed due to any of reason.


    /*
        Movies Services
     */
    const val MOVIES = "movies"



}



