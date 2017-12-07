package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.util.Logger

fun Context.divisionsIntent(): Intent {
    return Intent(this, DivisionsActivity::class.java)
}

class DivisionsActivity : AppCompatActivity() {

    private lateinit var divisionViewModel : DivisionViewModel

    lateinit var rvDivisions : RecyclerView

    @BindView(R.id.tvTitile)
    lateinit var title : TextView

    private lateinit var adapter : DivisionAdapter
    private lateinit var lm : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divisions)

        ButterKnife.bind(this)
        rvDivisions = findViewById(R.id.rvDivisions)
        rvDivisions.setHasFixedSize(true)

        divisionViewModel = ViewModelProviders.of(this).get(DivisionViewModel::class.java)

        divisionViewModel.getToken().observe(this, Observer { token->
            if (token != null){

                divisionViewModel.loadUserDivisions(token.token, null).observe(this,
                        Observer { resourceDivisionList->
                            if (resourceDivisionList != null){
                                if (resourceDivisionList.data != null){
                                    Logger.debug("resource" + resourceDivisionList.data.size)
                                    if (resourceDivisionList.data.size > 1){
                                        Toast.makeText(this, "Here must be list of divisions", Toast.LENGTH_SHORT).show()
                                    }else{
                                        Logger.debug("resourceHere")
                                        Toast.makeText(this, "Here must be opened main activity", Toast.LENGTH_SHORT).show()
//                                        startActivity(mainIntent())
                                    }
                                    lm = LinearLayoutManager(this)
                                    adapter = DivisionAdapter(resourceDivisionList.data, this)
                                    rvDivisions.setLayoutManager(lm)
                                    rvDivisions.setAdapter(adapter)
                                }
                            }
                        })
            }
        })
    }
}
