package kz.mycrm.android.ui.main.info.service

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_service.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status

/**
 * Created by Nurbek Kabylbay on 10.02.2018.
 */

fun Context.addServiceIntent(): Intent {
    return Intent(this, AddServiceActivity::class.java)
}

class AddServiceActivity : AppCompatActivity() {

    private lateinit var viewModel: AddServiceViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AddServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        viewModel = ViewModelProviders.of(this).get(AddServiceViewModel::class.java)

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)

        val extras = intent.extras
        val orderId = extras.getString("orderId")
        val divisionId = extras.getInt("divisionId", -1)
        val staffId = extras.getInt("staffId", -1)
        val services = extras.getSerializable("servicesId") as ArrayList<String>

        val order = viewModel.getOrderById(orderId)
        order.services = viewModel.getServiceArrayList(services)

        adapter = AddServiceAdapter(order, this)

        viewModel.requestServiceList(divisionId, staffId).observe(this, Observer { serviceList ->
            when(serviceList?.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> onSuccess(serviceList)
                Status.ERROR -> {}
            }
        })

        Logger.debug(order.toString())

        cancelTextView.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        readyTextView.setOnClickListener {
            val checkedServices = adapter.getCheckedServiceList()
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putSerializable("services", checkedServices)
            resultIntent.putExtras(bundle)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun onSuccess(serviceList: Resource<List<Service>>) {
        if(serviceList.data == null)
            return

        layoutManager = LinearLayoutManager(this)
        adapter.setServiceList(serviceList.data)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}