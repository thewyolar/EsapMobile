package javavlsu.kb.esap.esapmobile.domain.repository

import javavlsu.kb.esap.esapmobile.domain.api.MainApiService
import javavlsu.kb.esap.esapmobile.domain.util.apiRequestFlow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
) {
    fun getUserInfo(token: String) = apiRequestFlow {
        mainApiService.getUserInfo(token)
    }
}