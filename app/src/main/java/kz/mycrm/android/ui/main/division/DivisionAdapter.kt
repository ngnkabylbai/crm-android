package kz.mycrm.android.ui.main.division

import android.app.Activity
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

class DivisionAdapter(internal var context: Context) : RecyclerView.Adapter<DivisionAdapter.ViewHolder>() {

    private var divisionList = ArrayList<Division>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_division, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.division = divisionList[position]
        holder.address.text = divisionList[position].address
        holder.city.text = divisionList[position].cityName
        holder.title.text = divisionList[position].name
    }

    override fun getItemCount(): Int {
        return divisionList.size
    }

    fun add(division: Division) {
        divisionList.add(division)
        notifyDataSetChanged()
    }

    fun clear() {
        divisionList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var address: TextView = itemView.findViewById<View>(R.id.item_address) as TextView
        internal var title: TextView = itemView.findViewById<View>(R.id.item_title) as TextView
        internal var city: TextView = itemView.findViewById<View>(R.id.item_city) as TextView
        internal lateinit var division: Division

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?){
            val intent = itemView.context.mainIntent()
            val divisionId = division.id
            val staffId = division.staff?.id ?: "-1"

            intent.putExtra("division_id", divisionId)
            intent.putExtra("staff_id", staffId)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).finish()
        }
    }
}
