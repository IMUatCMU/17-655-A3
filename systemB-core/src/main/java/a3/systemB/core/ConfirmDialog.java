package a3.systemB.core;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class ConfirmDialog extends JDialog {

    public ConfirmDialog(
            String title,
            int timeOutInSeconds,
            Runnable okCallback,
            Runnable cancelCallback,
            Runnable timeOutCallback) {
        super(new Frame(), title, true);
        this.setSize(200, 80);

        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        s.schedule(() -> {
            timeOutCallback.run();
            this.setVisible(false);
            this.dispose();
        }, timeOutInSeconds, TimeUnit.SECONDS);

        Toolkit aKit = this.getToolkit();
        Dimension screenSize = aKit.getScreenSize();
        this.setLocation((int) screenSize.getWidth() / 2 - this.getWidth() / 2,
                (int) screenSize.getHeight() / 2 - this.getHeight() / 2);

        JPanel pan=new JPanel();
        pan.setLayout(new FlowLayout());

        JButton okayButton = new JButton("OK");
        okayButton.addActionListener(e -> {
            s.shutdownNow();
            okCallback.run();
            this.setVisible(false);
            this.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            s.shutdownNow();
            cancelCallback.run();
            this.setVisible(false);
            this.dispose();
        });

        pan.add(okayButton);
        pan.add(cancelButton);
        this.add(pan);

        this.setVisible(true);
    }
}
