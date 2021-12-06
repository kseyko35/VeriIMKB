package com.kseyko.veriimkb.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    private var _binding: VB? = null

    val binding
        get() = _binding
            ?: throw IllegalStateException("Cannot access view in after view destroyed and before view creation")

    private var viewId: Int = -1

    abstract fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): VB

    open fun onObserverData() {}

    open fun onInitView() {}

    open fun onInitListener() {}

    open fun clickHandling() {}

    open fun onPreInit(savedInstanceState: Bundle?) {}

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getDataBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewId = binding.root.id
        onPreInit(savedInstanceState)
        onInitView()
        onObserverData()
        onInitListener()
        clickHandling()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideLoading() {
        (activity as BaseActivity<*>).hideLoading()
    }

    fun showLoading() {
        (activity as BaseActivity<*>).showLoading()
    }
}