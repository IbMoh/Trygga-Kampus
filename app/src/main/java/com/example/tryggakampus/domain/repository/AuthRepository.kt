package com.example.tryggakampus.domain.repository

import android.util.Log
import com.example.tryggakampus.domain.model.ArticleModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

enum class AuthResponse {
    ERROR,
    SUCCESS,
    FAILURE
}

interface AuthRepository {
    suspend fun authenticateUser(email: String, password: String): AuthResponse
}

object AuthRepositoryImpl: AuthRepository {
    //private const val COLLECTION_NAME = "articles"

    override suspend fun authenticateUser(email: String, password: String): AuthResponse {
        try {
            val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            if (result.user == null) {
                return AuthResponse.FAILURE
            }
            return AuthResponse.SUCCESS
        }

        catch (e: FirebaseAuthInvalidCredentialsException) {
            return AuthResponse.FAILURE
        }

        catch (e: FirebaseNetworkException) {
            Log.d("FATAL", e.stackTraceToString())
        }

        return AuthResponse.ERROR
    }
}