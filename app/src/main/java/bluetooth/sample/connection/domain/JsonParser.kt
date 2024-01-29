package almaqraa.student.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseFromJson(response: String?): T? {
    return try {
        val gson = Gson()
        val type = object : TypeToken<T>() {

        }.type
        gson.fromJson(response, type)
    } catch (e1: Exception) {
        e1.printStackTrace()
        null
    }
}

inline fun <T : Any> parseToJson(
    model: T
): String? {
    return try {
        var bodyjson = ""
        val gson = Gson()
        bodyjson = gson.toJson(model)
        bodyjson
    } catch (e1: Exception) {
        e1.printStackTrace()
        null
    }
}