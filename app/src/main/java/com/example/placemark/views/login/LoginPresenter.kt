package com.example.placemark.views.login

import com.example.placemark.models.firebase.PlacemarkFireStore
import com.google.firebase.auth.FirebaseAuth
import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: PlacemarkFireStore? = null

    init {
        if (app.placemarks is PlacemarkFireStore) {
            fireStore = app.placemarks as PlacemarkFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchPlacemarks {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null)
                    fireStore!!.fetchPlacemarks {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }
}