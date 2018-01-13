package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_info.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.Logger
import java.text.SimpleDateFormat
import java.util.*

fun Context.infoIntent(): Intent {
    return Intent(this, InfoActivity::class.java)
}

class InfoActivity : AppCompatActivity() {

    private lateinit var rv:RecyclerView
    private lateinit var lm:LinearLayoutManager
    private lateinit var adapter:ServiceAdapter

    private var serviceList:List<Service>? = null
    private val dateFormat = Order.datetimeFormat
    private val textDateFormat = SimpleDateFormat("d MMMM, H:mm", Locale.getDefault())

    private lateinit var viewModel:InfoViewModel

    private lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        rv = findViewById(R.id.service_list)
        rv.isNestedScrollingEnabled = false
        rv.setHasFixedSize(true)
        lm = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)

        val intent: Intent = intent

        viewModel.requestOrder(intent.getStringExtra("id")).observe(this, Observer { order->
            clientName.text = order?.customerName
            clientPhone.text = order?.customerPhone
            clientNotes.text = order?.note

            var strDate = ""
            try {
                val date = dateFormat.parse(order?.datetime)
                strDate = textDateFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            infoTime.text = strDate.toLowerCase()

            if (order != null){
                adapter = ServiceAdapter(order.services, this)
                rv.layoutManager = lm
                rv.adapter = adapter
            }
            Logger.debug("Started InfoActivity: ${order?.services.toString()}")
        })

        closeActivity.setOnClickListener { finish() }
    }
}
