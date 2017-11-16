package kz.mycrm.android.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.util.Status

/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel

    @BindView(R.id.login)
    lateinit var login: EditText
    @BindView(R.id.password)
    lateinit var password: EditText
    @BindView(R.id.progress)
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

//    TODO: handle null values
    @OnClick(R.id.loginButton, R.id.password, R.id.forgotPassword, R.id.createAccount)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.loginButton -> {
                progress.visibility = View.VISIBLE

                viewModel.requestToken(login.text.toString(), password.text.toString())
                        .observe(this, Observer { token ->

                            if(token?.status == Status.SUCCESS) { // success, token received. Go to MainActivity
                                progress.visibility = View.GONE
                            } else if(token?.status == Status.ERROR) { // error, show error message

                            }
                        })
            }
            R.id.forgotPassword -> Log.d("MyCRM", "Forgot pass clicked")
            R.id.createAccount -> Log.d("MyCRM", "Create acc clicked")
        }
    }

// TODO: complete
//        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
//        viewModel.requestUserDivisions("T5texf3FvexQXDSCEsI9uhIhiGTcljkt_1510810898", null)
//                .observe(this, Observer {divisions ->
//                    if(divisions?.status == Status.SUCCESS) {
//                        for(d: Division in divisions.data!!) {
//                            Log.d("MyCRM", d.toString())
//                        }
//                    }
//                })

}