package kz.mycrm.android.ui.division

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division

/**
 * Created by lab on 11/18/17.
 */
class DivisionAdapter(divisions : ArrayList<Division>, context: Context) : RecyclerView.Adapter<DivisionAdapter.ViewHolder>() {

    var divisions :ArrayList<Division>
    var context :Context

    init {
        this.divisions = divisions
        this.context = context
    }

    lateinit var view :View

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder!!.bind()

        holder!!.city.setText(divisions[position].cityName)
        holder!!.title.setText(divisions[position].name)
        holder!!.address.setText(divisions[position].address)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        view = LayoutInflater.from(parent!!.context).inflate(R.layout.division_item, parent, false)

        var vh :ViewHolder = ViewHolder(view)

        return vh
    }

    override fun getItemCount(): Int {
        return divisions.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.item_city)
        lateinit var city : TextView
        @BindView(R.id.item_title)
        lateinit var title : TextView
        @BindView(R.id.item_address)
        lateinit var address : TextView

        fun bind(){
            ButterKnife.bind(itemView)
        }

    }
}