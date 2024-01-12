package kr.co.teamfresh.ssh.dawnmarket.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.Pagination
import kr.co.teamfresh.ssh.dawnmarket.domain.repository.CategoryRepository
import javax.inject.Inject

class getProductListUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String): Flow<PagingData<AppGoodsInfoDTO>> {
        return categoryRepository.getProductList(dispClasSeq,subDispClasSeq,page,size,searchValue)
    }

    suspend fun getPaginationInfo(dispClasSeq: Int, subDispClasSeq:Int, page: Int, size: Int, searchValue: String): Flow<Pagination>{
        return categoryRepository.getPaginationInfo(dispClasSeq,subDispClasSeq,page,size,searchValue)
    }
}