package com.example.app.driveyourday.domain.repository.login

interface LoginRepository {

    /**
     * Launches login call and returns userId if success
     * @return userId if login operation worked, exception if not
     */
    suspend fun login(username: String, password: String) : String

}