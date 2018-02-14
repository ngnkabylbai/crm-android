package kz.mycrm.android.ui.main.info.service

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.db.entity.Service

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class AddServiceAdapter(private val order: Order, private var context: Context) : RecyclerView.Adapter<AddServiceAdapter.ViewHolder>(){

    private val serviceArrayList = ArrayList<Service>()
    private lateinit var mask: Array<Int> // used to identify if the service is checked or not

    private val checkedServices:MutableSet<Service> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = serviceArrayList[position]

        if(mask[position] == 1) {
            checkedServices.add(service)
            holder.selectCheckBox.isChecked = true
        } else {
            checkedServices.remove(service)
            holder.selectCheckBox.isChecked = false
        }

        holder.selectCheckBox.setOnClickListener {
            if(holder.selectCheckBox.isChecked) {
                checkedServices.add(service)
                holder.selectCheckBox.isChecked = true
                mask[position] = 1
            } else {
                holder.selectCheckBox.isChecked = false
                checkedServices.remove(service)
                mask[position] = 0
            }
        }
        holder.serviceName.text = service.serviceName
        holder.servicePrice.text = service.price
    }

    override fun getItemCount(): Int {
        return serviceArrayList.size
    }

    fun getCheckedServiceList(): ArrayList<Service> {
        return ArrayList(checkedServices)
    }

    fun setServiceList(list: List<Service>) {
        serviceArrayList.clear()
        serviceArrayList += list

        refreshMask()

        notifyDataSetChanged()
    }

    private fun refreshMask() {
        mask = Array(serviceArrayList.size, {_ -> 0})

        for(i in 0 until serviceArrayList.size) {
            val service = serviceArrayList[i]
            if(order.services.any { it.id == service.id})
                mask[i] = 1
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var serviceName: TextView = itemView.findViewById<View>(R.id.serviceNameTextView) as TextView
        var servicePrice: TextView = itemView.findViewById<View>(R.id.servicePriceTextView) as TextView
        var selectCheckBox: CheckBox = itemView.findViewById<View>(R.id.selectRadioButton) as CheckBox
    }
}