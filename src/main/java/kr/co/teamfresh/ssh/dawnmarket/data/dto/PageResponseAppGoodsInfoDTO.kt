package kr.co.teamfresh.ssh.dawnmarket.data.dto

data class PageResponseAppGoodsInfoDTO(
    val data: List<AppGoodsInfoDTO> = emptyList(),
    val pagination:Pagination = Pagination()
)
