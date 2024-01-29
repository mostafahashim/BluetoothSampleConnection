package bluetooth.sample.connection.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.R
import bluetooth.sample.connection.databinding.ActivityMainBinding
import bluetooth.sample.connection.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(application as MyApplication)
            )[MainViewModel::class.java]
        binding.lifecycleOwner = this
        replaceCurrentFragment(
            activity = this,
            container = R.id.frameLayout_main_Activity,
            tag = "HomeFragment",
            targetFragment = HomeFragment(),
            addToBackStack = true,
            duplicateIfInBackStack = false,
            animate = false
        )
    }


    fun hideShowArrowBack(isShow: Boolean) {
        binding.viewModel?.arrowBackVisibility?.value = isShow
    }

    fun replaceCurrentFragment(
        activity: AppCompatActivity,
        container: Int,
        targetFragment: Fragment,
        tag: String,
        addToBackStack: Boolean,
        duplicateIfInBackStack: Boolean,
        animate: Boolean
    ) {
        if (activity.isFinishing) {
            return
        }

        val manager = activity.supportFragmentManager
        var fragmentPopped = false
        if (!duplicateIfInBackStack)
            fragmentPopped = manager.popBackStackImmediate(tag, 0)
        if (duplicateIfInBackStack || (!fragmentPopped && manager.findFragmentByTag(tag) == null)) {
//        if (!fragmentPopped) {
            val ft = manager.beginTransaction()
//            if (animate) ft.setCustomAnimations(
//                com.util.R.anim.slide_from_right_to_left,
//                com.util.R.anim.slide_in_left,
//                com.util.R.anim.slide_out_left,
//                com.util.R.anim.slide_from_left_to_right
//            )
            ft.replace(container, targetFragment, tag)
            activity.supportFragmentManager.executePendingTransactions()
            if (addToBackStack) {
                ft.addToBackStack(tag)
            }
            ft.commitAllowingStateLoss()
        }
    }

}