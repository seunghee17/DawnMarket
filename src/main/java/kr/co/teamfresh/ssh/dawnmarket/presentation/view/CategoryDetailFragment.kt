package kr.co.teamfresh.ssh.dawnmarket.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kr.co.teamfresh.ssh.dawnmarket.R
import kr.co.teamfresh.ssh.dawnmarket.data.dto.AppSubDispClasInfo
import kr.co.teamfresh.ssh.dawnmarket.databinding.FragmentCategoryDetailBinding
import kr.co.teamfresh.ssh.dawnmarket.presentation.base.BaseFragment
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.Extension.repeatOnStarted
import kr.co.teamfresh.ssh.dawnmarket.presentation.util.GridSpacingItemDecoration
import kr.co.teamfresh.ssh.dawnmarket.presentation.viewmodel.CategoryViewModel

@AndroidEntryPoint
class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>(R.layout.fragment_category_detail) {
    val categoryVM by activityViewModels<CategoryViewModel>()
    private lateinit var subCategoryAdapter: SubCategoryListAdapter
    private lateinit var productListAdapter: ProductListAdapter

    data class AliagnView(val layout: LinearLayout, val dotView: View, val textView: TextView)


    private var selectedOption: AliagnView? = null
    private lateinit var options: List<AliagnView>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //모든 레이아웃 상태 초기화
        options = listOf(
            AliagnView(binding.linear1, binding.dot1, binding.alignText1),
            AliagnView(binding.linear2, binding.dot2, binding.alignText2),
            AliagnView(binding.linear3, binding.dot3, binding.alignText3),
            AliagnView(binding.linear4, binding.dot4, binding.alignText4)
        )
        selectOption(options[0])
        setAlignClick()

        binding.viewModel = categoryVM
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
            categoryVM.resetData()
        }

    }

    override fun setupInit() {
        super.setupInit()
        setUpSubCategoryList()
        setUpProductList()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        collectSubCategoryList()
        collectProductList()
    }

    fun setUpSubCategoryList() {
        val gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.subCategorylist.layoutManager = gridLayoutManager
        subCategoryAdapter = SubCategoryListAdapter(
            requireContext(),
            emptyList(),
            object : SubCategoryListAdapter.onItemClickListener {
                override fun onItemClickListener(
                    dispClasSeq: Int,
                    subDispClasSeq: Int,
                    subDispClasNm: String
                ) {
                    if (subDispClasSeq == 0) {
                        categoryVM.getPaginationInfo(
                            dispClasSeq,
                            0,
                            0,
                            20,
                            categoryVM.optionValue.value!!
                        )
                        //categoryVM.getProductList(dispClasSeq,0,0,20,categoryVM.optionValue.value!!)
                        categoryVM.setsubDispClasSeqValue(0)
                        binding.categoryTitle.text =
                            categoryVM.subCategoryList.value.data.dispClasNm
                    } else {
                        categoryVM.getPaginationInfo(
                            dispClasSeq,
                            subDispClasSeq,
                            0,
                            20,
                            categoryVM.optionValue.value!!
                        )
                        categoryVM.setsubDispClasSeqValue(subDispClasSeq)
                        binding.categoryTitle.text = subDispClasNm
                    }
                }
            })
        binding.subCategorylist.adapter = subCategoryAdapter
    }

    fun collectSubCategoryList() {
        repeatOnStarted {
            categoryVM.subCategoryList.collectLatest { response ->
                val dispClasSeq =
                    response.data.appSubDispClasInfoList.firstOrNull()?.prntsDispClasSeq ?: 0
                val headerItem = AppSubDispClasInfo(
                    "", "", 0, dispClasSeq, "상품 전체", true
                )
                val allList = mutableListOf<AppSubDispClasInfo>().apply {
                    add(headerItem)
                    addAll(response.data.appSubDispClasInfoList)
                }
                subCategoryAdapter.updateList(allList)
            }
        }
    }


    fun setUpProductList() {
        val spanCount = 2
        val horizontalSpacing =
            resources.getDimensionPixelSize(R.dimen.horizontal_productlist_spacing)
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_productlist_spacing)
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        productListAdapter = ProductListAdapter(requireContext())
        binding.productList.apply {
            layoutManager = gridLayoutManager
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    horizontalSpacing = horizontalSpacing,
                    verticalSpacing = verticalSpacing
                )
            )
            adapter = productListAdapter
        }
    }

    fun collectProductList() {
        repeatOnStarted {
            categoryVM.productList.collectLatest { pagingData ->
                productListAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setAlignClick() {
        options.forEach { option ->
            option.layout.setOnClickListener {
                selectOption(option)
            }
        }

    }

    private fun selectOption(option: AliagnView) {
        //이전선택 해제
        selectedOption?.let {
            it.dotView.isSelected = false
            it.textView.isSelected = false
        }
        //새 옵션 선택
        option.dotView.isSelected = true
        option.textView.isSelected = true

        categoryVM.setSearchValue(option.textView.text.toString())

        //선택 옵션 정보 업데이트
        selectedOption = option
    }

}
