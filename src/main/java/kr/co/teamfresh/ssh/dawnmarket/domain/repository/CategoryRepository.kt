package kr.co.teamfresh.ssh.dawnmarket.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.Pagination
import kr.co.teamfresh.ssh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import retrofit2.Response

interface CategoryRepository {
    suspend fun getCategoryList(): Flow<Response<ListResultAppDispClasInfoDTO>>
    suspend fun getEventList():Flow<Response<ListResultAppMainQuickMenuDTO>>
    suspend fun getSubCategoryList(dispClasSeq:Int):Flow<Response<SingleResultAppDispClasInfoBySubDispClasInfoDTO>>
    suspend fun getProductList(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String):Flow<PagingData<AppGoodsInfoDTO>>
    suspend fun getPaginationInfo(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String):Flow<Pagination>
}