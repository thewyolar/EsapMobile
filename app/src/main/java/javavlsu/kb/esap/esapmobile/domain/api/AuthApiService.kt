package javavlsu.kb.esap.esapmobile.domain.api

import javavlsu.kb.esap.esapmobile.domain.model.request.AuthRequest
import javavlsu.kb.esap.esapmobile.domain.model.response.AuthResponse
import javavlsu.kb.esap.esapmobile.domain.model.response.ServerStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @GET("actuator/health")
    suspend fun checkStatus(): Response<ServerStatusResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body token: String): Response<AuthResponse>
}