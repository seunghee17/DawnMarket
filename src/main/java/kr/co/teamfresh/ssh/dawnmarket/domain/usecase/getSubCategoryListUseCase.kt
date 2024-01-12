package kr.co.teamfresh.ssh.dawnmarket.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.teamfresh.ssh.dawnmarket.data.dto.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.domain.repository.CategoryRepository
import retrofit2.Response
import javax.inject.Inject

class getSubCategoryListUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(dispClasSeq:Int) : Flow<Response<SingleResultAppDispClasInfoBySubDispClasInfoDTO>>{
        return categoryRepository.getSubCategoryList(dispClasSeq)
    }

}