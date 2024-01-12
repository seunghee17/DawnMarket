package kr.co.teamfresh.ssh.dawnmarket.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.Pagination
import kr.co.teamfresh.ssh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.domain.usecase.getCategoryListUseCase
import kr.co.teamfresh.ssh.dawnmarket.domain.usecase.getEventListUseCase
import kr.co.teamfresh.ssh.dawnmarket.domain.usecase.getProductListUseCase
import kr.co.teamfresh.ssh.dawnmarket.domain.usecase.getSubCategoryListUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getcategoryListUsecase: getCategoryListUseCase,
    private val geteventListUseCase : getEventListUseCase,
    private val getsubCategoryListUseCase: getSubCategoryListUseCase,
    private val getproductListUseCase: getProductListUseCase
): ViewModel() {

    private val _categoryList = MutableStateFlow<List<AppDispClasInfoDTO>>(emptyList())
    val categoryList = _categoryList.asStateFlow()

    private val _eventList = MutableStateFlow<List<AppMainQuickMenuDTO>>(emptyList())
    val eventList = _eventList.asStateFlow()

    private val _subCategoryList = MutableStateFlow(SingleResultAppDispClasInfoBySubDispClasInfoDTO())
    val subCategoryList = _subCategoryList.asStateFlow()

    private val _productList = MutableStateFlow<PagingData<AppGoodsInfoDTO>>(PagingData.empty())
    val productList = _productList.asStateFlow()

    private val _paginationInfo = MutableStateFlow(Pagination())
    val paginationInfo = _paginationInfo.asStateFlow()

    //소분류
    private val _optionValue = MutableLiveData<String>("추천순")
    val optionValue:LiveData<String> = _optionValue

    //대분류
    private val _dispClasSeqValue = MutableLiveData<Int>(0)
    val dispClasSeqValue:LiveData<Int> = _dispClasSeqValue

    //중분류
    private val _subDispClasSeq = MutableLiveData<Int>(0)
    val subDispClasSeq:LiveData<Int> = _subDispClasSeq

    init {
        dispClasSeqValue.observeForever{value->
            getSubCategoryList(value)
            getProductList(value,0,0,20, optionValue.value!!)
        }
        subDispClasSeq.observeForever{value->
            getProductList(dispClasSeqValue.value!!, value,0,20,optionValue.value!!)
        }

        optionValue.observeForever{value->
            getProductList(dispClasSeqValue.value!!, subDispClasSeq.value!!,0,20,value)
        }
    }

    //대분류 변경하는 함수
    fun setDispClasSeqValue(dispClasSeqValue:Int){
        _dispClasSeqValue.value = dispClasSeqValue
    }

    //중분류 변경하는 함수
    fun setsubDispClasSeqValue(subDispClasSeq:Int){
        _subDispClasSeq.value = subDispClasSeq
    }

    //정렬순을 변경하는 함수
    fun setSearchValue(value:String){
        _optionValue.value = value
    }


    fun getCategoryList(){
        viewModelScope.launch {
            getcategoryListUsecase().collect{response->
                if(response.isSuccessful){
                    response.body()?.let { result->
                        _categoryList.value = result.data
                    }
                } else{
                    _categoryList.value = emptyList()
                }
            }
        }
    }

    fun getEventList(){
        viewModelScope.launch {
            geteventListUseCase().collect{response->
                if(response.isSuccessful){
                    response.body()?.let{result->
                        Log.d("EventList","success")
                        _eventList.value = result.data
                    }
                } else{
                    Log.d("EventList","fail")
                    _eventList.value = emptyList()
                }
            }
        }
    }

    fun getSubCategoryList(dispClasSeq:Int){
        viewModelScope.launch {
            getsubCategoryListUseCase(dispClasSeq).collect{response->
                if(response.isSuccessful){
                    response.body()?.let {result->
                        Log.d("taggggggg","${result}")
                        _subCategoryList.value = result
                    }
                } else{
                    _subCategoryList.value = SingleResultAppDispClasInfoBySubDispClasInfoDTO()
                }
            }
        }
    }

    fun getProductList(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String){
        viewModelScope.launch {
            getproductListUseCase(dispClasSeq,subDispClasSeq,page,size,searchValue).collect{response->
                Log.d("viewModel","${_productList.value}")
                _productList.value = response
            }
        }
    }

    fun getPaginationInfo(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String){
        viewModelScope.launch {
            getproductListUseCase.getPaginationInfo(dispClasSeq,subDispClasSeq,page,size,searchValue).collect{response->
                Log.d("viewModell","${response}")
                _paginationInfo.value = response
            }
        }
    }

    fun resetData(){
        _productList.value = PagingData.empty()
        _optionValue.value = ""
        _subDispClasSeq.value = 0
        _dispClasSeqValue.value = 0
    }



}