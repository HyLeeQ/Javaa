package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ClockApp extends JFrame {
	private JLabel clockLabel;
	private JTextField timezoneTextField;

	public ClockApp() {
		setTitle("Clock App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);

		// Panel for the clock
		JPanel clockPanel = new JPanel();
		clockPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		clockLabel = new JLabel();
		clockPanel.add(clockLabel);

		// Panel for the timezone input and button
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		timezoneTextField = new JTextField(10);
		JButton createClockButton = new JButton("Create Clock");
		createClockButton.addActionListener(new CreateClockButtonListener());
		inputPanel.add(new JLabel("Timezone: "));
		inputPanel.add(timezoneTextField);
		inputPanel.add(createClockButton);

		// Main frame layout
		setLayout(new BorderLayout());
		add(clockPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);

		// Start the clock
		startClock();

		setVisible(true);
	}

	private void startClock() {
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				clockLabel.setText(formatter.format(now));
			}
		});
		timer.start();
	}

	private class CreateClockButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String timezone = timezoneTextField.getText();
			TimeZoneClock newClock = new TimeZoneClock(timezone);
			newClock.start();
		}
	}

	private class TimeZoneClock extends Thread {
		private String timezone;
		public TimeZoneClock(String timezone) {
			this.timezone = timezone;
		}

		@Override
		public void run() {
			while (true) {
				ZoneId zoneId = ZoneId.of(timezone);
				ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedTime = formatter.format(zonedDateTime);
				System.out.println("Time in " + timezone + ": " + formattedTime);

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ClockApp();
			}
		});
	}
}