package kz.mycrm.android.ui.main.notification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status
import java.util.*



/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: NotificationViewModel

    private lateinit var currentNotificationAdapter: CurrentNotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)

        currentNotificationAdapter = CurrentNotificationAdapter(activity)

        rvCurrentNotifications.adapter = currentNotificationAdapter
        rvCurrentNotifications.layoutManager = LinearLayoutManager(activity)
        rvCurrentNotifications.setHasFixedSize(true)

        val divisionId = arguments.getInt("division_id")
        viewModel.setDivisionId(divisionId)

        viewModel.getToDaysNotifications().observe(this, Observer { resourceList ->
            when(resourceList!!.status) {
                Status.LOADING -> onLoading(resourceList)
                Status.SUCCESS -> onSuccess(resourceList)
                Status.ERROR -> onError()
            }
        })

        swipeRefreshContainer.setOnRefreshListener(this)
        swipeRefreshContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        swipeRefreshContainer.post {
            viewModel.startRefresh()
        }
    }

    override fun onRefresh() {
        viewModel.startRefresh()
    }

    private fun onLoading(resourceList: Resource<List<Order>>) {
        swipeRefreshContainer.isRefreshing = true
        loadData(resourceList)
    }

    private fun onSuccess(resourceList: Resource<List<Order>>) {
        loadData(resourceList)
        swipeRefreshContainer.isRefreshing = false
    }

    private fun onError() {

        swipeRefreshContainer.isRefreshing = false
    }

    private fun loadData(resourceList: Resource<List<Order>>) {
        var orderArrayList = ArrayList<Order>()
        val orderList = resourceList.data
        if (orderList != null) {
            orderArrayList.clear()
            for (order in orderList) {
                orderArrayList.add(order)
            }
            orderArrayList = getFilteredAndSortedList(orderArrayList)
            currentNotificationAdapter.setListAndNotify(orderArrayList)
        }
    }

    private fun getFilteredAndSortedList(list: ArrayList<Order>): ArrayList<Order> {
        val todayStr = Order.datetimeFormat.format(Date())
        val today = Order.datetimeFormat.parse(todayStr)
            val cal = Calendar.getInstance()
            cal.time = today
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
        today.time = cal.timeInMillis
        val result = ArrayList<Order>()
        for(order in list) {
            val date = Order.datetimeFormat.parse(order.datetime)
            if(date.equals(today) || date.after(today)) {
                result.add(order)
            }
        }

        Collections.sort(result)
        return result
    }
}

