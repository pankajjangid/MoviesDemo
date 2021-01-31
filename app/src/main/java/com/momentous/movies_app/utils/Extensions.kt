package com.momentous.movies_app.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

object Extensions {

    fun View.show(){

        this.visibility = View.VISIBLE
    }

    fun View.hide(){

        this.visibility=View.GONE
    }

    fun Context.toast(message:String){

        Toast.makeText(this, message    , Toast.LENGTH_SHORT).show()
    }

    fun Context.toastLong(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun Context.startNewActivity(aClass: Class<*>){
        this.startActivity(Intent(this,aClass))
    }
}