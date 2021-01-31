package com.momentous.movies_app.networking


import com.momentous.movies_app.R
import com.momentous.movies_app.utils.App
import com.momentous.movies_app.utils.Extensions.toast
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ApiUtils {


    fun handleRetrofitError(e: Throwable) {
        e.printStackTrace()

        if (e is HttpException) {
            val responseBody: ResponseBody = e.response()!!.errorBody()!!
            App.application?.toast(getErrorMessage(responseBody))
        } else if (e is SocketTimeoutException) {

            App.application?.resources?.getString(R.string.msg_socket_time_out)?.let {
                App.application?.toast(
                    it
                )
            }

        } else if (e is IOException) {
            App.application?.resources?.getString(R.string.msg_api_error)?.let {
                App.application?.toast(
                    it
                )
            }
        } else {
            App.application?.resources?.getString(R.string.msg_api_error)?.let {
                App.application?.toast(
                    it
                )
            }
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody): String {
        try {
            val jsonObject = JSONObject(responseBody.string())
            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.message!!
        }
    }

    fun checkResponseStatus(
        statusCode: Int,
        msg: String?,
        showSuccessMessage: Boolean = false
    ): Boolean {

        var message = ""

        if (msg.isNullOrEmpty()) message = "" else message = msg

        when (statusCode) {
            WebServiceUrl.STATUS_SUCCESS -> {
                if (showSuccessMessage)
                    App.application?.toast(message)
                return true
            }
            WebServiceUrl.STATUS_AUTH_FAILED -> {
                handleAuthFailed(message)
                return false
            }
            WebServiceUrl.STATUS_PARAMETER_MISSING -> {
                handleServerError(message)
                return false
            }
            WebServiceUrl.STATUS_SESSION_EXPIRE -> {
                handleAuthFailed(message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_ERROR -> {
                handleServerError(message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_400 -> {
                handleServerError(message)
                return false
            }
            else -> return false

        }

    }/*
    fun checkResponseStatus(
        mActivity: Activity,
        statusCode: Int,
        message: String,
        showSuccessMessage: Boolean
    ): Boolean {

        when (statusCode) {
            WebServiceUrl.STATUS_SUCCESS -> {
                if (showSuccessMessage)
                    App.application?.toast(message)
                return true
            }
            WebServiceUrl.STATUS_AUTH_FAILED -> {
                handleAuthFailed(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_PARAMETER_MISSING -> {
                handleServerError(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SESSION_EXPIRE -> {
                handleAuthFaild(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_ERROR -> {
                handleServerError(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_400 -> {
                handleServerError(mActivity, message)
                return false
            }
            else -> return false

        }

    }*/

    private fun handleServerError(msg: String) {
        App.application?.toast(msg)

    }

    private fun handleAuthFailed(msg: String) {
        if (msg.isNotEmpty())
            App.application?.toast(msg)
        //  (activity as HomeActivity).mViewModel.callApi_UserLogout(activity)


    }


}