package kz.mycrm.android.ui.intro.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.mycrm.android.R

/**
 * Created by Nurbek Kabylbay on 23.01.2018.
 */
class ThirdFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_third, container, false)
    }

}