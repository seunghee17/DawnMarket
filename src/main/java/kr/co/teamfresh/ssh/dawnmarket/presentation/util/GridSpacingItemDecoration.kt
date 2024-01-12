package kr.co.teamfresh.ssh.dawnmarket.presentation.util

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount:Int,
    private val horizontalSpacing:Int,
    private val verticalSpacing:Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent:RecyclerView,
        state:RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        with(outRect){
            // 첫 번째 행 처리
//            if (position < spanCount) {
//                top = 0
//                left = if (column == 0) 0 else horizontalSpacing / 2
//                right = if (column == spanCount - 1) 0 else horizontalSpacing / 2
//            }
//            // 첫 번째 행을 제외한 나머지 행 처리
//            else {
//                top = verticalSpacing / 2
//                left = if (column == 0) 0 else horizontalSpacing / 2
//                right = if (column == spanCount - 1) 0 else horizontalSpacing / 2
//            }
//            bottom = verticalSpacing / 2
            left = if (column == 0) 0 else horizontalSpacing / 2
            right = if (column == spanCount - 1) 0 else horizontalSpacing / 2

            top = if (position < spanCount) 0 else verticalSpacing / 2
            bottom = verticalSpacing / 2

        }

    }

}