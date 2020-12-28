package com.example.placemark.views.login

import com.example.placemark.views.BasePresenter
import com.example.placemark.views.BaseView
import com.example.placemark.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    fun doLogin(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }

    fun doSignUp(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }

}