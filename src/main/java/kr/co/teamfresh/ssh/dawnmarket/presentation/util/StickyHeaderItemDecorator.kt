package kr.co.teamfresh.ssh.dawnmarket.presentation.util

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.presentation.view.ProductListAdapter

//class StickyHeaderItemDecorator {
//    private lateinit var listener: StickyHeaderInterface
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter:ProductListAdapter
//    private val currentHeaderViewMap: MutableMap<Int, Boolean> by lazy{ mutableMapOf() }
//    private var stickyHeaderContainer:LinearLayout? = null
//
//    fun attachRecyclerView(
//        listener:StickyHeaderInterface,
//        recyclerView: RecyclerView,
//        adapter: ProductListAdapter
//    ){
//        this.listener = listener
//        this.recyclerView = recyclerView
//        this.adapter = adapter
//        initContainer()
//        clearHeaderViews()
//        refreshHeader()
//    }
//
//    private fun initContainer(){
//        stickyHeaderContainer = (recyclerView.parent as? ViewGroup)?.findViewById(R.id.stickyHeader)
//        if (stickyHeaderContainer == null) {
//            val linearLayout = LinearLayout(recyclerView.context).apply {
//                orientation = LinearLayout.VERTICAL
//                visibility = View.VISIBLE
//            }
//            val params = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            stickyHeaderContainer = linearLayout
//            (recyclerView.parent as? ViewGroup)?.addView(stickyHeaderContainer, params)
//        }
//        recyclerView.addOnScrollListener(onScrollChangeListener)
//    }
//
//    private fun clearHeaderViews(){
//        currentHeaderViewMap.clear()
//        stickyHeaderContainer?.removeAllViews()
//    }
//
//    private fun addHeaderViewFromPosition(position:Int){
//        if(currentHeaderViewMap[position] == true) return
//        stickyHeaderContainer?.let{
//            val vh = adapter.createViewHolder(it,adapter.getItemViewType(position))
//            adapter.bindViewHolder(vh,position)
//            val view = vh.itemView
//            view.tag = position
//            it.addView(
//                view,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            currentHeaderViewMap[position] = true
//            recyclerView.post{it.requestLayout()}
//        }
//    }
//
//    private fun removeHeaderViewFromPosition(position:Int){
//        if(currentHeaderViewMap[position] != true) return
//        val view = stickyHeaderContainer?.findViewWithTag<View>(position)
//        view?.let{
//            stickyHeaderContainer?.removeView(it)
//        }
//        currentHeaderViewMap[position] = false
//    }
//
//    private fun drawHeaders(){
//        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager?
//        var topChildPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0
//        var i =0
//        while(i <= topChildPosition){
//            if(i == RecyclerView.NO_POSITION) break
//            if(listener.isHeader(i)){
//                removeExistingHeaders(i)
//                addHeaderViewFromPosition(i)
//                topChildPosition++
//            }
//            i++
//        }
//    }
//
//    private fun removeExistingHeaders(i:Int){
//        currentHeaderViewMap.forEach{(key,value) ->
//            if((!listener.isHeader(key) || key<i) && value){
//                removeHeaderViewFromPosition(key)
//            }
//        }
//    }
//
//    private fun removeInvalidHeaders(){
//        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager?
//        val topChildPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0
//        currentHeaderViewMap.forEach{(key,value) ->
//            if((!listener.isHeader(key) || key > topChildPosition) && value){
//                removeHeaderViewFromPosition(key)
//            }
//        }
//    }
//
//    private val onScrollChangeListener:RecyclerView.OnScrollListener=
//        object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx:Int, dy:Int){
//                super.onScrolled(recyclerView, dx, dy)
//                refreshHeader()
//            }
//        }
//
//    private fun refreshHeader(){
//        recyclerView.post{
//            removeInvalidHeaders()
//            drawHeaders()
//        }
//    }
//
//    fun clearReferences(){
//        recyclerView.removeOnScrollListener(onScrollChangeListener)
//    }
//
//
//    interface StickyHeaderInterface{
//        fun isHeader(itemPosition:Int):Boolean
//    }
//
