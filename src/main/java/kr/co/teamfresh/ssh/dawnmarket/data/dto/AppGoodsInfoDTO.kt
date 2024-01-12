package kr.co.teamfresh.ssh.dawnmarket.data.dto

data class AppGoodsInfoDTO(
    val goodsNm: String = "",
    val goodsCd: String="",
    val goodsStat: String="",
    val goodsDispYn: String="",
    val taxtnYn: String="",
    val mnrBuyYn: String="",
    val slePrice: Long=0,
    val dcPrice: Long=0,
    val splyPrice: Long=0,
    val goodsGroupCd: String="",
    val goodsGroupNm: String="",
    val delYn: String="",
    val goodsSuplmtImgSeq: Long=0,
    val imgPath: String="",
    val maxBuyPsblCntQty: Long=0,
    val minBuyPsblCntQty: Long=0,
    val goodsNrm: String="",
    val goodsCntQty: Long=0,
    val goodsGroupOptnNm: String?="",
    val goodsGroupOptnSeq: Long?=0,
    val goodsGroupOptnValue: String?=""
)
