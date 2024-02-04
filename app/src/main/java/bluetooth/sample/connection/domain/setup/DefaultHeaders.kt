package bluetooth.sample.connection.domain.setup

import android.os.Build

inline fun getDefaultHeaders(
    isFormData: Boolean
): MutableMap<String, String> {
    var params = HashMap<String, String>()
    if (!isFormData)
        params["Content-Type"] = "application/json"

    params["Accept"] = "application/json"
//    var token = Preferences.getUserToken()
//    params["device-token"] = token

//    params["device-type"] = if (Build.MANUFACTURER.lowercase().contains("HUAWEI".lowercase()))
//        "HUAWEI"
//    else
//        "ANDROID"
//    params["Accept-Language"] = Preferences.getApplicationLocale()
//    params["language"] = Preferences.getApplicationLocale()
//    params["Authorization"] = if (!Preferences.getAPIToken()
//            .isNullOrEmpty()
//    ) "Bearer ${Preferences.getAPIToken()}" else ""
    return params
}
