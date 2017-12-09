package kz.mycrm.android.ui.main.journal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarListener
import kz.mycrm.android.R
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nurbek Kabylbay on 23.11.2017.
 */
class JournalFragment : Fragment() {

    private lateinit var viewModel: JournalViewModel
    private lateinit var horizontalCalendar: HorizontalCalendar
    private lateinit var token: String
    private lateinit var mDate: String
    private var divisionId = 49
    private var staffId = intArrayOf(748)

    private lateinit var dateFormat: SimpleDateFormat

    @BindView(R.id.date)
    lateinit var dateView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)

        viewModel = ViewModelProviders.of(this).get(JournalViewModel::class.java)
        viewModel.getToken().observe(this, Observer { token ->
            this.token = token!!.token
        })

        setupCalendar(view)
        setupSpinner(view)
    }


    private fun setupSpinner(view: View) {
        val spinner = view.findViewById<View>(R.id.divisionSpinner) as AppCompatSpinner
        val spinnerItems = ArrayList<String>()
        val adapter = ArrayAdapter<String>(activity, R.layout.item_journal_spinner, spinnerItems)
        spinner.adapter = adapter
        viewModel.getDivisions().observe(activity, Observer { list ->
            list!!.mapTo(spinnerItems) { it.name.toString() }
            adapter.notifyDataSetChanged()
        } )

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }
    }

    private fun setupCalendar(view: View) {
        dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        validDate()
        /** end after 2 weeks from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.WEEK_OF_MONTH, 2)

        /** start 2 weeks ago from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.WEEK_OF_MONTH, -2)

        horizontalCalendar = HorizontalCalendar.Builder(activity, R.id.calendarView)
                .startDate(startDate.time)
                .endDate(endDate.time)
                .dayNameFormat("EE")
                .dayNumberFormat("d")
                .showDayName(true)
                .showMonthName(true)
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Date, position: Int) {

                mDate = formatDate(date)
                viewModel.requestJournal(token, mDate, divisionId, staffId)
                        .observe(activity, Observer {orders ->
                            if(orders?.status == Status.SUCCESS){
                                Logger.debug("Journal success" + orders.toString())
                            } else {
                                Logger.debug("Journal status:"+orders?.status)
                            }
                        })
            }

        }
        view.findViewById<View>(R.id.toDay).setOnClickListener({ horizontalCalendar.goToday(false, true) })

    }

    private fun formatDate(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.YEAR).toString()+
                "-"+String.format("%02d",cal.get(Calendar.MONTH)+1)+
                "-"+String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))
    }

    private fun validDate() {
        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date

        val dayName = SimpleDateFormat("EEEE", Locale.getDefault())
        val monthName = SimpleDateFormat("MMMM", Locale.getDefault())
        val str = String.format("Сегодня %s, %d %s %dг.", dayName.format(cal.time).toLowerCase(),
                cal.get(Calendar.DAY_OF_MONTH), monthName.format(cal.time).toLowerCase(), cal.get(Calendar.YEAR))
        dateView.text = str
    }

}