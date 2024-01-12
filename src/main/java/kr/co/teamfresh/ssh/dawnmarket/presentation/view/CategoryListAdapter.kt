package kr.co.teamfresh.ssh.dawnmarket.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppDispClasInfoDTO
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.Extension.toFullImageUrl

class CategoryListAdapter(
    private val context: Context,
    private var items: List<AppDispClasInfoDTO>,
    private val listener:onItemClickListener
): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    interface onItemClickListener{
        fun onItemClickListener(dispClasSeq:Int)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.category_list_img)
        val categoryName: TextView = itemView.findViewById(R.id.category_list_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item,parent,false)
        return CategoryListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.categoryName.text = item.dispClasNm
        val url = item.dispClasImgPath.toFullImageUrl()
        Glide.with(holder.categoryImg.context)
            .load(url)
            .error(R.drawable.ic_bell_big)
            .into(holder.categoryImg)
        holder.itemView.setOnClickListener{
            listener.onItemClickListener(item.dispClasSeq)
        }
    }

    fun updateList(newData: List<AppDispClasInfoDTO>){
        items = newData
        notifyDataSetChanged()
    }
}