package kr.co.teamfresh.ssh.dawnmarket.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppSubDispClasInfo

class SubCategoryListAdapter(
    private val context: Context,
    private var items: List<AppSubDispClasInfo>,
    private val listener: onItemClickListener
):RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder>() {
    var selectedPosition = 0
    interface onItemClickListener {
        fun onItemClickListener(dispClasSeq:Int,subDispClasSeq:Int,subDispClasNm:String)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val subCategoryName: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subcategory_list_item,parent,false)
        return SubCategoryListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryListAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val isSelected = position == selectedPosition
        holder.subCategoryName.isSelected = isSelected

        //계산해서 중복 선 빼기
        holder.subCategoryName.apply{
            background = ContextCompat.getDrawable(context, R.drawable.bg_subcategory_stroke)
            text = item.subDispClasNm
        }

        holder.itemView.setOnClickListener{
            val previousSelectedPosition = selectedPosition
            selectedPosition = position

            if(item.isEmptyItem){
                listener.onItemClickListener(item.prntsDispClasSeq,0,item.subDispClasNm)
            }else{
                listener.onItemClickListener(item.prntsDispClasSeq,item.dispClasSeq,item.subDispClasNm)
            }

            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun updateList(newData: List<AppSubDispClasInfo>){
        items = newData.toMutableList()
        val remainder = items.size %3
        //빈 레이아웃 그려 grid 열수 맞추기 위함
        if(remainder != 0){
            val emptyItem = AppSubDispClasInfo("","",0,0,"",isEmptyItem = false)
            for(i in 1..(3-remainder)){
                (items as MutableList<AppSubDispClasInfo>).add(emptyItem)
            }
        }
        notifyDataSetChanged()
    }
}