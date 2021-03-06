package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.MainActivity
import kz.mycrm.android.ui.main.info.InfoActivity
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status

fun Context.divisionsIntent(): Intent {
    return Intent(this, DivisionsActivity::class.java)
}

class DivisionsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: DivisionViewModel

    lateinit var rvDivisions: RecyclerView

    private lateinit var adapter: DivisionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divisions)

        rvDivisions = findViewById(R.id.rvDivisions)
        rvDivisions.setHasFixedSize(true)
        adapter = DivisionAdapter(this)
        rvDivisions.adapter = adapter
        rvDivisions.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(DivisionViewModel::class.java)
        viewModel.getResourceDivisionsList().observe(this, Observer { resourceList ->
            when(resourceList?.status) {
                Status.LOADING -> swipeRefreshContainer.isRefreshing = true
                Status.SUCCESS -> onSuccess(resourceList)
                Status.ERROR -> onError(resourceList)
            }
        })

        dialogManager = MaterialDialog.Builder(this)
                .positiveText(R.string.try_again)

        swipeRefreshContainer.setOnRefreshListener(this)
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        swipeRefreshContainer.post {
            viewModel.startRefresh()
        }

        val mIntent = intent
        if(mIntent.extras != null && mIntent.extras.getString("order_id") != null) {
            val startInfoIntent = Intent(this, InfoActivity::class.java)
            val bundle = mIntent.extras
            startInfoIntent.putExtras(bundle)

            startActivityForResult(startInfoIntent, Constants.infoRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val startMainIntent = Intent(this, MainActivity::class.java)
        val bundle = intent.extras
        startMainIntent.putExtras(bundle)
        startActivity(startMainIntent)
        finish()
    }

    private fun onSuccess(divisionList: Resource<List<Division>>) {
        if (divisionList.data != null) {
            val list = divisionList.data
            Logger.debug("Division count: ${list.size}")
            adapter.clear()
            for (division in divisionList.data) {
                adapter.add(division)
            }

            swipeRefreshContainer.isRefreshing = false
        } else {
            startLogin()
        }
    }

    private fun onError(divisionList: Resource<List<Division>>) {
        val isInternetAvailable = isInternetAvailable()
        if(isInternetAvailable) {
            Toast.makeText(this, divisionList.message, Toast.LENGTH_SHORT).show()
        } else {
            showMessage(getString(R.string.error_no_internet_connection)) {
                viewModel.startRefresh()
            }
        }
    }

    override fun onRefresh() {
        viewModel.startRefresh()
    }

    private fun startLogin() {
        startActivity(loginIntent())
        finish()
    }
}
