package bluetooth.sample.connection.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bluetooth.sample.connection.MyApplication

open class MainViewModel(var application: MyApplication) : AndroidViewModel(application) {

    var arrowBackVisibility = MutableLiveData(false)

    init {

    }
}