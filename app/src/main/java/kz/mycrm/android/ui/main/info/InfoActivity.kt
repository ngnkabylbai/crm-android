package kz.mycrm.android.ui.main.info

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kz.mycrm.android.R
import kz.mycrm.android.ui.division.DivisionAdapter

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    @BindView(R.id.close_activity)
    lateinit var close: ImageView
    @BindView(R.id.info_time)
    lateinit var time:TextView

    lateinit var rv:RecyclerView
    lateinit var lm:LinearLayoutManager
    lateinit var adapter:ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ButterKnife.bind(this)

        rv = findViewById(R.id.service_list)
        rv.setHasFixedSize(true)
        lm = LinearLayoutManager(this)
        adapter = ServiceAdapter(serviceList, this)
        rv.setLayoutManager(lm)
        rv.setAdapter(adapter)
    }

    @OnClick(R.id.close_activity)
    override fun onClick(view: View?){
        finish()
    }
}
