package kz.mycrm.android.ui.main.journal

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_journal.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Order
import kz.mycrm.android.ui.main.info.infoIntent
import kz.mycrm.android.ui.view.JournalView
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Nurbek Kabylbay on 23.11.2017.
 */
class JournalFragment : Fragment(), JournalView.OrderEventClickListener {

    private lateinit var viewModel: JournalViewModel
    private lateinit var horizontalCalendar: HorizontalCalendar
    private lateinit var mDate: String
    private var mDivisionId = -1
    private lateinit var mStaffId: IntArray

    private lateinit var dateFormat: SimpleDateFormat
    private val spinnerItems = ArrayList<String>()
    private val divisionList = ArrayList<Division>()

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journal.setOnEventClickListener(this)
        viewModel = ViewModelProviders.of(this).get(JournalViewModel::class.java)

        mDivisionId = arguments.getInt("division_id")
        val division = viewModel.getDivisionById(mDivisionId)
            mDivisionId = division.id
            mStaffId = intArrayOf(division.staff?.id?.toInt() ?: 0)

        adapter = ArrayAdapter(activity, R.layout.item_journal_spinner, spinnerItems)
        divisionSpinner.adapter = adapter

        viewModel.getOrderLis().observe(activity, Observer {orders ->
            when(orders!!.status) {
                Status.LOADING -> onLoading(orders)
                Status.SUCCESS -> onSuccess(orders)
                Status.ERROR -> onError()
            }
        })

        setupCalendar(view)
        setupSpinner()

        updateAndRefreshJournal(mDate, mDivisionId, mStaffId)
    }

    private fun setupSpinner() {
        val list = viewModel.getDivisions()
        divisionList.clear()
        spinnerItems.clear()

        list.mapTo(spinnerItems) { it.name!! }
        for(d in list) {
            if(d.id == mDivisionId) {
                divisionSpinner.setSelection(spinnerItems.indexOf(d.name))
            }
            divisionList.add(d)
        }
        adapter.notifyDataSetChanged()

        divisionSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                mDivisionId = divisionList[position].id
                divisionSpinner.setSelection(position)
                updateAndRefreshJournal(null, mDivisionId, null)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }
    }

    private fun setupCalendar(view: View) {
        dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        mDate = dateFormat.format(Date())
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
                updateAndRefreshJournal(mDate, null, null)
            }

        }
        view.findViewById<View>(R.id.toDay).setOnClickListener({ horizontalCalendar.goToday(false, true) })

    }

    private fun onLoading(orders: Resource<List<Order>>) {
        onSuccess(orders)
    }

    private fun onSuccess(orders: Resource<List<Order>>) {
        if(orders.data != null)
            journal?.updateEventsAndInvalidate(orders.data as ArrayList<Order>, orders.status)
    }

    private fun onError() {
        Toast.makeText(activity, "Произошла ошибка", Toast.LENGTH_SHORT).show()
    }

    private fun updateAndRefreshJournal(date: String?, divisionId: Int?, staffId: IntArray?) {
        viewModel.updateData(date ?: mDate, divisionId ?: mDivisionId, staffId ?: mStaffId)
        viewModel.startRefresh()
    }

    private fun formatDate(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.YEAR).toString()+
                "-"+String.format("%02d",cal.get(Calendar.MONTH)+1)+
                "-"+String.format("%02d",cal.get(Calendar.DAY_OF_MONTH))
    }

    private fun validDate() {
        val dateTime = Date()
        val cal = Calendar.getInstance()
        cal.time = dateTime

        val dayName = SimpleDateFormat("EEEE", Locale.getDefault())
        val monthName = SimpleDateFormat("MMMM", Locale.getDefault())
        val str = String.format("Сегодня %s, %d %s %dг.", dayName.format(cal.time).toLowerCase(),
                cal.get(Calendar.DAY_OF_MONTH), monthName.format(cal.time).toLowerCase(), cal.get(Calendar.YEAR))
        dateView.text = str
    }

    override fun onOrderEventClicked(order: Order) {
        super.onOrderEventClicked(order)
        val intent: Intent = activity.infoIntent()
            intent.putExtra("id", order.id)
        startActivity(intent)
        Logger.debug("Event clicked:" + order.toString())
    }
}