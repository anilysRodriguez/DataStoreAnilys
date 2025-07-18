package com.example.datastoreanilys.data


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFS_NAME = "user_prefs"

val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

object PreferenceKeys {
    val NAME = stringPreferencesKey("name")
    val PROFESSION = stringPreferencesKey("profession")
    val EXPERIENCE = intPreferencesKey("experience")
    val PHONE = stringPreferencesKey("phone")
    val HANDLE = stringPreferencesKey("handle")
    val EMAIL = stringPreferencesKey("email")
    val SKILLS = stringPreferencesKey("skills")
    val GITHUB = stringPreferencesKey("github")
    val LINKEDIN = stringPreferencesKey("linkedin")
}
data class UserData(
    val name: String = "",
    val profession: String = "",
    val experience: Int = 0,
    val phone: String = "",
    val handle: String = "",
    val email: String = "",
    val skills: String = "",
    val github: String = "",
    val linkedin: String = ""
)

class UserPreferences(private val context: Context) {

    val userFlow: Flow<UserData> = context.dataStore.data.map { prefs ->
        UserData(
            name = prefs[PreferenceKeys.NAME] ?: "",
            profession = prefs[PreferenceKeys.PROFESSION] ?: "",
            experience = prefs[PreferenceKeys.EXPERIENCE] ?: 0,
            phone = prefs[PreferenceKeys.PHONE] ?: "",
            handle = prefs[PreferenceKeys.HANDLE] ?: "",
            email = prefs[PreferenceKeys.EMAIL] ?: "",
            skills = prefs[PreferenceKeys.SKILLS] ?: "",
            github = prefs[PreferenceKeys.GITHUB] ?: "",
            linkedin = prefs[PreferenceKeys.LINKEDIN] ?: ""
        )
    }

    suspend fun saveUserData(data: UserData) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.NAME] = data.name
            prefs[PreferenceKeys.PROFESSION] = data.profession
            prefs[PreferenceKeys.EXPERIENCE] = data.experience
            prefs[PreferenceKeys.PHONE] = data.phone
            prefs[PreferenceKeys.HANDLE] = data.handle
            prefs[PreferenceKeys.EMAIL] = data.email
            prefs[PreferenceKeys.SKILLS] = data.skills
            prefs[PreferenceKeys.GITHUB] = data.github
            prefs[PreferenceKeys.LINKEDIN] = data.linkedin
        }
    }
}
