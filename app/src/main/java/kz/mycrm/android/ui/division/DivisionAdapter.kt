package kz.mycrm.android.ui.division

import android.content.Context
import android.support.v7.widget.RecyclerView
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
class DivisionAdapter(divisions : ArrayList<Division>, context: Context) : RecyclerView.Adapter<DivisionAdapter.ViewHolder>(divisions, context) {

    lateinit var divisions : ArrayList<Division>

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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