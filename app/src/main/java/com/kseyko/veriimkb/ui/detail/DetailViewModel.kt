package com.kseyko.veriimkb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.detail.StockDetailResponse
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
class DetailViewModel @Inject constructor(private val repository: ImkbRepository) :
    BaseViewModel() {

    private val _imkbDetailLiveData = MutableLiveData<Resource<StockDetailResponse>>()
    val imkbDetailLiveData: LiveData<Resource<StockDetailResponse>> = _imkbDetailLiveData

    fun submitDetail(XVPAuthorization: String, detailRequest: StockDetailRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getImkbDetail(detailRequest, XVPAuthorization).onStart {
                _imkbDetailLiveData.postValue(Resource.Loading())
            }.catch { err ->
                _imkbDetailLiveData.postValue(Resource.Error(err))
            }.collect {
                _imkbDetailLiveData.postValue(it)
            }
        }
    }
}