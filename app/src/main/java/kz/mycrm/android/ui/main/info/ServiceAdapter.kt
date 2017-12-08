package kz.mycrm.android.ui.main.info

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Service

/**
 * Created by asset on 12/7/17.
 */

class ServiceAdapter(internal var serviceList: List<Service>, internal var context: Context) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.service_title)
        lateinit var title: TextView
        @BindView(R.id.service_price)
        lateinit var price: TextView

        init {
            ButterKnife.bind(itemView)
            itemView.setOnClickListener { Toast.makeText(context, "service number " + itemView.id, Toast.LENGTH_SHORT).show() }
        }
    }
}
