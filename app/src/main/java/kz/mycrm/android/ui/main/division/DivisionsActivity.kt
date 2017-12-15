package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.mainIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status

fun Context.divisionsIntent(): Intent {
    return Intent(this, DivisionsActivity::class.java)
}

class DivisionsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var divisionViewModel: DivisionViewModel

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

        divisionViewModel = ViewModelProviders.of(this).get(DivisionViewModel::class.java)

        swipeRefreshContainer.setOnRefreshListener(this)
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        swipeRefreshContainer.post {
            loadDivisions()
        }
    }

    private fun loadDivisions() {
        swipeRefreshContainer.isRefreshing = true

        divisionViewModel.getToken().observe(this, Observer { token ->
            divisionViewModel.loadUserDivisions(token!!.token).observe(this, Observer { resourceList ->
                if (resourceList?.data != null && resourceList.status == Status.SUCCESS) {
                    val list = resourceList.data
                    Logger.debug("resource" + list.size)
//                    if (list.size > 1) {
                    adapter.clear()
                    for (d in resourceList.data) {
                        adapter.add(d)
                    }
//                    } else if (list.size == 1) {
//                        startMain(list[0])
//                    }
                    swipeRefreshContainer.isRefreshing = false
                } else if ((resourceList?.data == null && resourceList?.status == Status.SUCCESS) || resourceList?.status == Status.ERROR) {
                    startLogin()
                }
            })
        })
    }

    override fun onRefresh() {
        loadDivisions()
    }

    private fun startLogin() {
        startActivity(loginIntent())
        finish()
    }

    private fun startMain(division: Division) {
        val intent = mainIntent()
        intent.putExtra("division_id", division.id)
        startActivity(intent)
        finish()
    }
}
