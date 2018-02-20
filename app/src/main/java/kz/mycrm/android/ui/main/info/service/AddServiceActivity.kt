package kz.mycrm.android.ui.main.info.service

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
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

class AddServiceActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: AddServiceViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AddServiceAdapter
    private lateinit var divisionId: String
    private lateinit var staffId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        viewModel = ViewModelProviders.of(this).get(AddServiceViewModel::class.java)

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)


        val extras = intent.extras
        divisionId = extras.getString("divisionId")
        staffId = extras.getString("staffId")
        val orderId = extras.getString("orderId")
        val services = extras.getSerializable("servicesId") as ArrayList<String>

        val order = viewModel.getOrderById(orderId)
        order.services = viewModel.getServiceArrayListByIds(services)

        adapter = AddServiceAdapter(order, this)

        viewModel.getResourceServiceList().observe(this, Observer { serviceList ->
            when(serviceList?.status) {
                Status.LOADING -> swipeRefreshContainer.isRefreshing = true
                Status.SUCCESS -> onSuccess(serviceList)
                Status.ERROR -> onError(serviceList)
            }
        })

        Logger.debug(order.toString())

        swipeRefreshContainer.setOnRefreshListener(this)
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        swipeRefreshContainer.post {
            viewModel.startRefresh(divisionId.toInt(), staffId.toInt())
        }

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

    override fun onRefresh() {
        viewModel.startRefresh(divisionId.toInt(), staffId.toInt())
    }

    private fun onSuccess(serviceList: Resource<List<Service>>) {
        if(serviceList.data == null)
            return

        layoutManager = LinearLayoutManager(this)
        adapter.setServiceList(serviceList.data)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        swipeRefreshContainer.isRefreshing = false
    }

    private fun onError(serviceList: Resource<List<Service>>) {
        swipeRefreshContainer.isRefreshing = false
        Toast.makeText(this, serviceList.message, Toast.LENGTH_SHORT).show()
    }
}