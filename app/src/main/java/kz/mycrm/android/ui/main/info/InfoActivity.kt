package kz.mycrm.android.ui.main.info

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Service
import kz.mycrm.android.util.Logger

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    @BindView(R.id.close_activity)
    lateinit var close: ImageView
    @BindView(R.id.info_time)
    lateinit var time:TextView
    @BindView(R.id.client_name)
    lateinit var client_name:TextView
    @BindView(R.id.client_phone)
    lateinit var client_phone:TextView
    @BindView(R.id.client_notes)
    lateinit var client_notes:TextView

    lateinit var rv:RecyclerView
    lateinit var lm:LinearLayoutManager
    lateinit var adapter:ServiceAdapter

    var serviceList:List<Service>? = null

    lateinit var viewModel:InfoViewModel

    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ButterKnife.bind(this)
        rv = findViewById(R.id.service_list)
        rv.setHasFixedSize(true)
        lm = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)

        var intent: Intent = getIntent()

        viewModel.requestOrder(intent.getStringExtra("id")).observe(this, Observer { order->
            client_name.setText(order?.customerFullName)
            client_phone.setText(order?.customerPhone)
            client_notes.setText(order?.note)
            if (order != null){
                adapter = ServiceAdapter(order.services, this)
                rv.setLayoutManager(lm)
                rv.setAdapter(adapter)
            }
            Logger.debug("order = " + order?.services.toString())
        })
    }

    @OnClick(R.id.close_activity)
    override fun onClick(view: View?){
        finish()
    }
}
