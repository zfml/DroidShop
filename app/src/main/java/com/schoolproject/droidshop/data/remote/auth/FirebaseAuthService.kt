package com.schoolproject.droidshop.data.remote.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun signUp(email: String, password: String, userName: String): Result<FirebaseUser> =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { res ->
                    // Optionally update displayName
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(userName)
                        .build()
                    res.user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { cont.resume(Result.success(res.user!!)) }
                }
                .addOnFailureListener { cont.resume(Result.failure(it)) }
        }

    suspend fun signIn(email: String, password: String): Result<FirebaseUser> =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { cont.resume(Result.success(it.user!!)) }
                .addOnFailureListener { cont.resume(Result.failure(it)) }
        }

    fun signOut() {
        auth.signOut()
    }
    val currentUser: FirebaseUser?
        get() = auth.currentUser





}