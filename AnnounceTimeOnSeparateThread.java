import java.applet.*;

public class AnnounceTimeOnSeparateThread implements Runnable {
	// Create audio clips for every minute and hour
	protected AudioClip[] hourAudio = new AudioClip[12];
	protected AudioClip[] minuteAudio = new AudioClip[60];
	// Create audio clips for pronouncing am and pm
	protected AudioClip amAudio = Applet.newAudioClip(this.getClass().getResource("/audio/am.au"));
	protected AudioClip pmAudio = Applet.newAudioClip(this.getClass().getResource("/audio/pm.au"));
	// hour and minute to announce
	private int hour = 0, minute = 0;

	public AnnounceTimeOnSeparateThread() {
		init();
	}

	/** Initialize the applet */
	public void init() {
		// Create audio clips for pronouncing hours
		for (int i = 0; i < 12; i++)
			hourAudio[i] = Applet.newAudioClip(this.getClass().getResource("/audio/hour" + i + ".au"));
		// Create audio clips for pronouncing minutes
		for (int i = 0; i < 60; i++)
			minuteAudio[i] = Applet.newAudioClip(this.getClass().getResource("/audio/minute" + i + ".au"));
	}

	// set hour to announce
	public void setHour(int hour) {
		this.hour = hour;
	}

	// set minute to announce
	public void setMinute(int minute) {
		this.minute = minute;
	}

	@Override
	public void run() {
		try { // Announce hour
			hourAudio[hour % 12].play();
			// Time delay to allow hourAudio play to finish
			Thread.sleep(1500);
			// Announce minute
			minuteAudio[minute].play();
			// Time delay to allow minuteAudio play to finish
			Thread.sleep(1500);
		} catch (InterruptedException ex) {
		}
		// Announce am or pm
		if (hour < 12)
			amAudio.play();
		else
			pmAudio.play();
	}
}
