package kr.co.teamfresh.ssh.dawnmarket.data.repositoryimpl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class ProductPagingSource @Inject constructor(
    private val service: ApiService,
    private val dispClasSeq: Int,
    private val subDispClasSeq:Int,
    private val searchValue:String
):PagingSource<Int, AppGoodsInfoDTO>() {
    override fun getRefreshKey(state: PagingState<Int, AppGoodsInfoDTO>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AppGoodsInfoDTO> {
        return try{
            val currentpage = params.key ?: 0
            val results = service.getProductList(dispClasSeq,subDispClasSeq,currentpage,params.loadSize,searchValue)
            val responseData = results.body()?.data ?: emptyList()
            val prevKey = if(currentpage == 0) null else currentpage-1
            val nextKey = if(responseData.isEmpty()) null else currentpage+1

            LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey=nextKey
            )
        } catch (exception : IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }
}