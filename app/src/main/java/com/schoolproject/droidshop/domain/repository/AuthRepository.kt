package com.schoolproject.droidshop.domain.repository

import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.domain.model.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signUp(email: String, password: String, username: String): Resource<User>
    suspend fun signOut(): Resource<Unit>
    fun getCurrentUser(): User?
}