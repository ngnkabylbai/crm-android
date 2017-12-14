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
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class CurrentNotificationAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var TYPE_HEADER = 0
    private var TYPE_ITEM = 1

    private var notificationList: ArrayList<Order> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
                    TYPE_HEADER -> {
                        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.header_cur_notification, parent, false)
                        ViewHeaderHolder(view)
                    }
                    else -> {
                        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_notification, parent, false)
                        ViewItemHolder(view)
                    }
             }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHeaderHolder) {

        } else if(holder is ViewItemHolder) {
            val notification = notificationList[position-1]
            val str = notification.customerName +" "+ notification.services[0].serviceName
            holder.name.text = str
        }
//        holder.time.text = notification.start?.substring(11, 16) // 15:00
//        holder.type.text = "Напоминание".toUpperCase()
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return TYPE_HEADER

        return TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return notificationList.size + 1
    }

    // TODO: change type to Notification
    fun add(notification: Order) {
        notificationList.add(notification)
        notifyDataSetChanged()
    }

    fun clear() {
        notificationList.clear()
    }

    class ViewItemHolder(itemView :View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            Toast.makeText(itemView.context, "Go to map", Toast.LENGTH_SHORT).show()
        }

        internal var name: TextView = itemView.findViewById<View>(R.id.notification_name) as TextView
        internal var time: TextView = itemView.findViewById<View>(R.id.notification_time) as TextView
        internal var type: TextView = itemView.findViewById<View>(R.id.notification_type) as TextView
        internal var link: TextView = itemView.findViewById<View>(R.id.notification_link) as TextView

        init {
            link.setOnClickListener(this)
        }
    }

    class ViewHeaderHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        internal var title = itemView.findViewById<View>(R.id.current_notif_title)
    }
}