package kr.co.teamfresh.ssh.dawnmarket.presentation.view

import android.content.Context
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppGoodsInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.Extension.toFullImageUrl
import java.text.DecimalFormat

class ProductListAdapter(
    private val context: Context,
): PagingDataAdapter<AppGoodsInfoDTO,ProductListAdapter.ProductViewHolder>(
    object : DiffUtil.ItemCallback<AppGoodsInfoDTO>(){
        override fun areItemsTheSame(oldItem: AppGoodsInfoDTO, newItem: AppGoodsInfoDTO): Boolean {
            return oldItem.goodsCd == newItem.goodsCd
        }

        override fun areContentsTheSame(
            oldItem: AppGoodsInfoDTO,
            newItem: AppGoodsInfoDTO
        ): Boolean {
            return oldItem == newItem
        }
    }
){

        override fun onBindViewHolder(holder: ProductListAdapter.ProductViewHolder, position: Int) {
            val dec = DecimalFormat("#,###")
            val redColor = ContextCompat.getColor(context, R.color.red)
            val negativeMargin = context.resources.getDimensionPixelSize(R.dimen.horizontal_priceContainer_spacing)
            val params = holder.productOriginalContainer.layoutParams as ViewGroup.MarginLayoutParams
            val item = getItem(position)
            item?.let {
                val discountRate = (item.slePrice - item.dcPrice)/item.slePrice * 100
                holder.productName.text = item.goodsNm

                //img
                val url = item.imgPath.toFullImageUrl()
                Glide.with(holder.productImg.context)
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(4)))
                    .into(holder.productImg)

                //옵션값
                holder.option.text = item.goodsNrm

                //할인 여부 확인후 레이아웃 변경
                if(item.slePrice == item.dcPrice){
                    //할인하지 않는 상품
                    val formatPrice = dec.format(item.slePrice)
                    holder.productPrice.text = formatPrice
                    params.topMargin = negativeMargin
                    holder.productOriginalContainer.layoutParams = params
                }else{
                    holder.productNormalContainer.visibility = View.VISIBLE
                    //할인할때
                    val discountFormat = dec.format(item.slePrice)
                    //할인전 가격
                    holder.productNormalPrice.apply{
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        text = "${discountFormat}"
                    }

                    val discountResult = "${discountRate}%${dec.format(item.dcPrice)}"
                    val spans = SpannableStringBuilder(discountResult)
                    holder.productPrice.text = spans.apply {
                        setSpan(ForegroundColorSpan(redColor),0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                }
                holder.cartBtn.setOnClickListener{
                    Toast.makeText(holder.itemView.context,"${item.goodsNm}이 장바구니에 담겼습니다.",Toast.LENGTH_SHORT).show()
                }
            }

        }

        class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val productName : TextView = itemView.findViewById(R.id.product_name)
            val productImg: ImageView = itemView.findViewById(R.id.product_img)
            var productNormalPrice:TextView = itemView.findViewById(R.id.product_normal_price)
            val productNormalContainer:LinearLayout = itemView.findViewById(R.id.product_normal_container)
            var productOriginalContainer:LinearLayout = itemView.findViewById(R.id.original_price_container)
            var productPrice:TextView = itemView.findViewById(R.id.product_price)
            val option:TextView = itemView.findViewById(R.id.option)
            val cartBtn : FrameLayout= itemView.findViewById(R.id.cart_btn)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ProductListAdapter.ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)
            return ProductListAdapter.ProductViewHolder(view)
        }

    }

