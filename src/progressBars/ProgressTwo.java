package progressBars;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa monitorująca postęp danej czynności
 */
public class ProgressTwo extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 40;

    private Timer cancelMonitor;
    private JButton startButton;
    private ProgressMonitor progressDialog;
    private JTextArea textArea;
    private SimulatedActivity activity;

    public ProgressTwo() {
        //Pole tekstowe, w którym prezentowane jest działanie wątku
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        textArea.setFont(new Font("System", Font.PLAIN, 14));
        textArea.setEditable(false);

        //Tworzy panel przycisków
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        buttonPanel.add(startButton);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        //Tworzy obiekt nasłuchujący przycisku
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            final int MAX = 1000;

            //Uruchmia symulację
            activity = new SimulatedActivity(MAX);
            activity.execute();

            //Uruchamia okno dialogowe monitora
            progressDialog = new ProgressMonitor(ProgressTwo.this, "Waiting for Simulated Activity", null, 0, MAX);
            cancelMonitor.start();
        });

        //Konfiguruje akcję licznika czasu
        cancelMonitor = new Timer(500, e -> {
            if (progressDialog.isCanceled()) {
                activity.cancel(true);
                startButton.setEnabled(true);
            } else {
                progressDialog.setProgress(activity.getProgress());
            }
        });
        pack();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno wskaźników postępu 2 - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

    private class SimulatedActivity extends SwingWorker<Void, Integer> {
        private int current;
        private int target;

        /**
         * Tworzy wątek symulowanej operacji. Zwiększa on wartość licznika
         *
         * @param target wartość docelowa licznika
         */
        public SimulatedActivity(int target) {
            current = 0;
            this.target = target;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                while (current < target) {
                    Thread.sleep(100);
                    current++;
                    textArea.append(current + "\n");
                    setProgress(current);
                }
            } catch (InterruptedException e) {}
            return null;
        }
    }
}
