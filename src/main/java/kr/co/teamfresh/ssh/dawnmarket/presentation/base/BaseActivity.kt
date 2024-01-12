package kr.co.teamfresh.ssh.dawnmarket.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding>(
    @LayoutRes val layoutResId: Int
): AppCompatActivity() {
    protected lateinit var binding:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        setupInit()
        subscribeUi()
    }

    protected open fun setupInit(){}
    protected open fun subscribeUi(){}

    override fun onDestroy() {
        super.onDestroy()
    }
}