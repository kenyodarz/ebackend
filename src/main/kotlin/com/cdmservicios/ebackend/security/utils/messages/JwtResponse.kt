package com.cdmservicios.ebackend.security.utils.messages

class JwtResponse (
        var accessToken: String?,
        var id: Long?,
        var username: String?,
        var name: String?,
        var email: String?,
        var roles: List<String>?
) {
    val type = "Bearer"
}