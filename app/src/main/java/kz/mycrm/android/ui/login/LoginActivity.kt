package kz.mycrm.android.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

/**
 * Created by NKabylbay on 11/11/2017.
 */
class LoginActivity : AppCompatActivity() {

    lateinit var viewModel : LoginViewModel
    private var login = "+7 701 381 71 15"
    private var password = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.requestToken(login, password)
                .observe(this, Observer { getToken ->
                    if (getToken != null)
                        Log.d("API", "Answer token:)" )
                    else
                        Log.d("API", "Answer token: is null")
                })
    }
}