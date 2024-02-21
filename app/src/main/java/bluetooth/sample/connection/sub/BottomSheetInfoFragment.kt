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
import androidx.fragment.app.DialogFragment
import bluetooth.sample.connection.R
import bluetooth.sample.connection.activity.main.MainActivity
import bluetooth.sample.connection.databinding.BottomSheetInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetInfoFragment : BottomSheetDialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity)
            activity = context
    }

    lateinit var activity: MainActivity
    lateinit var binding: BottomSheetInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_info, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundTopWhiteSheetDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = requireArguments().getString("title")
        var body = requireArguments().getString("body")
        binding.tvTitle.text = title
        binding.tvBody.text = body
    }
}