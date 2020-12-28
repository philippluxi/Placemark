package com.example.placemark.views.login

import com.google.firebase.auth.FirebaseAuth
import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.longToast("Log-In Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.longToast("Sign Up Failed: ${task.exception?.message}")
            }
            view?.hideProgress()
        }
    }
}
