package com.kseyko.veriimkb.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.databinding.FragmentDetailBinding
import com.kseyko.veriimkb.helper.Information.getDetailInfo
import com.kseyko.veriimkb.ui.base.BaseViewModelFragment
import com.kseyko.veriimkb.utils.AESFunction
import com.kseyko.veriimkb.utils.DataStoreHelper
import com.kseyko.veriimkb.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseViewModelFragment<FragmentDetailBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var handshakeResponse: HandshakeResponse
    private lateinit var stockListRequest: StockDetailRequest
    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun onPreInit(savedInstanceState: Bundle?) {
        super.onPreInit(savedInstanceState)
        if (savedInstanceState == null) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                handshakeResponse = DataStoreHelper(requireContext()).dsGet(
                    "handshakeResponse",
                    HandshakeResponse::class.java
                )
                handshakeResponse.let {
                    stockListRequest = getDetailInfo(
                        args.id.toString(),
                        it.aesKey,
                        it.aesIV
                    )
                }
                fetchList()
            }
        }
    }

    override fun onInitView() {
        super.onInitView()
        binding.apply {
            stockDetailViewModel = viewModel
            lifecycleOwner = this@DetailFragment
        }


    }

    private fun fetchList() {
        viewModel.submitDetail(handshakeResponse.authorization, stockListRequest)
    }

    override fun onInitListener() {
        super.onInitListener()
        binding.swipeRefreshLayout.setOnRefreshListener { fetchList() }
    }

    override fun onObserverData() {
        super.onObserverData()
        val entries = ArrayList<Entry>()
        viewModel.imkbDetailLiveData.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    it.data.let {
                        try {
                            it?.symbol = AESFunction.decrypt(
                                it!!.symbol,
                                handshakeResponse.aesKey,
                                handshakeResponse.aesIV
                            )
                            entries.clear()
                            it.graphicData.forEach { data ->
                                entries.add(Entry(data.day.toFloat(), data.value.toFloat()))
                            }
                        } catch (e: Exception) {
                            //TODO
                        }
                    }


                    val vl = LineDataSet(entries, "Price")
                    vl.setDrawValues(true)
                    vl.setDrawFilled(true)
                    vl.lineWidth = 3f
                    binding.chart.apply {
                        xAxis.labelRotationAngle = 0f
                        binding.chart.data = LineData(vl)
                        axisRight.isEnabled = false
                        setTouchEnabled(true)
                        setPinchZoom(true)
                        description.text = ""
                        setNoDataText("Grafik datası alınamadı")
                        animateX(1800, Easing.EaseInExpo)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(
                        requireContext(), it.data?.status?.error?.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}