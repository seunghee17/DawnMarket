package kr.co.teamfresh.ssh.dawnmarket.data.dto

data class AppDispClasInfoBySubDispClasInfoDTO(
    val appSubDispClasInfoList: List<AppSubDispClasInfo> = emptyList(),
    val dispClasNm: String = ""
)