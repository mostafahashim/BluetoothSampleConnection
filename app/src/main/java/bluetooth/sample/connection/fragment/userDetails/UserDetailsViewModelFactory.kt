package bluetooth.sample.connection.fragment.userDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bluetooth.sample.connection.MyApplication

class UserDetailsViewModelFactory(
    var application: MyApplication
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(application) as T
    }
}