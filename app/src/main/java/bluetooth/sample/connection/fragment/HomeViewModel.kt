package bluetooth.sample.connection.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bluetooth.sample.connection.MyApplication
import bluetooth.sample.connection.Preferences
import bluetooth.sample.connection.activity.main.MainViewModel

class HomeViewModel(application: MyApplication) : MainViewModel(application) {

    lateinit var observer: Observer
    var isArabic = MutableLiveData(false)
    var email = MutableLiveData("")

    init {
        isArabic.value = Preferences.getApplicationLocale() == "ar"
    }

    fun onButtonChangeLanguageClicked() {
        Preferences.saveApplicationLocale(if (isArabic.value!!) "ar" else "en")
        observer.changeLanguage()
    }

    interface Observer {
        fun changeLanguage()
    }
}