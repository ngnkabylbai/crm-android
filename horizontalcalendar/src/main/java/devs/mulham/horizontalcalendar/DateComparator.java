package devs.mulham.horizontalcalendar;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Nurbek Kabylbay on 22.11.2017.
 */

public class DateComparator implements Comparator<Date> {
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public int compare(Date d1, Date d2) {
        return DATE_FORMAT.format(d1).compareTo(DATE_FORMAT.format(d2));
    }
}
