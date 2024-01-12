package kr.co.teamfresh.ssh.dawnmarket.data.repositoryimpl

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.Pagination
import kr.co.teamfresh.ssh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.network.ApiService
import kr.co.teamfresh.ssh.dawnmarket.domain.repository.CategoryRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val service: ApiService
):CategoryRepository {
    override suspend fun getCategoryList(): Flow<Response<ListResultAppDispClasInfoDTO>> {
        return flow{
            val response = service.getCategoryList()
            emit(response)
        }
    }
    override suspend fun getEventList():Flow<Response<ListResultAppMainQuickMenuDTO>>{
        return flow{
            val response = service.getEventList()
            emit(response)
        }
    }

    override suspend fun getSubCategoryList(
        dispClasSeq:Int
    ): Flow<Response<SingleResultAppDispClasInfoBySubDispClasInfoDTO>> {
        return flow{
            val response = service.getSubCategoryList(dispClasSeq)
            emit(response)
        }
    }

    override suspend fun getProductList(
        dispClasSeq: Int,
        subDispClasSeq:Int,
        page: Int,
        size: Int,
        searchValue: String
    ): Flow<PagingData<AppGoodsInfoDTO>>{
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {ProductPagingSource(service,dispClasSeq,subDispClasSeq,searchValue)}
        ).flow
    }

    override suspend fun getPaginationInfo(
        dispClasSeq: Int,
        subDispClasSeq:Int,
        page: Int,
        size: Int,
        searchValue: String
    ): Flow<Pagination>{
        return flow{
            val response = service.getProductList(dispClasSeq,subDispClasSeq,page,size,searchValue).body()?.pagination
            if (response != null) {
                emit(response)
            }
        }
    }

}