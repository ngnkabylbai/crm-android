package kz.mycrm.android.ui.info

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kz.mycrm.android.R

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    @BindView(R.id.close_activity)
    lateinit var close: ImageView
    @BindView(R.id.info_time)
    lateinit var time:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.close_activity)
    override fun onClick(view: View?){
        finish()
    }
}
