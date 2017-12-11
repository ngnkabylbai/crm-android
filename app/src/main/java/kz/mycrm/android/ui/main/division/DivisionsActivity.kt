package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.mainIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status

fun Context.divisionsIntent(): Intent {
    return Intent(this, DivisionsActivity::class.java)
}

class DivisionsActivity : AppCompatActivity() {

    private lateinit var divisionViewModel: DivisionViewModel

    lateinit var rvDivisions: RecyclerView

    @BindView(R.id.tvTitile)
    lateinit var title: TextView

    private lateinit var adapter: DivisionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divisions)

        ButterKnife.bind(this)
        rvDivisions = findViewById(R.id.rvDivisions)
        rvDivisions.setHasFixedSize(true)
        adapter = DivisionAdapter(this)
        rvDivisions.adapter = adapter
        rvDivisions.layoutManager = LinearLayoutManager(this)

        divisionViewModel = ViewModelProviders.of(this).get(DivisionViewModel::class.java)

        divisionViewModel.getToken().observe(this, Observer { token ->
            if (token != null) {

                divisionViewModel.loadUserDivisions(token.token).observe(this, Observer { resourceDivisionList ->
                    if (resourceDivisionList?.data != null) {
                        Logger.debug("resource" + resourceDivisionList.data.size)
                        if (resourceDivisionList.status != Status.ERROR) {
                            adapter.clear()
                            for (d in resourceDivisionList.data) {
                                adapter.add(d)
                            }
                        } else {
                            startActivity(loginIntent())
                        }
                    }
                })
            }
        })
    }
}
