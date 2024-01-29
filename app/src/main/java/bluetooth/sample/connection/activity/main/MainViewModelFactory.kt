package bluetooth.sample.connection.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication

class MainViewModelFactory(
    var application: MyApplication
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}