package javavlsu.kb.esap.esapmobile.domain.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import javavlsu.kb.esap.esapmobile.config.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val ROLES_KEY = stringPreferencesKey("roles")
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getRoles(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ROLES_KEY]
        }
    }

    suspend fun saveRoles(roles: String) {
        context.dataStore.edit { preferences ->
            preferences[ROLES_KEY] = roles
        }
    }

    suspend fun deleteRoles() {
        context.dataStore.edit { preferences ->
            preferences.remove(ROLES_KEY)
        }
    }
}