package com.momentous.movies_app.base

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.momentous.movies_app.R
import com.momentous.movies_app.utils.App
import com.momentous.movies_app.utils.Extensions.toast
import com.momentous.movies_app.utils.ProgressDialogUtil
import java.util.*


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    protected var showToolbar = MutableLiveData<Boolean>()

    init {
        showToolbar.value = true
    }

    protected open fun setContentView(
        activity: AppCompatActivity?,
        layoutId: Int
    ) {

        binding = DataBindingUtil.setContentView(activity!!, layoutId)

        binding.lifecycleOwner = this

    }


    override fun onResume() {
        super.onResume()

        val window = window

        App.networkStatus.observe(this, {

            if (!it.isConnected)
                showSnackBar("No internet connection")


        })


        if (this.getToolbarInstance() != null)

            this.getToolbarInstance()?.let { this.initView(it) }
    }


    protected open fun showSnackBar(message: String) {
        val parentLayout: View = findViewById(android.R.id.content)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setActionTextColor(resources.getColor(R.color.text_blue, theme))
                .show()
        }
    }

    protected open fun showSnackBar(
        message: String,
        actionText: String,
        listener: View.OnClickListener
    ) {
        val parentLayout: View = findViewById(android.R.id.content)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setAction(actionText, listener)
                .setActionTextColor(resources.getColor(R.color.text_blue, theme))
                .show()
        }
    }

    private fun initView(toolbar: Toolbar) {


        // Toolbar setup
        setSupportActionBar(toolbar)   // Replaces the 'ActionBar' with the 'Toolbar'

        supportActionBar?.apply {
            // displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;

            if (showToolbar.value == true) {
                // show back button on toolbar
                // on back button press, it will navigate to parent activity
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_navigation_back);


            } else {
                supportActionBar?.apply {
                    // show back button on toolbar
                    // on back button press, it will navigate to parent activity
                    setDisplayHomeAsUpEnabled(false)
                    setHomeButtonEnabled(false); // disable the button


                }


                toolbar.navigationIcon = null;
            }

        }

    }


    abstract fun getToolbarInstance(): Toolbar?

    protected fun showProgress() {
        ProgressDialogUtil.showProgress(this)
    }

    protected fun hideProgress() {
        ProgressDialogUtil.hideProgress()
    }

    protected fun showToast(message: String) {
        toast(message)
    }

    protected fun transparentStatusBar() {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

    }


    protected open fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
