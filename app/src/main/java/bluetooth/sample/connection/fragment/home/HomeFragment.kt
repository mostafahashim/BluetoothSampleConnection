package bluetooth.sample.connection.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.R
import bluetooth.sample.connection.activity.main.MainActivity
import bluetooth.sample.connection.databinding.FragmentHomeBinding
import bluetooth.sample.connection.fragment.userDetails.UserDetailsFragment
import bluetooth.sample.connection.observer.OnAskUserAction
import bluetooth.sample.connection.sub.BottomSheetInfoFragment
import bluetooth.sample.connection.sub.PopupDialogAskUserAction

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

    override fun openPopupDialog() {
        val popupDialogAskUserAction = PopupDialogAskUserAction()
        val bundle = Bundle()
        bundle.putString("title", "Popup dialog")
        bundle.putString("body", "Example for Popup  dialog")
        bundle.putString("negativeButtonText", "Cancel")
        bundle.putString("positiveButtonText", "OK")

        bundle.putBoolean("isShowTitle", true)
        bundle.putBoolean("isShowNegativeButton", true)
        bundle.putBoolean("isShowPositiveButton", true)
        bundle.putBoolean("isShowTitle", true)
        popupDialogAskUserAction.arguments = bundle
        popupDialogAskUserAction.onAskUserAction = object : OnAskUserAction {
            override fun onPositiveAction() {
                Toast.makeText(activity, "Ok Clicked", Toast.LENGTH_LONG).show()
            }

            override fun onNegativeAction() {
                Toast.makeText(activity, "Cancel Clicked", Toast.LENGTH_LONG).show()
            }

        }
        popupDialogAskUserAction.show(
            requireActivity().supportFragmentManager,
            "PopupDialogAskUserAction"
        )
    }

    override fun openBottomSheetDialog() {
        val bottomSheetInfoFragment = BottomSheetInfoFragment()
        val bundle = Bundle()
        bundle.putString("title", "Bottom sheet dialog")
        bundle.putString("body", "Example for bottom sheet dialog")
        bottomSheetInfoFragment.arguments = bundle
        bottomSheetInfoFragment.show(
            requireActivity().supportFragmentManager,
            "BottomSheetInfoFragment"
        )
    }

    override fun openUserDetails(position: Int) {
        val userDetailsFragment = UserDetailsFragment()
        val userModel = binding.viewModel?.userModels!![position]
        val bundle = Bundle()
        bundle.putSerializable("UserModel", userModel)
        userDetailsFragment.arguments = bundle
        activity!!.replaceCurrentFragment(
            activity = activity!!,
            container = R.id.frameLayout_main_Activity,
            tag = "UserDetailsFragment",
            targetFragment = userDetailsFragment,
            addToBackStack = true,
            duplicateIfInBackStack = false,
            animate = false
        )
    }

}