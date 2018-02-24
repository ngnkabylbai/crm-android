package kz.mycrm.android.ui.main.info

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_info.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.db.entity.UpdateOrder
import kz.mycrm.android.db.entity.UpdateService
import kz.mycrm.android.ui.BaseActivity
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

class InfoActivity : BaseActivity() {

    private lateinit var rv:RecyclerView
    private lateinit var lm:LinearLayoutManager
    private val adapter = InfoServiceAdapter(this)

    private val textDateFormat = SimpleDateFormat("d MMMM, H:mm", Locale.getDefault())

    private lateinit var viewModel:InfoViewModel

    private var orderId: String = "-1"
    private var divisionId: String = "-1"
    private var staffId: String = "-1"

    private var checkedServices: ArrayList<Service> = ArrayList()
    private lateinit var order: Order

    private var isOrderUpdated = false

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
                Status.LOADING -> progress.visibility = View.VISIBLE
                Status.SUCCESS -> finish()
                Status.ERROR -> onError(order)
            }
        })

        dialogManager = MaterialDialog.Builder(this)
                .positiveText(R.string.try_again)

        val mIntent: Intent = intent
        if(mIntent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!!.getString(key)
                Logger.debug("Key: $key Value: $value")
            }

            orderId = mIntent.getStringExtra("order_id")
            staffId = mIntent.getStringExtra("staff_id")
            divisionId = mIntent.getStringExtra("division_id")
        }

        viewModel.getOrderById(orderId).observe(this, Observer { order ->
            when(order!!.status) {
                Status.LOADING -> progress.visibility = View.VISIBLE
                Status.SUCCESS -> onRequestSuccess(order)
                Status.ERROR -> onError(order)
            }
        })

        closeActivity.setOnClickListener { finish() }

        addServiceTextView.setOnClickListener {
            val serviceIntent = addServiceIntent()

            val servicesId = ArrayList<String>()
            checkedServices.mapTo(servicesId) { it.id }

            val bundle = Bundle()
            bundle.putSerializable("services_id", servicesId)
            bundle.putString("order_id", orderId)
            bundle.putString("division_id", divisionId)
            bundle.putString("staff_id", staffId)

            serviceIntent.putExtras(bundle)

            startActivityForResult(serviceIntent, 0)
        }

        saveTextView.setOnClickListener {
            if(!isOrderUpdated) {
                finish()
                return@setOnClickListener
            }

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
        progress.visibility = View.INVISIBLE
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

    private fun onError(order: Resource<Order>) {
        val isInternetAvailable = isInternetAvailable()
        if(isInternetAvailable) {
            progress.visibility = View.INVISIBLE
            Toast.makeText(this, order.message, Toast.LENGTH_SHORT).show()
        } else {
            showMessage(getString(R.string.error_no_internet_connection)) {
                viewModel.getOrderById(orderId)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK) {
            isOrderUpdated = true
            checkedServices = data!!.extras.getSerializable("services") as ArrayList<Service>
            adapter.setServicesList(checkedServices)
        }
    }
}
