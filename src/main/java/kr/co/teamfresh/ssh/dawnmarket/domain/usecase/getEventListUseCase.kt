package kr.co.teamfresh.ssh.dawnmarket.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.data.dto.ListResultAppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.domain.repository.CategoryRepository
import retrofit2.Response
import javax.inject.Inject

class getEventListUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Response<ListResultAppMainQuickMenuDTO>> {
        return categoryRepository.getEventList()
    }
}