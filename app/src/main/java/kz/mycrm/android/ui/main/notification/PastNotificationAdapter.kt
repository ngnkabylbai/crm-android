package kz.mycrm.android.ui.main.notification

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kz.mycrm.android.R

/**
 * Created by lab on 11/25/17.
 */
class PastNotificationAdapter(context: Context): RecyclerView.Adapter<PastNotificationAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_division, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
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