package com.example.app.driveyourday.data.repository.login

import com.example.app.driveyourday.domain.repository.login.LoginRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

/**
 * Dummy repository simulating network calls.
 */
class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    override suspend fun login(username: String, password: String): String {
        delay(2_000)
        return Random.nextInt().toString()
    }
}