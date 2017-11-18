package kz.mycrm.android.ui.division

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R

class DivisionActivity : AppCompatActivity() {

    private lateinit var divisionViewModel : DivisionViewModel

    @BindView(R.id.rvDivisions)
    lateinit var rvDivisions : RecyclerView

    @BindView(R.id.tvTitile)
    lateinit var title : TextView

    private lateinit var adapter : Adapter
    private lateinit var lm : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_division)

        ButterKnife.bind(this)

        rvDivisions.setHasFixedSize(true)

        lm = LinearLayoutManager(this)
        adapter = DivisionAdapter()

    }
}
