package a3.monitor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.InitializingBean;

/**
 * Indicator UI. Provides configuration options to for fix positions and color
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class Indicator extends JFrame implements MonitorUI, InitializingBean {

    private int Height;
    private int UpperLeftX;
    private int UpperLeftY;
    private String MessageLabel;
    private Color IluminationColor = Color.black;
    private Color oldIluminationColor;

    private Color TextColor = Color.black;
    private JFrame IndicatorWindow;

    private JPanel panel;
    private JLabel textLabel;

    @Override
    public void afterPropertiesSet() throws Exception {
        displayUI();
    }

    @Override
    public void displayUI() {
        validate();
        repaint();
        setVisible(true);
    }

    public Indicator(String Label, float Xpos, float Ypos) {

        MessageLabel = Label;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        Toolkit aKit = this.getToolkit();
        Dimension ScreenSize = aKit.getScreenSize();

        // Size up the indicators. They width and height is 1% of the
        // screen height or width (which ever is larger).

        if (ScreenSize.width >= ScreenSize.height) {
            Height = (int) (ScreenSize.height * 0.1);

        } else {

            Height = (int) (ScreenSize.width * 0.1);

        } // if

		/* Calculate the X and Y position of the window's upper left
        ** hand corner as a proportion of the screen
		*/

        UpperLeftX = (int) (ScreenSize.width * Xpos);
        UpperLeftY = (int) (ScreenSize.height * Ypos);

        setBounds(UpperLeftX, UpperLeftY, Height, Height);
        setVisible(true);
        Graphics g = getGraphics();

        this.panel = new JPanel();
        this.panel.setLocation(0, 0);
        this.panel.setSize(this.getSize());
        this.add(this.panel);

        this.textLabel = new JLabel(Label);
        this.textLabel.setForeground(Color.WHITE);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setSize(this.panel.getWidth(), this.panel.getHeight() / 3);
        this.textLabel.setLocation(0, this.panel.getHeight() / 3);
        this.panel.add(this.textLabel);

        render();

    } // constructor

    /***************************************************************************
     * Constructor:: Indicator Purpose: This method sets up a JFrame window and drawing pane with
     * the title specified at the position indicated by the x, y coordinates.
     *
     * Arguments: String Label - the indicator title int Xpos - the vertical position of the
     * indicator on the screen specified in pixels. int Ypos - the horizontal position of the
     * indicator on the screen specified in pixels.
     *
     * Returns: Indicator
     *
     * Exceptions: none
     ****************************************************************************/

    public Indicator(String Label, int Xpos, int Ypos) {
        MessageLabel = Label;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        Toolkit aKit = this.getToolkit();
        Dimension ScreenSize = aKit.getScreenSize();

        // Size up the indicators. They width and height is 1% of the
        // screen height or width (which ever is larger).

        if (ScreenSize.width >= ScreenSize.height) {
            Height = (int) (ScreenSize.height * 0.1);

        } else {

            Height = (int) (ScreenSize.width * 0.1);

        } // if

        UpperLeftX = Xpos;
        UpperLeftY = Ypos;

        setBounds(Xpos, Ypos, Height, Height);
        setVisible(true);
        Graphics g = getGraphics();

        this.panel = new JPanel();
        this.panel.setLocation(0, 0);
        this.panel.setSize(this.getSize());
        this.add(this.panel);

        this.textLabel = new JLabel(Label);
        this.textLabel.setForeground(Color.WHITE);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setSize(this.panel.getWidth(), this.panel.getHeight() / 3);
        this.textLabel.setLocation(0, this.panel.getHeight() / 3);
        this.panel.add(this.textLabel);

        render();

    } // constructor

    /***************************************************************************
     * Constructor:: Indicator Purpose: This method sets up a JFrame window and drawing pane with
     * the title specified at the position indicated by the x, y coordinates.
     *
     * Arguments: String Label - the indicator title Float Xpos - the vertical position of the
     * indicator on the screen specified in terms of a percentage of the screen width. Float Ypos -
     * the horizontal position of the indicator on the screen specified in terms of a percentage of
     * the screen height.
     *
     * int InitialColor - specifies the color that the indicator should be on startup.
     *
     * Returns: Indicator
     *
     * Exceptions: none
     ****************************************************************************/

    public Indicator(String Label, int InitialColor, int x, int y, int width, int height) {
        MessageLabel = Label;

        switch (InitialColor) {
            case 0:
                IluminationColor = Color.black;
                break;

            case 1:
                IluminationColor = Color.green;
                break;

            case 2:
                IluminationColor = Color.yellow;
                break;

            case 3:
                IluminationColor = Color.red;
                break;

        } // switch

        this.setResizable(false);
        this.setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);

        Height = height;
        UpperLeftX = x;
        UpperLeftY = y;

        setBounds(x, y, width, height);
        setVisible(true);
        Graphics g = getGraphics();

        this.panel = new JPanel();
        this.panel.setLocation(0, 0);
        this.panel.setSize(this.getSize());
        this.add(this.panel);

        this.textLabel = new JLabel(Label);
        this.textLabel.setForeground(Color.WHITE);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setSize(this.panel.getWidth(), this.panel.getHeight() / 3);
        this.textLabel.setLocation(0, this.panel.getHeight() / 3);
        this.panel.add(this.textLabel);

        render();
    }

    public Indicator(String Label, float Xpos, float Ypos, int InitialColor) {
        MessageLabel = Label;

        switch (InitialColor) {
            case 0:
                IluminationColor = Color.black;
                break;

            case 1:
                IluminationColor = Color.green;
                break;

            case 2:
                IluminationColor = Color.yellow;
                break;

            case 3:
                IluminationColor = Color.red;
                break;

        } // switch

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        Toolkit aKit = this.getToolkit();
        Dimension ScreenSize = aKit.getScreenSize();

        if (ScreenSize.width >= ScreenSize.height) {
            Height = (int) (ScreenSize.height * 0.1);

        } else {

            Height = (int) (ScreenSize.width * 0.1);

        } // if

		/* Calculate the X and Y position of the window's upper left
		** hand corner as a proportion of the screen
		*/

        UpperLeftX = (int) (ScreenSize.width * Xpos);
        UpperLeftY = (int) (ScreenSize.height * Ypos);

        setBounds(UpperLeftX, UpperLeftY, Height, Height);
        setVisible(true);
        Graphics g = getGraphics();

        this.panel = new JPanel();
        this.panel.setLocation(0, 0);
        this.panel.setSize(this.getSize());
        this.add(this.panel);

        this.textLabel = new JLabel(Label);
        this.textLabel.setForeground(Color.WHITE);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setSize(this.panel.getWidth(), this.panel.getHeight() / 3);
        this.textLabel.setLocation(0, this.panel.getHeight() / 3);
        this.panel.add(this.textLabel);

        render();

    } // constructor


    /***************************************************************************
     * Constructor:: Indicator Purpose: This method sets up a JFrame window and drawing pane with
     * the title specified at the position indicated by the x, y coordinates.
     *
     * Arguments: String Label - the indicator title int Xpos - the vertical position of the
     * indicator on the screen specified in pixels. int Ypos - the horizontal position of the
     * indicator on the screen specified in pixels. int InitialColor - specifies the color that the
     * indicator should be on startup.
     *
     * Returns: Indicator
     *
     * Exceptions: none
     ****************************************************************************/

    public Indicator(String Label, int Xpos, int Ypos, int InitialColor) {
        MessageLabel = Label;

        switch (InitialColor) {
            case 0:
                IluminationColor = Color.black;
                break;

            case 1:
                IluminationColor = Color.green;
                break;

            case 2:
                IluminationColor = Color.yellow;
                break;

            case 3:
                IluminationColor = Color.red;
                break;

        } // switch

        //setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.lightGray);
        Toolkit aKit = this.getToolkit();
        Dimension ScreenSize = aKit.getScreenSize();

        if (ScreenSize.width >= ScreenSize.height) {
            Height = (int) (ScreenSize.height * 0.1);

        } else {

            Height = (int) (ScreenSize.width * 0.1);

        } // if

        UpperLeftX = Xpos;
        UpperLeftY = Ypos;


        setBounds(Xpos, Ypos, Height, Height);
        setVisible(true);

        this.panel = new JPanel();
        this.panel.setLocation(0, 0);
        this.panel.setSize(this.getSize());
        this.add(this.panel);

        this.textLabel = new JLabel(Label);
        this.textLabel.setForeground(Color.WHITE);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setSize(this.panel.getWidth(), this.panel.getHeight() / 3);
        this.textLabel.setLocation(0, this.panel.getHeight() / 3);
        this.panel.add(this.textLabel);

        render();

    } // constructor

    private void render() {
        if (this.panel != null)
            this.panel.setBackground(IluminationColor);

        if (this.textLabel != null) {
            this.textLabel.setText(MessageLabel);
            if (Color.BLACK.equals(IluminationColor))
                this.textLabel.setForeground(Color.WHITE);
            else
                this.textLabel.setForeground(Color.BLACK);
        }
    }

    /***************************************************************************
     * CONCRETE Class:: GetX Purpose: This method returns the X (vertical) position of the window in
     * pixels
     *
     * Arguments: none
     *
     * Returns: integer length in pixels
     *
     * Exceptions: none
     ****************************************************************************/

    public int GetX() {
        return (UpperLeftX);

    } // GetX

    /***************************************************************************
     * CONCRETE Class:: GetY Purpose: This method returns the Y (horizontal) position of the window
     * in pixels
     *
     * Arguments: none
     *
     * Returns: integer length in pixels
     *
     * Exceptions: none
     ****************************************************************************/

    public int GetY() {
        return (UpperLeftY);

    } // GetY

    /***************************************************************************
     * CONCRETE Class:: Height Purpose: This method returns the vertical height of the window
     *
     * Arguments: none
     *
     * Returns: integer length in pixels
     *
     * Exceptions: none
     ****************************************************************************/

    public int Height() {
        return (this.Height);

    } // Height

    /***************************************************************************
     * CONCRETE Class:: Width Purpose: This method returns the horizontal length of the window.
     *
     * Arguments: none
     *
     * Returns: integer length in pixels
     *
     * Exceptions: none
     ****************************************************************************/

    public int Width() {
        return (this.Height);

    } // Width

    /***************************************************************************
     * CONCRETE Class::SetLampColorAndMessage Purpose: This method will change both the indicator
     * lamp color and the indicator label to the specified color and label. The display text is
     * always black.
     *
     * Arguments: String s - the new indicator label int c - the new indicator color where 0=black,
     * 1=green, 2=yellow, and 3=red
     *
     * Returns: none
     *
     * Exceptions: none
     ****************************************************************************/

    public void SetLampColorAndMessage(String s, int c) {
        switch (c) {
            case 0:
                IluminationColor = Color.black;
                break;

            case 1:
                IluminationColor = Color.green;
                break;

            case 2:
                IluminationColor = Color.yellow;
                break;

            case 3:
                IluminationColor = Color.red;
                break;

        } // switch

        MessageLabel = s;

        render();
        repaint();

    } // SetLampColor

    /***************************************************************************
     * CONCRETE Class::SetLampColor Purpose: This method will change the indicator lamp color to the
     * specified color.
     *
     * Arguments: int c - the new indicator color where 0=black, 1=green, 2=yellow, and 3=red
     *
     * Returns: none
     *
     * Exceptions: none
     ****************************************************************************/

    public void SetLampColor(int c) {
        switch (c) {
            case 0:
                IluminationColor = Color.black;
                break;

            case 1:
                IluminationColor = Color.green;
                break;

            case 2:
                IluminationColor = Color.yellow;
                break;

            case 3:
                IluminationColor = Color.red;
                break;

        } // switch

    } // SetLampColor

    /***************************************************************************
     * CONCRETE Class::SetMessage Purpose: This method will change both the indicator label to the
     * specified label string. The display text is always black.
     *
     * Arguments: String s - the new indicator label
     *
     * Returns: none
     *
     * Exceptions: none
     ****************************************************************************/

    public void SetMessage(String m) {
        MessageLabel = m;
        repaint();

    } // SetMessage

    /***************************************************************************
     * CONCRETE Class::paint Purpose: The paint() method is an overridden method inherited from
     * JFrame that draws the indicator on the screen. This method must be overridden so that various
     * JRE services can update the display. If you do not override paint, the indicator will not be
     * consistiently drawn to the screen and may have various graphics disappear at run time. This
     * method is invoked indirectly by the repaint() method.
     *
     * Arguments: Graphics g this is the indicator's graphic instance.
     *
     * Returns: none
     *
     * Exceptions: none
     ****************************************************************************/
    private int paintCount = 0;

    @Override
    public void paint(Graphics g) {
        render();
        super.paint(g);
    } // paint

    private static class BulbAndText extends JPanel {

        private JLabel bulb;
        private JLabel text;

        public BulbAndText(Color initialColor, String text) {
            super();
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            this.bulb = new JLabel();
            this.bulb.setSize(50, 50);
            this.bulb.setBackground(initialColor);
            this.add(this.bulb);

            this.text = new JLabel(text);
            this.add(this.text);

            this.setVisible(true);
        }

        public void changeColor(Color color) {
            this.bulb.setBackground(color);
        }
    }
}
