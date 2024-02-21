package bluetooth.sample.connection.sub

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import bluetooth.sample.connection.R
import bluetooth.sample.connection.activity.main.MainActivity
import bluetooth.sample.connection.databinding.PopupDialogSureBinding
import bluetooth.sample.connection.observer.OnAskUserAction

class PopupDialogAskUserAction : BaseDialogFragment() {

    internal var activity: MainActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            activity = context
        }
    }

    lateinit var binding: PopupDialogSureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity == null) activity = getActivity() as MainActivity?
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.popup_dialog_sure, container, false)
        return binding.root
    }

    lateinit var onAskUserAction: OnAskUserAction

    fun setOnAskUserActionObserver(onAskUserAction: OnAskUserAction) {
        this.onAskUserAction = onAskUserAction
    }

    lateinit internal var dialog: Dialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null)
            activity = getActivity() as MainActivity?
        dialog = Dialog(requireActivity())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog = Dialog(requireActivity(), R.style.FullWidthDialogTheme)
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        setListener()
    }


    var title = ""
    var body = ""
    var negativeButtonText = ""
    var positiveButtonText = ""
    var isShowTitle = false
    var isShowNegativeButton = false
    var isShowPositiveButton = false

    fun initializeViews() {
        getArgumentsData()

        setData()
    }

    fun getArgumentsData() {
        if (arguments != null) {
            title = requireArguments().getString("title", "")
            body = requireArguments().getString("body", "")
            negativeButtonText = requireArguments().getString("negativeButtonText", "")
            positiveButtonText = requireArguments().getString("positiveButtonText", "")
            isShowTitle = requireArguments().getBoolean("isShowTitle", false)
            isShowNegativeButton = requireArguments().getBoolean("isShowNegativeButton", false)
            isShowPositiveButton = requireArguments().getBoolean("isShowPositiveButton", false)
        }
    }

    fun setData() {
        binding.txtviewDialogSureTitle.text = title
        binding.txtviewDialogSureBody.text = body
        binding.btnDialogSureCancel.text = negativeButtonText
        binding.btnDialogSureOk.text = positiveButtonText

        binding.txtviewDialogSureTitle.visibility = if (isShowTitle) View.VISIBLE else View.GONE
        binding.btnDialogSureCancel.visibility =
            if (isShowNegativeButton) View.VISIBLE else View.GONE
        binding.btnDialogSureOk.visibility = if (isShowPositiveButton) View.VISIBLE else View.GONE

    }

    fun chatViaWhatsApp() {
        /*try {
            var number = (requireActivity() as BaseActivity).parentResponseModel!!.whatsapp
            number = number.replace(" ", "").replace("+", "")

            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra(
                "jid",
                PhoneNumberUtils.stripSeparators("2" + number) + "@s.whatsapp.net"
            )
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(
                requireActivity(),
                "ERROR OPEN WhatsApp",
                Toast.LENGTH_LONG
            ).show()
        }*/
    }

    fun setListener() {
        binding.btnDialogSureOk.setOnClickListener {
            if (::onAskUserAction.isInitialized)
                onAskUserAction.onPositiveAction()
            dismissAllowingStateLoss()
        }

        binding.btnDialogSureCancel.setOnClickListener {
            if (::onAskUserAction.isInitialized)
                onAskUserAction.onNegativeAction()
            dismissAllowingStateLoss()
        }

    }

}