package kz.mycrm.android.ui.main.notification

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Order
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class CurrentNotificationAdapter(context: Context): RecyclerView.Adapter<CurrentNotificationAdapter.ViewHolder>(){

    private var notificationList: ArrayList<Order> = ArrayList()

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationList[position]

        holder.name.text = notification.customer?.lastname + " " + notification.customer?.name + " " + notification.services[0].serviceName
        holder.time.text = notification.start?.substring(11, 16) // 15:00
        holder.type.text = "Напоминание".toUpperCase()
    }

    // TODO: change type to Notification
    fun add(notification: Order) {
        notificationList.add(notification)
        notifyDataSetChanged()
    }

    fun clear() {
        notificationList.clear()
    }

    class ViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            Toast.makeText(itemView.context, "Go to map", Toast.LENGTH_SHORT).show()
        }

        internal var name: TextView
        internal var time: TextView
        internal var type: TextView
        internal var link: TextView

        init {
            name = itemView.findViewById<View>(R.id.notification_name) as TextView
            time = itemView.findViewById<View>(R.id.notification_time) as TextView
            type = itemView.findViewById<View>(R.id.notification_type) as TextView
            link = itemView.findViewById<View>(R.id.notification_link) as TextView
            link.setOnClickListener(this)
        }
    }
}