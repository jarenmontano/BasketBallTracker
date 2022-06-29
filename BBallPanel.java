/*
 * Author: Jaren Montano
 * Date: 4/4/2022
 * Notes: the panel that has 3 panels in it.
 *updat/ now has 4 panels in it and the load data will not use
 * the explorer on on mmac to get the file and mneumonic don't work
 * because there is no alt key.
 *
 * Recent change:4/17/2022
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

public class BBallPanel extends JPanel {
    private JMenu dataMnu, fileMnu;
    private JMenuItem  saveJmi, exitJmi, newJmi, loadJmi, firstJmi, lastJmi;
    private JButton loadbtn, allDatabtn, filterbtn, clearDatabtn, totalAssistsbtn, totalReboundsbtn, totalTurnoversbtn;
    private JRadioButton eastRB, westRB;
    private ButtonGroup buttonGroup;
    private JCheckBox starterCheck;
    private JLabel positionlb, firstStartWithlb, lastStartWithlb, assistGreaterlb, filterDatalb;
    private JComboBox<String> positionComboBox;
    private String[] postion = {"Point Guard", "Shooting Guard", "Small Forward", "Power Forward", "Center"};
    private JSlider slider;
    private JTextField firstStartWithtf, lastStartWithtf, assistGreatertf;
    private JTextArea resultsta;
    private FileChooser fc;

    ArrayList<Player> players = new ArrayList<>();
    JMenuBar mb;
    int indexPosition = 0;

    JPanel top = new JPanel();
    JPanel middle = new JPanel();
    JPanel bottom = new JPanel();
    JPanel west = new JPanel();
    JPanel east = new JPanel();

    JButton newbtn = new JButton("New");
    JButton savebtn = new JButton("Save");
    JButton exitbtn = new JButton("Exit");
    JLabel indexlb = new JLabel("0 of 0");
    //center
    JLabel fNamelb = new JLabel("First Name");
    JLabel LNamelb = new JLabel("Last Name");
    JLabel shootlb = new JLabel("Shooting %");
    JLabel reblb = new JLabel("Rebounds");
    JLabel asslb = new JLabel("Assists");
    JLabel turnlb = new JLabel("Turnovers");
    //text fields
    JTextField fNametf = new JTextField("", 10);
    JTextField Lnametf = new JTextField("", 10);
    JTextField shoottf = new JTextField("", 10);
    JTextField rebtf = new JTextField("", 10);
    JTextField asstf = new JTextField("", 10);
    JTextField turntf = new JTextField("", 10);

    //South
    JButton farLeftbtn = new JButton("<<<");
    JButton leftbtn = new JButton("<");
    JButton rightbtn = new JButton(">");
    JButton farRightbtn = new JButton(">>>");




    public BBallPanel(JMenuBar mb){
        this.mb = mb;
        setLayout( new BorderLayout());
        // the north panel
        add(top, BorderLayout.NORTH);
        add(middle, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        add(west, BorderLayout.WEST);
        add(east, BorderLayout.EAST);

        Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        top.setBorder(lowerEtched);
        bottom.setBorder(lowerEtched);
        middle.setBorder(raisedBevel);

        makeMenu(mb);
        makeWestPanel();
        makeEastPanel();

        top.add(newbtn);
        top.add(savebtn);
        top.add(exitbtn);
        top.add(indexlb);

        //Center Panel
        //fname
        middle.add(fNamelb);
        middle.add(fNametf);
        fNamelb.setPreferredSize(new Dimension(120,25));
        fNamelb.setHorizontalAlignment(SwingConstants.RIGHT);
        //lname
        middle.add(LNamelb);
        middle.add(Lnametf);
        LNamelb.setPreferredSize(new Dimension(120,25));
        LNamelb.setHorizontalAlignment(SwingConstants.RIGHT);
        // Shooting
        middle.add(shootlb);
        middle.add(shoottf);
        shootlb.setPreferredSize(new Dimension(120,25));
        shootlb.setHorizontalAlignment(SwingConstants.RIGHT);
        // rebounds
        middle.add(reblb);
        middle.add(rebtf);
        reblb.setPreferredSize(new Dimension(120,25));
        reblb.setHorizontalAlignment(SwingConstants.RIGHT);
        // assists
        middle.add(asslb);
        middle.add(asstf);
        asslb.setPreferredSize(new Dimension(120,25));
        asslb.setHorizontalAlignment(SwingConstants.RIGHT);
        //turnovers
        middle.add(turnlb);
        middle.add(turntf);
        turnlb.setPreferredSize(new Dimension(120,25));
        turnlb.setHorizontalAlignment(SwingConstants.RIGHT);

        //South Panel
        bottom.add(farLeftbtn);
        bottom.add(leftbtn);
        bottom.add(rightbtn);
        bottom.add(farRightbtn);

        fc = new FileChooser();

        // will write the buttons this way
        exitbtn.addActionListener(event -> System.exit(0));
        savebtn.addActionListener(event -> saveRecord());
        newbtn.addActionListener(event -> newRecord());
        farLeftbtn.addActionListener(event -> firstRecord());
        farRightbtn.addActionListener(event -> lastRecord());
        rightbtn.addActionListener(event -> nextRecord());
        leftbtn.addActionListener(event -> previousRecord());
        loadbtn.addActionListener(event -> new FileChooser());
        //loadbtn.addActionListener(event -> loadData(new File("src/res/players.txt")));


        //menu buttons
        saveJmi.addActionListener(event -> saveRecord());
        exitJmi.addActionListener(event -> System.exit(0));
        newJmi.addActionListener(event -> newRecord());
        firstJmi.addActionListener(event -> firstRecord());
        lastJmi.addActionListener(event -> lastRecord());
        loadJmi.addActionListener(event ->  new FileChooser());

        //east panel button listeners
        allDatabtn.addActionListener(event -> alldata());
        clearDatabtn.addActionListener(event -> clearData());
        filterbtn.addActionListener(event -> filterData());
        totalAssistsbtn.addActionListener(event -> totalAssists());
        totalReboundsbtn.addActionListener(event -> totalRebounds());
        totalTurnoversbtn.addActionListener(event -> totalTurnovers());

        //accelorator//only works unless clicked in the menu
        saveJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        exitJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        newJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        loadJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
        firstJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl B"));
        lastJmi.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));

        savebtn.setMnemonic('S');
        exitbtn.setMnemonic('X');
        newbtn.setMnemonic('N');
        loadbtn.setMnemonic('L');
        farLeftbtn.setMnemonic('B');
        farRightbtn.setMnemonic('E');

        makeSlider();

    }

    private void totalTurnovers() {
        long sum = players.stream().mapToLong(x -> x.getTurnovers())
                .sum();
        resultsta.setText("Total Turnovers: " + sum);
    }

    private void totalRebounds() {
        long sum = players.stream().mapToLong(x -> x.getRebounds())
                .sum();
        resultsta.setText("Total Rebounds: " + sum);
    }

    private void totalAssists() {
        long sum = players.stream().mapToLong(x -> x.getAssists())
                .sum();
        resultsta.setText("Total Assists: " + sum);
    }

    private void filterData() {
        resultsta.setText("");
        String firstStartWith = firstStartWithtf.getText();
        String lastStartWith = lastStartWithtf.getText();
        String assists = assistGreatertf.getText();
        if (assists.equals("")) assists = "0";

        resultsta.setText("");
        Stream<Player> playerInfo = players.stream();
        String finalAssists = assists;
        playerInfo.filter(f -> f.getfName().startsWith(firstStartWith))
                .filter(l -> l.getlName().startsWith(lastStartWith))
                .filter(a -> a.getAssists() > Integer.parseInt(finalAssists))
                .forEach(p -> resultsta.append(p + "\n"));

    }

    private void clearData() {

        resultsta.setText("");
        lastStartWithlb.setText("");
        firstStartWithlb.setText("");
        assistGreaterlb.setText("");
    }

    private void alldata() {
        resultsta.setText("");
        Stream<Player> playerInfo = players.stream();
        playerInfo.forEach(p -> resultsta.append(p + "\n"));
    }

    private void makeEastPanel() {
        east.setLayout(new BoxLayout(east , BoxLayout.PAGE_AXIS));
        allDatabtn = new JButton("All Data");
        filterbtn = new JButton("Filter");
        clearDatabtn = new JButton("Clear Data");
        totalAssistsbtn = new JButton("Total Assists");
        totalReboundsbtn = new JButton("Total Rebounds");
        totalTurnoversbtn = new JButton("Total Turnovers");
        Box row = Box.createHorizontalBox();
        row.add(allDatabtn);
        row.add(filterbtn);
        row.add(clearDatabtn);
        row.add(Box.createRigidArea(new Dimension(40, 1)));
        row.add(totalAssistsbtn);
        row.add(totalReboundsbtn);
        row.add(totalTurnoversbtn);
        east.add(row);
        east.add(Box.createRigidArea(new Dimension(10,30)));

        filterDatalb = new JLabel("Filter Data");
        east.add(filterDatalb);

        firstStartWithlb = new JLabel("First Start With");
        firstStartWithtf = new JTextField("", 8);
        lastStartWithlb = new JLabel("Last Start With");
        lastStartWithtf = new JTextField("", 8);
        assistGreaterlb = new JLabel("Assist >");
        assistGreatertf = new JTextField("", 8);
        Box row2 = Box.createHorizontalBox();
        row2.add(firstStartWithlb);
        row2.add(firstStartWithtf);
        row2.add(Box.createRigidArea(new Dimension(30,1)));
        row2.add(lastStartWithlb);
        row2.add(lastStartWithtf);
        row2.add(Box.createRigidArea(new Dimension(30,1)));
        row2.add(assistGreaterlb);
        row2.add(assistGreatertf);
        east.add(row2);
        row2.setMaximumSize(new Dimension(650, 10));
        east.add(Box.createRigidArea(new Dimension(1,10)));

        resultsta = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultsta);
        resultsta.setBackground(Color.gray);
        east.add(scrollPane);
        east.add(Box.createRigidArea(new Dimension(1,10)));


    }

    private void makeSlider() {
        slider = new JSlider(0,50,0);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        middle.add(slider);

        MyChangeListener slidelistener = new MyChangeListener();
        slider.addChangeListener(slidelistener);

    }

    private void makeWestPanel() {
        west.setLayout(new BoxLayout(west , BoxLayout.PAGE_AXIS));
        loadbtn = new JButton("Load");
        west.add(loadbtn);
        west.add(Box.createRigidArea(new Dimension(1, 30)));
        eastRB = new JRadioButton("East", false);
        westRB = new JRadioButton("West", false);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(eastRB);
        buttonGroup.add(westRB);
        Box row = Box.createHorizontalBox();
        row.add(eastRB);
        row.add(westRB);
        west.add(row);
        west.add(Box.createRigidArea(new Dimension(0, 10)));
        loadbtn.setAlignmentX(CENTER_ALIGNMENT);
        starterCheck = new JCheckBox("Starter");
        starterCheck.setAlignmentX(CENTER_ALIGNMENT);
        west.add(Box.createRigidArea(new Dimension(1, 30)));

        positionlb = new JLabel("Position");
        positionlb.setAlignmentX(CENTER_ALIGNMENT);
        positionComboBox = new JComboBox<String>(postion);

        west.add(starterCheck);
        west.add(Box.createRigidArea(new Dimension(1, 30)));

        west.add(positionlb);
        west.add(positionComboBox);
        west.add(Box.createRigidArea(new Dimension(1, 30)));

        Border etched = BorderFactory.createEtchedBorder();
        Border titled = BorderFactory.createTitledBorder(etched, "Conference");
        row.setBorder(titled);
    }

    public void paintComponent(Graphics g){
        super.paintComponent( g);
    }

    private void newRecord(){
        fNametf.setText("");
        Lnametf.setText("");
        shoottf.setText("");
        rebtf.setText("");
        asstf.setText("");
        turntf.setText("");
        indexPosition = 0;
        starterCheck.setSelected(false);
        eastRB.setSelected(false);
        westRB.setSelected(false);
        positionComboBox.setSelectedIndex(0);
        indexlb.setText(indexPosition + " of " + players.size());

    }
    private void saveRecord(){
        String f, l, con;
        double shoot;
        int reb, assist, turnovers;

        try{
            f = fNametf.getText();
            l = Lnametf.getText();
            if (f.equals("") || l.equals("")){
                JOptionPane.showMessageDialog(null, "Must have First and Last names.");
                return;
            }
            if (shoottf.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Shooting percentage is required.");
                return;
            }else{
                shoot = Double.parseDouble(shoottf.getText());
            }
            if (rebtf.getText().equals("")){
                reb = 0;
            }else{
                reb = Integer.parseInt(rebtf.getText());
            }
            if (asstf.getText().equals("")){
                assist = 0;
            }else{
                assist = Integer.parseInt(asstf.getText());
            }
            if (turntf.getText().equals("")){
                turnovers = 0;
            }else {
                turnovers = Integer.parseInt(turntf.getText());
            }
            if (!eastRB.isSelected() && !westRB.isSelected()){
                JOptionPane.showMessageDialog(null, "Conference is required.");
                return;
            }
            con = "West";
            if (eastRB.isSelected()) con = "East";

            Player temp = new Player(f,l,shoot,reb,assist,turnovers, con , positionComboBox.getSelectedIndex() + 1 , starterCheck.isSelected());
            if(indexPosition == 0){
                players.add(temp);
                indexPosition = players.size();
                indexlb.setText(indexPosition + " of " + players.size());

            }else{
                players.set(indexPosition -1, temp);
            }


        }catch (NumberFormatException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Invalid input");
        }catch (InputMismatchException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }



    }
    private void firstRecord(){
        if(players.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "There are no saved records to show");
            return;
        }
        Player temp = players.get(0);
        fNametf.setText(temp.getfName());
        Lnametf.setText(temp.getlName());
        shoottf.setText(String.valueOf(temp.getShootPct()));
        rebtf.setText(String.valueOf(temp.getRebounds()));
        asstf.setText(String.valueOf(temp.getAssists()));
        turntf.setText(String.valueOf(temp.getTurnovers()));
        positionComboBox.setSelectedIndex(temp.getPosition() -1);
        starterCheck.setSelected(temp.isStarter());
        eastRB.setSelected(true);
        if (temp.getConference().equals("West")) westRB.setSelected(true);

        indexPosition = 1;
        indexlb.setText( indexPosition + " of " + players.size());
    }
    private void lastRecord(){
        if(players.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "There are no saved records to show");
            return;
        }
        Player temp = players.get(players.size() -1);
        fNametf.setText(temp.getfName());
        Lnametf.setText(temp.getlName());
        shoottf.setText(String.valueOf(temp.getShootPct()));
        rebtf.setText(String.valueOf(temp.getRebounds()));
        asstf.setText(String.valueOf(temp.getAssists()));
        turntf.setText(String.valueOf(temp.getTurnovers()));
        positionComboBox.setSelectedIndex(temp.getPosition() -1);
        starterCheck.setSelected(temp.isStarter());
        eastRB.setSelected(true);
        if (temp.getConference().equals("West")) westRB.setSelected(true);

        indexPosition = players.size();
        indexlb.setText( indexPosition + " of " + players.size());

    }
    private void nextRecord(){
        if (indexPosition < players.size()){

            Player temp = players.get(indexPosition);
            fNametf.setText(temp.getfName());
            Lnametf.setText(temp.getlName());
            shoottf.setText(String.valueOf(temp.getShootPct()));
            rebtf.setText(String.valueOf(temp.getRebounds()));
            asstf.setText(String.valueOf(temp.getAssists()));
            turntf.setText(String.valueOf(temp.getTurnovers()));
            positionComboBox.setSelectedIndex(temp.getPosition()- 1);
            starterCheck.setSelected(temp.isStarter());
            eastRB.setSelected(true);
            if (temp.getConference().equals("West")) westRB.setSelected(true);

            indexPosition++;
            indexlb.setText( indexPosition + " of " + players.size());
            return;
        }


        JOptionPane.showMessageDialog(null, "There are no more next records to show. ");


    }
    private void previousRecord(){
        int tem = indexPosition - 2;
        if ( tem <= players.size() && tem >= 0){
            indexPosition--;
            Player temp = players.get(indexPosition - 1);
            fNametf.setText(temp.getfName());
            Lnametf.setText(temp.getlName());
            shoottf.setText(String.valueOf(temp.getShootPct()));
            rebtf.setText(String.valueOf(temp.getRebounds()));
            asstf.setText(String.valueOf(temp.getAssists()));
            turntf.setText(String.valueOf(temp.getTurnovers()));
            positionComboBox.setSelectedIndex(temp.getPosition() -1);
            starterCheck.setSelected(temp.isStarter());
            eastRB.setSelected(true);
            if (temp.getConference().equals("West")) westRB.setSelected(true);


            indexlb.setText( indexPosition + " of " + players.size());
            return;

        }
        JOptionPane.showMessageDialog(null, "There are no more previous records to show");

    }



    private void makeMenu(JMenuBar mb){
        fileMnu = new JMenu("File");
        dataMnu = new JMenu("Data");
        mb.add(fileMnu);
        mb.add(dataMnu);

        //jmenu items
        saveJmi = new JMenuItem("Save");
        exitJmi = new JMenuItem("Exit");
        newJmi = new JMenuItem("New");
        loadJmi = new JMenuItem("Load Data");
        firstJmi = new JMenuItem("First Record");
        lastJmi = new JMenuItem("Last Record");

        //add jmenu items
        fileMnu.add(newJmi);
        fileMnu.add(saveJmi);
        fileMnu.add(exitJmi);
        dataMnu.add(loadJmi);
        dataMnu.add(firstJmi);
        dataMnu.add(lastJmi);



    }
    private void loadData(File fi){
        String f, l, con;
        double shoot;
        int reb, assist, turnovers, pos;
        boolean start;


        try(Scanner input = new Scanner(fi)){
            while(input.hasNext()){
                f = input.next();
                l= input.next();
                shoot = input.nextDouble();
                reb = input.nextInt();
                assist = input.nextInt();
                turnovers = input.nextInt();
                con = input.next();
                pos = input.nextInt();
                pos--;
                start = input.next().equals("true");
                players.add(new Player(f,l,shoot,reb,assist,turnovers, con, pos, start));
                indexPosition++;
                indexlb.setText(indexPosition + " of " + players.size());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class FileChooser implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("howdy");
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("."));
            int result = fc.showOpenDialog(BBallPanel.this);
            File f = null;
            if(result == JFileChooser.APPROVE_OPTION){
                f = fc.getSelectedFile();
                loadData(f);
            }else{
                System.out.println("We messed up FILE aww shucks");
            }


        }
    }
    class MyChangeListener implements ChangeListener {
        MyChangeListener() {
        }

        public synchronized void stateChanged(ChangeEvent e) {
            int frequency = slider.getValue();
            if(frequency == 0){
                return;
            }else if(frequency > players.size()){
                JOptionPane.showMessageDialog(null, "you are at the max record" + players.size());

            }else if (frequency > indexPosition){
                nextRecord();
            } else if (frequency < indexPosition){
                previousRecord();
            }
        }
    }

}
