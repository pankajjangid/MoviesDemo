package com.momentous.movies_app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject

class Utils {

    companion object {


        fun checkNull(param: String?): String {
            return if (param.isNullOrEmpty()) {
                ""
            } else {
                param
            }
        }

        fun jsonObjectToString(jsonObject: JsonObject?): String {

            return Gson().toJson(jsonObject)
        }

        fun getResponseCode(jsonObject: JsonObject): Int {

            return jsonObject.get("code").asInt
        }

        fun getResponseMessage(jsonObject: JsonObject?): String {

            return if (jsonObject != null)
                jsonObject.get("message").asString
            else
                ""
        }

        fun toast(message: String) {

            Toast.makeText(App.application, message, Toast.LENGTH_SHORT).show()
        }

        fun hideSoftKeyboard(act: Activity) {
            try {
                if (act.currentFocus != null) {
                    val inputMethodManager =
                        act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        act.currentFocus!!.windowToken,
                        0
                    )
                }
            } catch (e: Exception) {
            }
        }

        fun showKeyboard(context: Context, view: View) {
            try {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun dpToPx(dp: Int, activity: Activity): Int {
            val displayMetrics = activity.resources.displayMetrics
            return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }


        /**
         * @param mCurrentActivity
         * @param mTargetActivity
         */
        fun startNewActivity(
            mCurrentActivity: Activity,
            mTargetActivity: Class<*>,
            clearTask: Boolean = false
        ) {

            val mIntent = Intent(mCurrentActivity, mTargetActivity)

            if (clearTask)
                mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            mCurrentActivity.startActivity(mIntent)
            //    mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

        }


        /**
         * @param activity
         */
        fun onBackPressedAnimation(
            activity: Activity,
            mTargetActivity: Class<*>,
            clearTask: Boolean
        ) {

            val mIntent = Intent(activity, mTargetActivity)

            if (clearTask)
                mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            activity.startActivity(mIntent)
            //   activity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave)


        }


        fun startNewActivity(
            mCurrentActivity: Activity,
            mTargetActivity: Class<*>,
            KEY: String,
            string: String,
            clearTask: Boolean
        ) {

            val mIntent = Intent(mCurrentActivity, mTargetActivity)

            if (clearTask)
                mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


            mIntent.putExtra(KEY, string)

            mCurrentActivity.startActivity(mIntent)
            //  mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

        }


    }
}