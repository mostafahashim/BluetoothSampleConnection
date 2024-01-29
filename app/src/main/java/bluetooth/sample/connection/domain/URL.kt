package almaqraa.student.domain

object URL {

    fun getAllUsersUrl(): String {
        var url = "register/getalluser"
        url = url.replace(" ".toRegex(), "%20")
        return url
    }

}