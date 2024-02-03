package bluetooth.sample.connection

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        Preferences.initializePreferences(context)
    }

    override fun attachBaseContext(base: Context) {
        context = this
        context = LocaleHelper.updateLocale(base)
        super.attachBaseContext(context)
    }
}