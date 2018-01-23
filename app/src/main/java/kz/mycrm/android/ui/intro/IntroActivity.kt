package kz.mycrm.android.ui.intro

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import kz.mycrm.android.ui.intro.fragments.FirstFragment
import kz.mycrm.android.ui.intro.fragments.SecondFragment
import kz.mycrm.android.ui.intro.fragments.ThirdFragment
import kz.mycrm.android.util.Logger

/**
 * Created by Nurbek Kabylbay on 23.01.2018.
 */
class IntroActivity : AppIntro() {

    private val firstFragment = FirstFragment()
    private val secondFragment = SecondFragment()
    private val thirdFragment = ThirdFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(firstFragment)
        addSlide(secondFragment)
        addSlide(thirdFragment)

        setVibrate(true)
        setVibrateIntensity(30)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        Logger.debug("Intro: Skipped")
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        Logger.debug("Intro: Done pressed")
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        Logger.debug("Intro: Slide changed")
    }

}