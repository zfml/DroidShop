package com.schoolproject.droidshop.data.repository




import com.schoolproject.droidshop.core.util.Resource
import com.schoolproject.droidshop.data.mappers.toUser
import com.schoolproject.droidshop.data.remote.auth.FirebaseAuthService
import com.schoolproject.droidshop.domain.model.User
import com.schoolproject.droidshop.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            val firebaseUser = firebaseAuthService.signIn(email, password).getOrThrow()
            Resource.Success(firebaseUser.toUser())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown sign in error")
        }
    }

    override suspend fun signUp(email: String, password: String, username: String): Resource<User> {
        return try {
            val firebaseUser = firebaseAuthService.signUp(email, password, username).getOrThrow()
            Resource.Success(firebaseUser.toUser())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown sign up error")
        }
    }

    override suspend fun signOut(): Resource<Unit> {
        return try {
            firebaseAuthService.signOut()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to sign out: ${e.localizedMessage}")
        }
    }

    override fun getCurrentUser(): User? = firebaseAuthService.currentUser?.toUser()
}
