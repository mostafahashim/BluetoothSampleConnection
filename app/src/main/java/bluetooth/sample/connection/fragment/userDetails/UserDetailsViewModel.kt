package bluetooth.sample.connection.fragment.userDetails

import almaqraa.student.domain.URL
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.Preferences
import bluetooth.sample.connection.activity.main.MainViewModel
import bluetooth.sample.connection.adapter.AgeAdapter
import bluetooth.sample.connection.adapter.RecyclerUsersAdapter
import bluetooth.sample.connection.data.model.UserModel
import bluetooth.sample.connection.data.resources.DataState
import bluetooth.sample.connection.domain.remoteService.startGetMethodUsingCoroutines
import bluetooth.sample.connection.domain.remoteService.startPostMethodUsingCoroutines
import bluetooth.sample.connection.domain.remoteService.startPostMethodWithGSONParamsUsingCoroutines
import bluetooth.sample.connection.domain.setup.getDefaultParams
import bluetooth.sample.connection.observer.OnItemClickObserver
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserDetailsViewModel(application: MyApplication) : MainViewModel(application) {

    lateinit var observer: Observer
    var isLoading = MutableLiveData(false)
    var userModel = UserModel()

    var name = MutableLiveData("")
    var nameError = MutableLiveData(false)
    var fatherName = MutableLiveData("")
    var fatherNameError = MutableLiveData(false)
    var age = MutableLiveData("")
    var userID = MutableLiveData("")
    var agePosition = MutableLiveData(0)

    var ageAdapter: AgeAdapter
    var ageModels = ArrayList<String>()

    init {
        ageAdapter = AgeAdapter(ageModels, object : OnItemClickObserver {
            override fun onItemClick(position: Int) {
                agePosition.value = position
                age.value = ageModels[position]
            }
        })
    }

    fun setData() {
        name.value = userModel.name
        fatherName.value = userModel.fatherName
        age.value = userModel.age
        initAgeModels()
    }

    var list = ArrayList<UserModel>()
    fun initAgeModels() {
        ageModels = ArrayList()
        for (i in 1..10) {
            ageModels.add("$i")
            if (i == age.value?.toInt())
                agePosition.value = i - 1
        }
        ageAdapter.setList(ageModels)


        var userModel = UserModel()
        userModel.userId = 5
        userModel.name = "ahmed"
        list.add(userModel)
        userModel = UserModel()
        userModel.userId = 6
        userModel.name = "ahmed 6"
        list.add(userModel)
        userModel = UserModel()
        userModel.userId = 7
        userModel.name = "ahmed 7"
        list.add(userModel)
        var selectedUserID = 6

        var selctedUserPositoin = 0
        for (i in list.indices) {
            if (selectedUserID.compareTo(list[i].userId!!) == 0)
                selctedUserPositoin = i
        }

    }

    fun updateUser() {
        var params = getDefaultParams(application, HashMap())
        params["name"] = name.value ?: ""
        params["fatherName"] = fatherName.value ?: ""
        params["age"] = agePosition.value!! + 1

//        var params = getDefaultParams(application, MultipartBody.Builder())
//        params.addFormDataPart("name", name.value!!)
        var url = URL.insertUser()
//        url = url.plus("?")
//        url = url.plus("name=${name.value ?: ""}&")
//        url = url.plus("fatherName=${fatherName.value ?: ""}&")
//        url = url.plus("age=${agePosition.value!! + 1}")
        viewModelScope.launch {
            startPostMethodWithGSONParamsUsingCoroutines<String>(
                false,
                url,
                params,
            ).collect { response ->
                when (response) {
                    is DataState.Loading -> {
                        isLoading.value = true
                    }

                    is DataState.Error -> {
                        isLoading.value = false
                        Log.e("Connectionerror", response.error)
                        observer.showMessage(response.error)
                    }

                    is DataState.Success -> {
                        isLoading.value = false
                        observer.showMessage(response.response.toString())
//                        if (response.response is Array<UserModel>)
                    }

                    is DataState.Unauthenticated -> {

                    }
                }

            }
        }
    }

    fun onCheckData() {
        var allSuccess = true
        if (name.value?.isEmpty()!!) {
            observer.showMessage("Invalid name")
            nameError.value = true
            allSuccess = false
        }
        if (fatherName.value?.isEmpty()!!) {
            observer.showMessage("Invalid father name")
            fatherNameError.value = true
            allSuccess = false
        }
        if (allSuccess)
            updateUser()

    }

    interface Observer {
        fun changeLanguage()
        fun showMessage(message: String)
    }
}