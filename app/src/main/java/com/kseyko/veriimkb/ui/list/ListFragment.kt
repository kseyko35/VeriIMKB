package com.kseyko.veriimkb.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.data.model.stock.Stock
import com.kseyko.veriimkb.data.model.stock.StockListRequest
import com.kseyko.veriimkb.databinding.FragmentListBinding
import com.kseyko.veriimkb.helper.Information
import com.kseyko.veriimkb.ui.adapter.StockListAdapter
import com.kseyko.veriimkb.ui.adapter.StockListener
import com.kseyko.veriimkb.ui.base.BaseViewModelFragment
import com.kseyko.veriimkb.utils.AESFunction.encrypt
import com.kseyko.veriimkb.utils.DataStoreHelper
import com.kseyko.veriimkb.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : BaseViewModelFragment<FragmentListBinding, ListViewModel>(), StockListener {

    private val args by navArgs<ListFragmentArgs>()
    override val viewModel: ListViewModel by viewModels()
    private lateinit var handshakeResponse: HandshakeResponse

    private lateinit var stockAdapter: StockListAdapter

    override fun getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    override fun onPreInit(savedInstanceState: Bundle?) {
        super.onPreInit(savedInstanceState)
        if (savedInstanceState == null) {
            lifecycleScope.launchWhenStarted {
                fetchAuth()

            }
        }
    }

    override fun onInitView() {
        super.onInitView()
//        binding.apply {
//            stockListViewModel = this@ListFragment.viewModel
//            lifecycleOwner = this@ListFragment
//        }
        binding.stockRecycleView.setHasFixedSize(true)
        stockAdapter = StockListAdapter(this)
//        fetchAuth()

    }

    private fun fetchList() {
        handshakeResponse.let {
            viewModel.submitList(
                handshakeResponse.authorization,
                StockListRequest(
                    encrypt(
                        args.period,
                        handshakeResponse.aesKey,
                        handshakeResponse.aesIV
                    )
                )
            )
        }

    }

    private fun fetchAuth() {
        viewModel.submitAuth(Information.getAuthInfo(requireContext()))
    }

    override fun onInitListener() {
        super.onInitListener()
        binding.stockSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                stockAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    stockAdapter.filter.filter(newText)
                }

                return false
            }

        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.stockSearch.setQuery("", false)
            binding.stockSearch.clearFocus()
            fetchList()
        }
    }


    override fun onObserverData() {
        super.onObserverData()
        viewModel.authLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.status?.isSuccess!!) {
                        handshakeResponse = it.data
                        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                            DataStoreHelper(requireContext()).dsSave(
                                "handshakeResponse",
                                it.data
                            )
                        }
                        fetchList()
                    } else {
                        fetchAuth()
                    }
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(
                        requireContext(),
                        it.data?.status?.error?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModel.imkbListLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    hideLoading()
                    it.data?.let { listResponse ->
                        if (listResponse.status.isSuccess) {
                            stockAdapter.setList(
                                listResponse.stocks,
                                handshakeResponse.aesKey,
                                handshakeResponse.aesIV
                            )
//                            stockAdapter =
//                                StockListAdapter(listResponse.stocks as ArrayList<Stock>, this)
                            binding.stockRecycleView.adapter = stockAdapter
                        }
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    hideLoading()
                    Toast.makeText(
                        requireContext(),
                        it.data?.status?.error?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onStockClick(stock: Stock) {
        val action = ListFragmentDirections.actionNavListToNavDetail(stock.id.toString())
        binding.root.findNavController().navigate(action)
    }


}