package kz.mycrm.android.ui.main.notification

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Notification
import kz.mycrm.android.db.entity.Order
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lab on 11/25/17.
 */
class CurrentNotificationAdapter(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var TYPE_HEADER = 0
    private var TYPE_ITEM = 1

    private var notificationList: ArrayList<Notification> = ArrayList()

    private val targetFormat = SimpleDateFormat("d MMMM, H:mm", Locale.getDefault())


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
                    TYPE_HEADER -> {
                        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.header_cur_notification, parent, false)
                        ViewHeaderHolder(view)
                    }
                    else -> {
                        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_notification_visit, parent, false)
                        ViewItemHolder(view)
                    }
             }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHeaderHolder) {

        } else if(holder is ViewItemHolder) {
            val notification = notificationList[position-1]
            holder.title.text = notification.title
            holder.body.text = notification.body
            holder.time.text = targetFormat.format(notification.datetime).toLowerCase()
            holder.type.text = "Визит".toUpperCase()
        }
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
    fun add(notification: Notification) {
        notificationList.add(notification)
        notifyDataSetChanged()
    }

    fun clear() {
        notificationList.clear()
    }

    fun setListAndNotify(notificationList: ArrayList<Notification>) {
        this.notificationList = notificationList
        notifyDataSetChanged()
    }

    class ViewItemHolder(itemView :View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            Toast.makeText(itemView.context, "Go to map", Toast.LENGTH_SHORT).show()
        }

        internal var title: TextView = itemView.findViewById<View>(R.id.notification_title) as TextView
        internal var body: TextView = itemView.findViewById<View>(R.id.notification_body) as TextView
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