package bluetooth.sample.connection.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.R
import bluetooth.sample.connection.activity.main.MainActivity
import bluetooth.sample.connection.activity.main.MainViewModel
import bluetooth.sample.connection.activity.main.MainViewModelFactory
import bluetooth.sample.connection.databinding.FragmentHomeBinding
import java.util.zip.Inflater

class HomeFragment : Fragment(), HomeViewModel.Observer {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    var activity: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            activity = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(activity?.application as MyApplication)
            )[HomeViewModel::class.java]
        binding.viewModel?.observer = this
        binding.lifecycleOwner = this

    }

    override fun onResume() {
        super.onResume()
        activity?.hideShowArrowBack(false)
    }

    override fun changeLanguage() {
        activity?.recreate()
    }

}