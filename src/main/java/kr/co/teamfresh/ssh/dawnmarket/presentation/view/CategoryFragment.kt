package kr.co.teamfresh.ssh.dawnmarket.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.databinding.FragmentCategoryBinding
import kr.co.teamfresh.ssh.dawnmarket.presentation.base.BaseFragment
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.Extension.repeatOnStarted
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.GridSpacingItemDecoration
import kr.co.teamfresh.ssh.dawnmarket.presentation.viewmodel.CategoryViewModel

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    val categoryVM by activityViewModels<CategoryViewModel>()
    private lateinit var categoryListAdapter: CategoryListAdapter
    private lateinit var eventListAdapter: EventListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryVM.getCategoryList()
        categoryVM.getEventList()
    }

    override fun setupInit() {
        super.setupInit()
        setUpCategoryList()
        setUpEventList()
        setupToastTouchEvent()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        collectCategoryList()
        collectEventList()
    }

    fun setUpCategoryList(){
        val spanCount = 4
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.horizontal_maincategory_spacing)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_maincategory_spacing)
        val gridLayoutManager = GridLayoutManager(context, spanCount)

        //adapter 초기화
        categoryListAdapter = CategoryListAdapter(requireContext(),emptyList(), object : CategoryListAdapter.onItemClickListener{
            override fun onItemClickListener(dispClasSeq:Int) {
                categoryVM.setDispClasSeqValue(dispClasSeq)
                categoryVM.getPaginationInfo(dispClasSeq,0,0,20,"추천순")
                findNavController().navigate(R.id.action_categoryFragment_to_categoryDetailFragment)
            }
        })

        //간격 동적 조정
        binding.categoryList.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(spanCount = spanCount, horizontalSpacing = horizontalSpacing, verticalSpacing = verticalSpacing))
            adapter = categoryListAdapter
        }

    }

    fun setUpEventList(){
        val spanCount = 5
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.horizontal_eventcategory_spacing)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_eventcategory_spacing)
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        eventListAdapter = EventListAdapter(requireContext(), emptyList())

        binding.eventList.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(spanCount=spanCount, horizontalSpacing = horizontalSpacing, verticalSpacing=verticalSpacing))
            adapter = eventListAdapter
        }
    }

    private fun collectCategoryList(){
        repeatOnStarted {
            categoryVM.categoryList.collectLatest {response->
                categoryListAdapter.updateList(response)
            }
        }
    }

    private fun collectEventList(){
        Log.d("EventList","setupeventlist2")
        repeatOnStarted {
            categoryVM.eventList.collectLatest { response->
                eventListAdapter.updateList(response)
            }
        }
    }

    private fun setupToastTouchEvent(){
        binding.logintext.setOnClickListener{
            //setupToastMessage()
            Toast.makeText(requireContext(),"개발 예정",Toast.LENGTH_SHORT).show()
            Log.d("ttag","ttag")
        }
        binding.alarmBtn.setOnClickListener{
            setupToastMessage()
        }
        binding.settingBtn.setOnClickListener{
            setupToastMessage()
        }
    }

    private fun setupToastMessage(){
        Toast.makeText(context,"개발 예정",Toast.LENGTH_SHORT).show()
    }

}