package bluetooth.sample.connection.data.resources

sealed class DataState<out R>{
    data class Success<out T>(val response : T) : DataState<T>()
    data class Error(val error: String) : DataState<String>()
    data class Unauthenticated(val error: String?) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
