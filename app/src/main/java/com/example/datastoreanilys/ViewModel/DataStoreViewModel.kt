package com.example.datastoreanilys.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastoreanilys.data.UserData
import com.example.datastoreanilys.data.UserPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DataStoreViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application.applicationContext)

    private val _userData = MutableStateFlow(UserData("", "", 0, "", "", ""))
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.userFlow.collect {
                _userData.value = it
            }
        }
    }


    fun updateName(name: String) { _userData.value = _userData.value.copy(name = name) }
    fun updateProfession(profession: String) { _userData.value = _userData.value.copy(profession = profession) }
    fun updatePhone(phone: String) { _userData.value = _userData.value.copy(phone = phone) }
    fun updateSkills(skills: String) { _userData.value = _userData.value.copy(skills = skills) }
    fun updateGithub(github: String) { _userData.value = _userData.value.copy(github = github) }
    fun updateLinkedIn(linkedin: String) { _userData.value = _userData.value.copy(linkedin = linkedin) }


    fun updateAll(newUserData: UserData) {
        _userData.value = newUserData
    }

    fun saveAll() {
        viewModelScope.launch {
            prefs.saveUserData(_userData.value)
        }
    }
}
