package kz.mycrm.android.ui.main.info

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_info.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.db.entity.UpdateOrder
import kz.mycrm.android.db.entity.UpdateService
import kz.mycrm.android.ui.main.info.service.addServiceIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun Context.infoIntent(): Intent {
    return Intent(this, InfoActivity::class.java)
}

class InfoActivity : AppCompatActivity() {

    private lateinit var rv:RecyclerView
    private lateinit var lm:LinearLayoutManager
    private val adapter = InfoServiceAdapter(this)

    private val textDateFormat = SimpleDateFormat("d MMMM, H:mm", Locale.getDefault())

    private lateinit var viewModel:InfoViewModel

    private var orderId: String = "-1"
    private var divisionId: String = "-1"
    private var staffId: String = "-1"

    private lateinit var checkedServices: ArrayList<Service>
    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        rv = findViewById(R.id.service_list)
        rv.isNestedScrollingEnabled = false
        rv.setHasFixedSize(true)
        lm = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
        viewModel.getUpdatedOrder().observe(this, Observer { order ->
            when(order?.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> finish()
                Status.ERROR -> onUpdateError(order)
            }
        })

        val mIntent: Intent = intent
        if(mIntent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!!.getString(key)
                Logger.debug("Key: $key Value: $value")
            }

            orderId = mIntent.getStringExtra("order_id")
            divisionId = mIntent.getStringExtra("division_id")
            staffId = mIntent.getStringExtra("staff_id")
        }

        viewModel.getOrderById(orderId).observe(this, Observer { order ->
            when(order!!.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> onRequestSuccess(order)
                Status.ERROR -> {}
            }
        })

        closeActivity.setOnClickListener { finish() }

        addServiceTextView.setOnClickListener {
            val serviceIntent = addServiceIntent()

            val servicesId = ArrayList<String>()
            checkedServices.mapTo(servicesId) { it.id }

            val bundle = Bundle()
            bundle.putSerializable("servicesId", servicesId)
            bundle.putString("orderId", orderId)
            bundle.putString("divisionId", divisionId)
            bundle.putString("staffId", staffId)

            serviceIntent.putExtras(bundle)

            startActivityForResult(serviceIntent, 0)
        }

        saveTextView.setOnClickListener {
            val updateServiceList = ArrayList<UpdateService>()
            for(i in 0 until checkedServices.size) {
                val service = checkedServices[i]
                val updateService = UpdateService()

                updateService.id  = service.id
                updateService.duration = service.duration
                updateService.price  = service.price
                updateService.quantity  = service.quantity
                updateService.discount = service.discount

                updateServiceList.add(updateService)
            }

            val newOrder = UpdateOrder()
            newOrder.staffId = staffId
            newOrder.services = updateServiceList

            if(updateServiceList.isEmpty()){
                Toast.makeText(this, getString(R.string.warn_choose_service), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                viewModel.updateOrder(order.id, newOrder)
            }
        }
    }

    private fun onRequestSuccess(retrievedOrderResource: Resource<Order>) {
        this.order = retrievedOrderResource.data!!
//            val mOrder = if(BuildConfig.MOCK) viewModel.getTestOrder() else order
        val mOrder = this.order

        checkedServices = ArrayList(mOrder.services)

        clientName.text = mOrder.customerName
        clientPhone.text = mOrder.customerPhone
        clientNotes.text = mOrder.note

        infoTime.text = textDateFormat.format(mOrder.datetime)

        adapter.setServicesList(ArrayList(mOrder.services))
        rv.adapter = adapter
        rv.layoutManager = lm
    }

    private fun onUpdateError(order: Resource<Order>) {
        Toast.makeText(this, order.message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK) {
            checkedServices = data!!.extras.getSerializable("services") as ArrayList<Service>
            adapter.setServicesList(checkedServices)
        }
    }
}
