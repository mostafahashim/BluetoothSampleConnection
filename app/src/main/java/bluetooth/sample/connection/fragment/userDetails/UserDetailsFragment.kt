package bluetooth.sample.connection.fragment.userDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.R
import bluetooth.sample.connection.activity.main.MainActivity
import bluetooth.sample.connection.data.model.UserModel
import bluetooth.sample.connection.databinding.FragmentUserDetailsBinding
import java.util.StringTokenizer


class UserDetailsFragment : Fragment(), UserDetailsViewModel.Observer {

    lateinit var binding: FragmentUserDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_user_details,
            container,
            false
        )
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
                UserDetailsViewModelFactory(activity?.application as MyApplication)
            )[UserDetailsViewModel::class.java]
        binding.viewModel?.observer = this
        binding.lifecycleOwner = this

        binding.viewModel?.userModel = requireArguments().getSerializable("UserModel") as UserModel
        binding.viewModel?.setData()
        initAgeSpinner()
    }

    override fun onResume() {
        super.onResume()
        activity?.hideShowArrowBack(true)
    }

    fun initAgeSpinner() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                activity!!,
                android.R.layout.simple_spinner_item,
                binding.viewModel?.ageModels!!
            )
        adapter.setDropDownViewResource(R.layout.spinner_item_age)
        binding.spinnerAge.adapter = adapter
    }

    override fun changeLanguage() {
        activity?.recreate()
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}