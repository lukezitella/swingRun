package swingRun;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class start extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID= 1L;

    public JTextField descriptor;

    private int year;

    private int month;

    private int day;

    private int time;

    private String pace;

    private float mileage;

    private ChartPanel chart= null;

    public JButton b1;

    public JComboBox<String[]> yearList;

    public JComboBox<String[]> monthList;

    public JPanel panel4;

    private boolean mileGood= true;

    private boolean paceGood= true;

    private boolean timeGood= true;

    public start() {

        setLayout(new BorderLayout());

        // input bar
        JTextField text= new JTextField(100);
        text.addActionListener(this);
        text.setActionCommand("entryField");

        // displays what to enter
        descriptor= new JTextField(40);
        descriptor.setText("Enter the year of the run");
        descriptor.setEditable(false);

        // delete button
        b1= new JButton("delete latest run");
        b1.addActionListener(this);
        b1.setActionCommand("button");
        b1.setMnemonic(KeyEvent.VK_D);

        // years combo box
        String[] yearStrings= { "2000", "2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010",
                "2011", "2012", "2013", "2014", "2015",
                "2016", "2017", "2018", "2019", "2020",
                "2021", "2022", "2023", "2024", "2025",
                "2026", "2027", "2028", "2029", "2030",
        };
        yearList= new JComboBox(yearStrings);
        yearList.setSelectedIndex(21);
        yearList.addActionListener(this);
        yearList.setActionCommand("yearList");

        // months combo box
        String[] monthStrings= { "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" };
        monthList= new JComboBox(monthStrings);
        monthList.setSelectedIndex(0);
        monthList.addActionListener(this);
        monthList.setActionCommand("monthList");

        // check box for mileage
        JCheckBox mileCheck= new JCheckBox("Mileage");
        mileCheck.addActionListener(this);
        mileCheck.setActionCommand("mileCheck");
        mileCheck.setSelected(true);

        // check box for pace
        JCheckBox paceCheck= new JCheckBox("Pace");
        paceCheck.addActionListener(this);
        paceCheck.setActionCommand("paceCheck");
        paceCheck.setSelected(true);

        // check box for time
        JCheckBox timeCheck= new JCheckBox("Time");
        timeCheck.addActionListener(this);
        timeCheck.setActionCommand("timeCheck");
        timeCheck.setSelected(true);

        // calories burned display
        JTextField calorimeter= createCalorimeter();

        // add panel 1 components
        JPanel panel= new JPanel();
        panel.add(text);
        panel.add(descriptor);
        panel.add(b1);

        // add panel 2 components
        JPanel panel2= new JPanel();
        panel2.add(monthList);
        panel2.add(yearList);

        // add panel 3 components
        JPanel panel3= new JPanel();
        panel3.add(mileCheck);
        panel3.add(timeCheck);
        panel3.add(paceCheck);

        // add panel 4 components
        JPanel panel4= new JPanel();
        panel4.add(calorimeter);

        // creates and displays graph of runs
        createGraph(1, 2021);

        // add panels to frame
        add(panel3);
        add(panel, BorderLayout.LINE_START);
        add(panel2, BorderLayout.PAGE_START);
        add(panel4);
    }

    // starts the gui
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame= new JFrame("swingRun");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new start());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // starts the app and starts the gui by calling createAndShowGUI()
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    // controls actions events for each component of the app
    @Override
    public void actionPerformed(ActionEvent e) {

        // check boxes toggle on and off
        if (e.getActionCommand().equals("mileCheck")) {
            switchMile();
            createGraph(intFromMonth((String) monthList.getSelectedItem()),
                Integer.parseInt((String) yearList.getSelectedItem()));
        }
        if (e.getActionCommand().equals("paceCheck")) {
            switchPace();
            createGraph(intFromMonth((String) monthList.getSelectedItem()),
                Integer.parseInt((String) yearList.getSelectedItem()));
        }
        if (e.getActionCommand().equals("timeCheck")) {
            switchTime();
            createGraph(intFromMonth((String) monthList.getSelectedItem()),
                Integer.parseInt((String) yearList.getSelectedItem()));
        }

        // combo boxes. changes graph when the month or year changes
        if (e.getActionCommand() == "yearList") {
            JComboBox<String[]> bruh= (JComboBox<String[]>) e.getSource();
            String year= (String) bruh.getSelectedItem();
            int newYear= Integer.parseInt(year);
            createGraph(intFromMonth((String) monthList.getSelectedItem()), newYear);
        }

        if (e.getActionCommand() == "monthList") {
            JComboBox<String[]> bruh= (JComboBox<String[]>) e.getSource();
            String month= (String) bruh.getSelectedItem();
            int newMonth= intFromMonth(month);
            createGraph(newMonth, Integer.parseInt((String) yearList.getSelectedItem()));
        }

        // text fields for the user to enter Runs into
        if (e.getActionCommand().equals("entryField")) {

            String dText= descriptor.getText();

            JTextField text1= (JTextField) e.getSource();

            String text= text1.getText();

            text1.setText("");
            boolean bool= false;
            int number= 0;
            boolean miles= false;
            float numberAlt= 0;
            try {
                number= Integer.parseInt(text);
                if (dText.equals("Enter the year of the run") && number >= 2000 && number <= 2030) {
                    bool= true;
                }
                if (dText.equals("Enter the month of the run") && number >= 1 && number <= 12) {
                    bool= true;
                }
                if (dText.equals("Enter the day of the run") && number >= 1 && number <= 31) {
                    int thisMonth= getMonth();
                    if (thisMonth == 2) {
                        if (number >= 1 && number <= 28) {
                            bool= true;
                        }
                        if (number == 29 && getYear() % 4 == 0) {
                            bool= true;
                        }

                    }
                    if (thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 ||
                        thisMonth == 8 || thisMonth == 10 || thisMonth == 12) {
                        bool= true;
                    }
                    if (thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11) {
                        if (number >= 1 && number <= 30) {
                            bool= true;
                        }
                    }

                    ArrayList<String> runStrings= Json.getAllRuns();
                    ArrayList<Run> runs= Json.objectRuns(runStrings);
                    if (runs != null) {
                        for (Run run : runs) {
                            if (run.month == getMonth() && run.day == number &&
                                run.year == getYear()) {
                                bool= false;

                            }
                        }
                    }

                }
                if (dText.equals("Enter the time spent running in seconds") && number > 0) {
                    bool= true;
                }

            } catch (NumberFormatException ea) {
                bool= false;
            }
            try {
                numberAlt= Float.parseFloat(text);
                if (dText.equals("Enter the pace of the run in the " +
                    "form 00:00 or the total mileage as a float") && numberAlt > 0) {
                    miles= true;
                }
            } catch (NumberFormatException eaa) {

            }

            if (dText.equals("Enter the year of the run") && bool) {
                descriptor.setText("Enter the month of the run");
                setYear(number);
            } else if (dText.equals("Enter the month of the run") && bool) {
                descriptor.setText("Enter the day of the run");
                setMonth(number);
            } else if (dText.equals("Enter the day of the run") && bool) {
                descriptor.setText("Enter the time spent running in seconds");
                setDay(number);
            } else if (dText.equals("Enter the time spent running in seconds") &&
                bool) {
                    descriptor.setText(
                        "Enter the pace of the run in the form 00:00 or the total mileage as a float");
                    setTime(number);
                } else
                if (dText.equals(
                    "Enter the pace of the run in the form 00:00 or the total mileage as a float") &&
                    !miles) {
                        boolean goodToStore= false;
                        try {
                            goodToStore= true;
                            int num1= Integer.parseInt(text.substring(0, 2));
                            if (num1 < 0 || num1 >= 60) {
                                goodToStore= false;
                            }
                            if (!text.substring(2, 3).equals(":")) {
                                goodToStore= false;
                            }
                            int num2= Integer.parseInt(text.substring(3));
                            if (num2 < 0 || num2 >= 60) {
                                goodToStore= false;
                            }
                            if (text.length() != 5) {
                                goodToStore= false;
                            }

                        } catch (NumberFormatException error) {
                            goodToStore= false;
                        } catch (StringIndexOutOfBoundsException error1) {
                            goodToStore= false;
                        }
                        if (goodToStore) {
                            setPace(text);
                            Run run= new Run(getYear(), getMonth(), getDay(), getTime(), getPace());
                            Json.addToJson(run);

                            descriptor.setText("Enter the year of the run");

                            if (panel4 != null) {
                                panel4.setVisible(false);
                                remove(panel4);
                            }
                            panel4= new JPanel();
                            JTextField cal= createCalorimeter();
                            panel4.add(cal);
                            add(panel4);

                            if (String.valueOf(getYear()).equals(yearList.getSelectedItem())) {

                                if (getMonth() == intFromMonth(
                                    (String) monthList.getSelectedItem())) {

                                    createGraph(getMonth(), getYear());
                                }
                            }
                        }

                    }
            if (miles) {
                setMileage(numberAlt);
                Run runAlt= new Run(getYear(), getMonth(), getDay(), getTime(), getMileage());
                Json.addToJson(runAlt);
                descriptor.setText("Enter the year of the run");

                if (panel4 != null) {
                    panel4.setVisible(false);
                    remove(panel4);
                }
                panel4= new JPanel();
                JTextField cal= createCalorimeter();
                panel4.add(cal);
                add(panel4);

                if (String.valueOf(getYear()).equals(yearList.getSelectedItem())) {

                    if (getMonth() == intFromMonth(
                        (String) monthList.getSelectedItem())) {

                        createGraph(getMonth(), getYear());
                    }

                }
            }
        }
        if (e.getSource() == b1) {
            Json.removeLastRun();

        }

    }

    // creates the graph to display Runs
    public void createGraph(int month, int year) {

        ArrayList<String> allJsons= Json.getAllRuns();
        ArrayList<Run> allRuns= Json.objectRuns(allJsons);
        JFreeChart pieChart= ChartFactory.createBarChart(
            stringMonth(month) + " " + stringYear(year), "Day", "Mileage",
            createDataset(month, year, allRuns), PlotOrientation.VERTICAL, true, true, false);
        if (chart != null) {

            chart.setVisible(false);
            remove(chart);
        }
        chart= new ChartPanel(pieChart);
        chart.setPreferredSize(new java.awt.Dimension(500, 700));
        add(chart, BorderLayout.PAGE_END);

    }

    // creates dataset for the graph
    // if no applicable runs, then the dataset is empty
    private CategoryDataset createDataset(int month, int year, ArrayList<Run> allRuns) {
        DefaultCategoryDataset dataset= new DefaultCategoryDataset();
        String mileName= "Mileage (Miles)";
        String paceName= "Pace (Min/Mile";
        String timeName= "Time (Minutes)";
        // find applicable runs
        ArrayList<Run> applicableRuns= new ArrayList<>();

        if (allRuns != null) {
            for (Run run : allRuns) {

                if (run.month == month && run.year == year) {

                    applicableRuns.add(run);
                }
            }
        }

        // days
        ArrayList<Integer> list= new ArrayList<>();
        for (int i= 1; i <= 28; i++ ) {
            list.add(i);
        }
        if (month == 2) {
            if (year % 4 == 0) {
                list.add(29);
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            list.add(29);
            list.add(30);

        } else {
            list.add(29);
            list.add(30);
            list.add(31);
        }

        // dataset.addValue(value,runName,day)
        if (applicableRuns.size() != 0) {
            for (int i : list) {

                if (applicableRuns.size() != 0) {
                    Run run1= applicableRuns.get(0);

                    if (run1.day == i) {
                        int count= 0;
                        if (getMileStatus()) {
                            dataset.addValue(run1.mileage, mileName, String.valueOf(i));
                            count++ ;
                        }
                        if (getPaceStatus()) {
                            String star= run1.pace;
                            int minutes= Integer.parseInt(star.substring(0, 2));
                            float remain= Float.parseFloat(star.substring(3)) / 60;
                            float total= minutes + remain;
                            float mile= run1.time / total;
                            float next= mile / 60;
                            dataset.addValue(next, paceName, String.valueOf(i));
                            count++ ;
                        }
                        if (getTimeStatus()) {
                            float minutes= (float) run1.time / 60;
                            dataset.addValue(minutes, timeName, String.valueOf(i));
                            count++ ;
                        }
                        applicableRuns.remove(0);
                        if (count == 0) {
                            dataset.addValue(0, mileName, String.valueOf(i));
                        }
                    } else {
                        dataset.addValue(0, mileName, String.valueOf(i));
                    }
                } else {
                    dataset.addValue(0, mileName, String.valueOf(i));
                }

            }
        }
        return dataset;
    }

    // returns the appropriate String month given the passed int
    private String stringMonth(int month) {
        String monthString;
        switch (month) {
        case 1:
            monthString= "January";
            break;
        case 2:
            monthString= "February";
            break;
        case 3:
            monthString= "March";
            break;
        case 4:
            monthString= "April";
            break;
        case 5:
            monthString= "May";
            break;
        case 6:
            monthString= "June";
            break;
        case 7:
            monthString= "July";
            break;
        case 8:
            monthString= "August";
            break;
        case 9:
            monthString= "September";
            break;
        case 10:
            monthString= "October";
            break;
        case 11:
            monthString= "November";
            break;
        case 12:
            monthString= "December";
            break;
        default:
            monthString= "Invalid month";
            break;
        }
        return monthString;
    }

    // returns the proper year of the month given the given String
    public int intFromMonth(String month) {
        if (month.equals("January")) { return 1; }
        if (month.equals("February")) { return 2; }
        if (month.equals("March")) { return 3; }
        if (month.equals("April")) { return 4; }
        if (month.equals("May")) { return 5; }
        if (month.equals("June")) { return 6; }
        if (month.equals("July")) { return 7; }
        if (month.equals("August")) { return 8; }
        if (month.equals("September")) { return 9; }
        if (month.equals("October")) { return 10; }
        if (month.equals("November")) { return 11; }
        if (month.equals("December")) {
            return 12;
        } else {
            return 1;
        }
    }

    // creates the calorie burned JTextField
    public JTextField createCalorimeter() {
        ArrayList<String> allJsons= Json.getAllRuns();
        ArrayList<Run> allRuns= Json.objectRuns(allJsons);
        float totalMileage= 0;
        if (allRuns != null) {
            for (Run run : allRuns) {
                totalMileage+= run.mileage;
            }
        }
        totalMileage= totalMileage * 100;
        JTextField fin= new JTextField(
            String.valueOf((int) totalMileage) + " calories burnt (roughly)");
        return fin;

    }

    // returns the String equivalent of an int
    private String stringYear(int year) {
        return String.valueOf(year);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }

    public String getPace() {
        return pace;
    }

    public float getMileage() {
        return mileage;
    }

    public void setYear(int yearr) {
        year= yearr;
    }

    public void setMonth(int monthh) {
        month= monthh;
    }

    public void setDay(int dayy) {
        day= dayy;
    }

    public void setTime(int timee) {
        time= timee;
    }

    public void setPace(String pacee) {
        pace= pacee;
    }

    public void setMileage(float mileagee) {
        mileage= mileagee;
    }

    public boolean getMileStatus() {
        return mileGood;
    }

    public boolean getPaceStatus() {
        return paceGood;
    }

    public boolean getTimeStatus() {
        return timeGood;
    }

    public void switchMile() {
        mileGood= !mileGood;
    }

    public void switchPace() {
        paceGood= !paceGood;
    }

    public void switchTime() {
        timeGood= !timeGood;
    }

}
