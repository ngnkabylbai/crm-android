package kz.mycrm.android.ui.main.info

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Service

/**
 * Created by asset on 12/7/17.
 */

class ServiceAdapter(private var serviceList: List<Service>, internal var context: Context) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = serviceList[position]
        holder.price.text = service .price
        holder.title.text = service .serviceName
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var title: TextView = itemView.findViewById<View>(R.id.service_title) as TextView
        internal var price: TextView = itemView.findViewById<View>(R.id.service_price) as TextView

        init {
//            itemView.setOnClickListener { Toast.makeText(context, "service number " + itemView.id, Toast.LENGTH_SHORT).show() }
        }
    }
}
