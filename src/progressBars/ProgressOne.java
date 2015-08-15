package progressBars;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ramka zawierająca przycisk uruchamiający
 * symulację czasochłoddenj operacji oraz pasek postępu
 * i pole tekstowe
 */
public class ProgressOne extends JFrame {
    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 40;

    private JButton startButton;
    private JProgressBar progressBar;
    private JCheckBox checkBox;
    private JTextArea textArea;
    private SimulatedActivity activity;

    public ProgressOne() {
        //Pole tekstowe w którym prezentowane jest działanie wątku
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        textArea.setFont(new Font("System", Font.PLAIN, 14));
        textArea.setEditable(false);

        //Panel zawierjący przycisk i pasek postępu
        final int MAX = 1000;
        JPanel panel = new JPanel();
        startButton = new JButton("Start");
        progressBar = new JProgressBar(0, MAX);
        progressBar.setStringPainted(true);
        panel.add(startButton);
        panel.add(progressBar);

        checkBox = new JCheckBox("Indeterminate");
        checkBox.addActionListener(e -> {
            progressBar.setIndeterminate(checkBox.isSelected());
            progressBar.setStringPainted(!progressBar.isIndeterminate());
        });
        panel.add(checkBox);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        //Dodaje obiekt nasłuchujący przycisku
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            activity = new SimulatedActivity(MAX);
            activity.execute();
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno wskaźników postępu 1 - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

    //Klasa SwingWorker wywołuje metodę process w wątku rozdziału zdarzeń,
    //co umożliwia bezpieczną aktualizację paska postępu.
    private class SimulatedActivity extends SwingWorker<Void, Integer> {
        private int current;
        private int target;

        /**
         * Tworzy wątek symulowanej operacji. Zwiększa on wartość licznika
         * do momentu osiągnięcia wartości docelowej.
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
                    publish(current);
                }
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            for(Integer chunk : chunks) {
                textArea.append(chunk + " . . .\n");
                progressBar.setValue(chunk);
            }
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
        }
    }
}
