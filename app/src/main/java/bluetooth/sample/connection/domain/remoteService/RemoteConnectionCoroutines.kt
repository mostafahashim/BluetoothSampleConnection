package bluetooth.sample.connection.domain.remoteService

import almaqraa.student.domain.parseFromJson
import android.util.Log
import bluetooth.sample.connection.data.resources.DataState
import bluetooth.sample.connection.domain.setup.getDefaultHeaders
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

suspend inline fun <reified T> startGetMethodUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MutableMap<String, Any>
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val response = apiInterface!!.doGetConnectionUsingCoroutines(
            getDefaultHeaders(false), urlFunction, params
        )
        if ((response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            Log.v("response", response.body()!!.toString())
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                emit(
                    DataState.Error(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        } else {
            emit(DataState.Error(""))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }

suspend inline fun <reified T> startPostMethodUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MultipartBody
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val response = apiInterface!!.doPostConnectionUsingCoroutines(
            getDefaultHeaders(true),
            urlFunction,
            params
        )
        if ((response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            Log.v("response", response.body()!!.toString())
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                var error = response.errorBody()!!.string()
                emit(
                    DataState.Error(
                        JSONObject(
                            error
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        } else {
            emit(DataState.Error(""))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }

suspend inline fun <reified T> startPutMethodUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MultipartBody
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val response = apiInterface!!.doPutConnectionUsingCoroutines(
            getDefaultHeaders(true),
            urlFunction,
            params
        )
        if ((response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            Log.v("response", response.body()!!.toString())
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                emit(
                    DataState.Error(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        } else {
            emit(DataState.Error(""))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }

suspend inline fun <reified T> startPostMethodWithGSONParamsUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MutableMap<String, Any>
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val json = Gson().toJson(params)
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json
        )

        val response = apiInterface!!.doPostConnectionUsingCoroutines(
            getDefaultHeaders(false),
            urlFunction,
            body
        )
        if (response != null && (response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            Log.v("response", response.body()!!.toString())
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                emit(
                    DataState.Error(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        } else {
            emit(DataState.Error(""))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }

suspend inline fun <reified T> startPutMethodWithGSONParamsUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MutableMap<String, Any>
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val json = Gson().toJson(params)
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            json
        )

        val response = apiInterface!!.doPutConnectionUsingCoroutines(
            getDefaultHeaders(false),
            urlFunction,
            body
        )
        if (response != null && (response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            Log.v("response", response.body()!!.toString())
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                emit(
                    DataState.Error(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message as String))
            }
        } else {
            emit(DataState.Error("" as String))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }


suspend inline fun <reified T> startDeleteMethodUsingCoroutines(
    isUseBaseUrlPref: Boolean,
    urlFunction: String,
    params: MutableMap<String, Any>
): Flow<DataState<Any?>> =
    flow {
        val apiInterface = ConnectionHandler.getInstance().getClient(isUseBaseUrlPref)
            ?.create(APIInterface::class.java)
        emit(DataState.Loading)
        val response = apiInterface!!.doDeleteConnectionUsingCoroutines(
            getDefaultHeaders(false), urlFunction, params
        )
        if ((response.code() == 200 || response.code() == 201) && response.isSuccessful) {
            var responseModel =
                parseFromJson(response.body()!!.toString()) as T?
            emit(DataState.Success(responseModel))
        } else if (response.code() == 401 && response.errorBody() != null) {
            try {
                emit(
                    DataState.Unauthenticated(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Unauthenticated(e.message))
            }
        } else if (response.errorBody() != null) {
            try {
                emit(
                    DataState.Error(
                        JSONObject(
                            response.errorBody()!!.string()
                        ).toString()
                    )
                )
            } catch (e: Exception) {
                emit(DataState.Error(e.message as String))
            }
        } else {
            emit(DataState.Error(""))
        }
    }.catch { e ->
        emit(DataState.Error(e.message.toString()))
    }