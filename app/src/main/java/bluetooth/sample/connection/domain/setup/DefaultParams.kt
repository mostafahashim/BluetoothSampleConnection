package bluetooth.sample.connection.domain.setup

import bluetooth.sample.connection.BuildConfig
import bluetooth.sample.connection.MyApplication
import okhttp3.MultipartBody

fun getDefaultParams(
    application: MyApplication,
    params: HashMap<String, Any>
): MutableMap<String, Any> {
//    if (application.getDeviceManufacturer().lowercase().contains("HUAWEI".lowercase()))
//        params["device_type"] = "HUAWEI"
//    else
//        params["device_type"] = "ANDROID"
//    var token = Preferences.getUserToken()
//    params["device_token"] = token
//    params["locale"] = Preferences.getApplicationLocale()
    params["version_code"] = BuildConfig.VERSION_CODE.toString()

    return params
}

fun getDefaultParams(
    application: MyApplication,
    builder: MultipartBody.Builder
): MultipartBody.Builder {
    builder.setType(MultipartBody.FORM)
//    var token = Preferences.getUserToken()
//    builder.addFormDataPart("device_token", token)
//    builder.addFormDataPart("locale", Preferences.getApplicationLocale())
//    builder.addFormDataPart("version_code", BuildConfig.VERSION_CODE.toString())
//    if (application.getDeviceManufacturer().lowercase().contains("HUAWEI".lowercase()))
//        builder.addFormDataPart("device_type", "HUAWEI")
//    else
//        builder.addFormDataPart("device_type", "ANDROID")
    builder.addFormDataPart("type", "student")
    builder.addFormDataPart("android", "true")

    return builder
}