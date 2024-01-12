package kr.co.teamfresh.ssh.dawnmarket.data.network

import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("disp-clas-infos/disp-clas-nm")
    suspend fun getCategoryList() : Response<ListResultAppDispClasInfoDTO>

    @GET("main-infos/quick-menu")
    suspend fun getEventList(): Response<ListResultAppMainQuickMenuDTO>

    @GET("disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas-infos")
    suspend fun getSubCategoryList(
        @Path("dispClasSeq") dispClasSeq:Int
    ): Response<SingleResultAppDispClasInfoBySubDispClasInfoDTO>

    @GET("disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas/{subDispClasSeq}/goods-infos")
    suspend fun getProductList(
        @Path("dispClasSeq") dispClasSeq: Int,
        @Path("subDispClasSeq") subDispClasSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("searchValue") searchValue: String
    ):Response<PageResponseAppGoodsInfoDTO>

}