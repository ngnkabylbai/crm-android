package kz.mycrm.android.ui.division

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
class DivisionFragment : Fragment() {

    lateinit var divisions :ArrayList<Division>

    private lateinit var divisionViewModel : DivisionViewModel

    @BindView(R.id.rvDivisions)
    lateinit var rvDivisions : RecyclerView

    @BindView(R.id.tvTitile)
    lateinit var title : TextView

    private lateinit var adapter : DivisionAdapter
    private lateinit var lm : LinearLayoutManager

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Override
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view :View = inflater!!.inflate(R.layout.division_fragment, container, false)

        ButterKnife.bind(view)

        rvDivisions.setHasFixedSize(true)

        lm = LinearLayoutManager(view.context)
        adapter = DivisionAdapter(divisions, view.context)

        return view

    }
}