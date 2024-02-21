package bluetooth.sample.connection.fragment.home

import almaqraa.student.domain.URL
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.Preferences
import bluetooth.sample.connection.activity.main.MainViewModel
import bluetooth.sample.connection.adapter.RecyclerUsersAdapter
import bluetooth.sample.connection.data.model.UserModel
import bluetooth.sample.connection.data.resources.DataState
import bluetooth.sample.connection.domain.remoteService.startGetMethodUsingCoroutines
import bluetooth.sample.connection.domain.setup.getDefaultParams
import bluetooth.sample.connection.observer.OnItemClickObserver
import kotlinx.coroutines.launch

class HomeViewModel(application: MyApplication) : MainViewModel(application) {

    lateinit var observer: Observer
    var isArabic = MutableLiveData(false)
    var isLoading = MutableLiveData(false)
    var email = MutableLiveData("")
    var recyclerUsersAdapter: RecyclerUsersAdapter
    var userModels = ArrayList<UserModel>()

    init {
        isArabic.value = Preferences.getApplicationLocale() == "ar"
        recyclerUsersAdapter = RecyclerUsersAdapter(userModels, object : OnItemClickObserver {
            override fun onItemClick(position: Int) {
                observer.openUserDetails(position)
            }

        })

        getUsers()
    }

    fun onButtonChangeLanguageClicked() {
        Preferences.saveApplicationLocale(if (isArabic.value!!) "ar" else "en")
        observer.changeLanguage()
    }

    fun getUsers() {
        var params = getDefaultParams(application, HashMap())
        viewModelScope.launch {
            startGetMethodUsingCoroutines<UserModel>(
                false,
                URL.getAllUsersUrl(),
                params,
                true
            ).collect { response ->
                when (response) {
                    is DataState.Loading -> {
                        isLoading.value = true
                    }

                    is DataState.Error -> {
                        isLoading.value = false
                        Log.e("Connectionerror", response.error)
                    }

                    is DataState.Success -> {
                        isLoading.value = false
//                        if (response.response is Array<UserModel>)
                        userModels = response.response as ArrayList<UserModel>
                        recyclerUsersAdapter.setList(userModels)
                    }

                    is DataState.Unauthenticated -> {

                    }
                }

            }
        }
    }

    interface Observer {
        fun changeLanguage()
        fun openPopupDialog()
        fun openBottomSheetDialog()
        fun openUserDetails(position: Int)
    }
}