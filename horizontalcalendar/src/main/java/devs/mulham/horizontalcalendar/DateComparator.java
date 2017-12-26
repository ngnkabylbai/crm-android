package devs.mulham.horizontalcalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nurbek Kabylbay on 22.11.2017.
 */

public class DateComparator implements Comparator<Date> {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public int compare(Date d1, Date d2) {
        try {
            return DATE_FORMAT.parse(DATE_FORMAT.format(d1)).compareTo(DATE_FORMAT.parse(DATE_FORMAT.format(d2)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
