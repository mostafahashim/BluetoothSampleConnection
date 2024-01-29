package bluetooth.sample.connection

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}