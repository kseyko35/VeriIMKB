package com.kseyko.veriimkb.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kseyko.veriimkb.widgets.LoadingDialog


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var loadingProgressBar: LoadingDialog? = null

    lateinit var binding: VB

    abstract fun getViewBinding(): VB

    open fun onObserverData() {}

    open fun onInitView() {}

    open fun onInitListener() {}

    open fun onPreInit(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        onPreInit(savedInstanceState)
        onObserverData()
        onInitView()
        onInitListener()
    }

    fun hideLoading() {
        loadingProgressBar?.dismiss()
    }

    fun showLoading() {
        if (loadingProgressBar == null) {
            loadingProgressBar = LoadingDialog(this)
        }
        loadingProgressBar?.show()
    }
}