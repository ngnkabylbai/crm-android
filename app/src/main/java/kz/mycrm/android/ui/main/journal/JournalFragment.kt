package kz.mycrm.android.ui.main.journal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatSpinner
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarListener
import kz.mycrm.android.R
import java.text.DateFormat
import java.util.*

/**
 * Created by Nurbek Kabylbay on 23.11.2017.
 */
class JournalFragment : Fragment() {

    private lateinit var horizontalCalendar: HorizontalCalendar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** end after 2 weeks from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.WEEK_OF_MONTH, 2)

        /** start 2 weeks ago from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.WEEK_OF_MONTH, -2)

        val defaultDate = Calendar.getInstance()
        defaultDate.add(Calendar.MONTH, -1)
        defaultDate.add(Calendar.DAY_OF_WEEK, +5)

        horizontalCalendar = HorizontalCalendar.Builder(activity, R.id.calendarView)
                .startDate(startDate.time)
                .endDate(endDate.time)
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("d")
                .showDayName(true)
                .showMonthName(true)
                .datesNumberOnScreen(9)
                //                .defaultSelectedDate(new Date())
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Date, position: Int) {
                Toast.makeText(activity, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show()
                Log.d("Selected Item: ", DateFormat.getDateInstance().format(date))
            }

        }

        val spinner = view.findViewById<View>(R.id.divisionSpinner) as AppCompatSpinner
        spinner.adapter = ArrayAdapter.createFromResource(activity, R.array.spinnerItems, R.layout.item_journal_spinner)

        view.findViewById<View>(R.id.toDay).setOnClickListener(View.OnClickListener { horizontalCalendar.goToday(false) })
    }
}