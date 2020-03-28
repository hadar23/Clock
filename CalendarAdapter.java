import java.util.Calendar;

// Object Adapter
public class CalendarAdapter {

	private Calendar calendar;

	// Constructor
	public CalendarAdapter(Calendar calendar) {
		this.calendar = calendar;
	}

	// get current hour
	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	// get current minute
	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	// get current second
	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}
}
