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
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppMainQuickMenuDTO
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.Extension.toFullImageUrl

class EventListAdapter(
    private val context: Context,
    private var items: List<AppMainQuickMenuDTO>
    ): RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val eventImg:ImageView = itemView.findViewById(R.id.event_img)
        val eventName:TextView = itemView.findViewById(R.id.event_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item,parent,false)
        return EventListAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= items[position]

        holder.eventName.text = item.quickMenuNm
        val url = item.quickMenuImgPath.toFullImageUrl()

        Glide.with(holder.eventImg.context)
            .load(url)
            .into(holder.eventImg)
    }

    fun updateList(newData: List<AppMainQuickMenuDTO>){
        items = newData
        notifyDataSetChanged()
    }
}