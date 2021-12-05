package com.kseyko.veriimkb.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kseyko.veriimkb.data.model.handshake.HandshakeRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.data.model.stock.StockListRequest
import com.kseyko.veriimkb.data.model.stock.StockListResponse
import com.kseyko.veriimkb.data.repository.ImkbRepository
import com.kseyko.veriimkb.ui.base.BaseViewModel
import com.kseyko.veriimkb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val imkbRepository: ImkbRepository) :
    BaseViewModel() {

    private var _imkbListLiveData = MutableLiveData<Resource<StockListResponse>>()
    var imkbListLiveData: LiveData<Resource<StockListResponse>> = _imkbListLiveData

    private var _authLiveData = MutableLiveData<Resource<HandshakeResponse>>()
    val authLiveData: LiveData<Resource<HandshakeResponse>> = _authLiveData

    fun submitAuth(handshakeRequest: HandshakeRequest) {
        viewModelScope.launch {
            imkbRepository.getHandShake(handshakeRequest).onStart {
                _authLiveData.postValue(Resource.Loading())
            }.catch { err ->
                _authLiveData.postValue(Resource.Error(err))
                Log.e("", err.message.toString())
            }.collect {
                _authLiveData.postValue(it)

            }
        }
    }

    fun submitList(XVPAuthorization: String, stockListRequest: StockListRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            imkbRepository.getImkbList(stockListRequest, XVPAuthorization).onStart {
                _imkbListLiveData.postValue(Resource.Loading())
            }.catch { err ->
                _imkbListLiveData.postValue(Resource.Error(err))
            }.collect {
                _imkbListLiveData.postValue(it)
            }
        }
    }
}