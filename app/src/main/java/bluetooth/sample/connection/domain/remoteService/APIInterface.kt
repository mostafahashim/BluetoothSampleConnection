package bluetooth.sample.connection.domain.remoteService

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import okhttp3.ResponseBody
import retrofit2.http.Streaming
import retrofit2.http.GET

interface APIInterface {

    @GET("{function}")
    fun doGetConnection(
        @HeaderMap headers: Map<String, String>, @Path(
            value = "function",
            encoded = true
        ) function: String, @QueryMap params: MutableMap<String, Any?>
    ): Call<String>

    @POST("{function}")
    fun doPostConnection(
        @HeaderMap headers: Map<String, String>, @Path(
            value = "function",
            encoded = true
        ) function: String, @Body params: RequestBody
    ): Call<String>

    @Streaming
    @GET("{function}")
    fun downloadFile(
        @Path(
            value = "function",
            encoded = true
        ) function: String
    ): Call<ResponseBody>

    @GET("{function}")
    suspend fun doGetConnectionUsingCoroutines(
        @HeaderMap headers: Map<String, String>,
        @Path(
            value = "function",
            encoded = true
        ) function: String, @QueryMap params: MutableMap<String, Any>
    ): Response<String>

    @POST("{function}")
    suspend fun doPostConnectionUsingCoroutines(
        @HeaderMap headers: Map<String, String>,
        @Path(
            value = "function",
            encoded = true
        ) function: String, @Body params: RequestBody
    ): Response<String>

    @PUT("{function}")
    suspend fun doPutConnectionUsingCoroutines(
        @HeaderMap headers: Map<String, String>,
        @Path(
            value = "function",
            encoded = true
        ) function: String, @Body params: RequestBody
    ): Response<String>

    @DELETE("{function}")
    suspend fun doDeleteConnectionUsingCoroutines(
        @HeaderMap headers: Map<String, String>,
        @Path(
            value = "function",
            encoded = true
        ) function: String, @QueryMap params: MutableMap<String, Any>
    ): Response<String>
}