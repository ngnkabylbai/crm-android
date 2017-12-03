package kz.mycrm.android.ui.division

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.ui.main.mainIntent

/**
 * Created by lab on 11/25/17.
 */

class DivisionAdapter(internal var divisions: List<Division>, internal var context: Context) : RecyclerView.Adapter<DivisionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_division, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.address.text = divisions[position].address
        holder.city.text = divisions[position].cityName
        holder.title.text = divisions[position].name
    }

    override fun getItemCount(): Int {
        return divisions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var address: TextView
        internal var title: TextView
        internal var city: TextView

        init {
            address = itemView.findViewById<View>(R.id.item_address) as TextView
            title = itemView.findViewById<View>(R.id.item_title) as TextView
            city = itemView.findViewById<View>(R.id.item_city) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?){
            itemView.context.startActivity(itemView.context.mainIntent())
            Toast.makeText(itemView.context, "call main activity on item clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
