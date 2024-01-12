package kr.co.teamfresh.ssh.dawnmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.co.teamfresh.ssh.dawnmarket.databinding.ActivityMainBinding
import kr.co.teamfresh.ssh.dawnmarket.presentation.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupInit() {
        initNavigation()
    }

    fun initNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavi.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination,_ ->
            if(destination.id == R.id.categoryFragment || destination.id == R.id.homeFragment || destination.id == R.id.bookmarkFragment
                || destination.id == R.id.mypageFragment || destination.id == R.id.shoppingFragment){
                binding.naviContainer.visibility = View.VISIBLE
                binding.divider.visibility = View.VISIBLE
            }else{
                binding.naviContainer.visibility = View.GONE
                binding.divider.visibility = View.GONE
            }

        }
    }
}