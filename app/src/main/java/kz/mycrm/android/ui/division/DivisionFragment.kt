package kz.mycrm.android.ui.division

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.ui.main.MainViewModel
import kz.mycrm.android.util.Logger

/**
 * Created by lab on 11/18/17.
 */
class DivisionFragment : Fragment() {

//    lateinit var divisions :List<Division>

    private lateinit var divisionViewModel : DivisionViewModel
    private lateinit var sharedViewModel : MainViewModel

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
        rvDivisions = view.findViewById(R.id.rvDivisions)
        rvDivisions.setHasFixedSize(true)

        divisionViewModel = ViewModelProviders.of(this).get(DivisionViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)

        divisionViewModel.getDivisions().observe(this, Observer { divisions->
            if (divisions != null){
                lm = LinearLayoutManager(view.context)
                adapter = DivisionAdapter(divisions, view.context)
                rvDivisions.setAdapter(adapter)
                Logger.debug("Adapter" + divisions)
            }
        })

//        sharedViewModel.requestTokenFromDB().observe(this, Observer { token->
//            if (token != null){
//
//
//                divisionViewModel.unpackResource(divisionViewModel.requestUserDivisions(token.token, null))
//                        ?.observe(this, Observer { divisionList->
//                            lm = LinearLayoutManager(view.context)
//                            adapter = DivisionAdapter(divisionList!!, view.context)
//                            rvDivisions.setAdapter(adapter)
//                        })
//
//                Log.d("Tag", "requesting division with token: " + token.token)
//            }else{
//                Log.d("Tag", "Error")
//            }
//        })

        return view

    }
}