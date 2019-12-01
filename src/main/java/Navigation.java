import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;

public class Navigation extends JFrame{
    private MachinePage machinePage;
    private JobPage jobPage;
    private ProfilePage profilePage;
    private JFrame frame;
    private JLayeredPane layeredPane;
    private JPanel machinePanel;
    private Timer jobTimer;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Navigation window = new Navigation();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void switchPanels(JPanel panel) {
        layeredPane.removeAll();
        layeredPane.add(panel);
        layeredPane.repaint();
        layeredPane.revalidate();
    }

    /**
     * Create the application.
     */
    public Navigation() {
        super();
        setBounds(400, 200, 570, 477);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new CardLayout(0, 0));


        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane, "layer_panel");
        layeredPane.setLayout(new CardLayout(0, 0));
        machinePage = new MachinePage();
        machinePanel = machinePage.machinePanel;
        layeredPane.add(machinePanel, "machine_panel");

        jobPage = new JobPage();
        final JPanel jobPanel = jobPage.jobPanel;
        layeredPane.add(jobPanel, "name_3361101841548929");


        profilePage = new ProfilePage();
        final JPanel profilePanel = profilePage.profilePanel;
        layeredPane.add(profilePanel, "profile_panel");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenuItem mntmMachine = new JMenuItem("Machine");
        mntmMachine.setFocusCycleRoot(true);
        mntmMachine.setFocusTraversalPolicyProvider(true);
        mntmMachine.setFocusable(true);
        mntmMachine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jobTimer != null) {
                    jobTimer.cancel();
                    jobTimer = null;
                }
                machinePage.refresh_machine_list();
                switchPanels(machinePanel);
            }
        });
        mntmMachine.setSelected(true);
        mntmMachine.setFocusPainted(true);
        mntmMachine.setBorder(new LineBorder(new Color(0, 0, 0)));
        mntmMachine.setBackground(Color.WHITE);
        mntmMachine.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mntmMachine);

        JMenuItem mntmJob = new JMenuItem("Job");

        mntmJob.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jobTimer != null) {
                    return;
                }
                //TimerTask task = jobPage.listJobTask;
                TimerTask task = jobPage.new ListJobTask();
                jobTimer = new Timer();
                jobTimer.schedule(task, 0,5000); // line 1
                switchPanels(jobPanel);
            }
        });
        mntmJob.setBorder(new LineBorder(new Color(0, 0, 0)));
        mntmJob.setSelected(true);
        menuBar.add(mntmJob);

        JMenuItem mntmProfile = new JMenuItem("Profile");
        mntmProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jobTimer != null) {
                    jobTimer.cancel();
                    jobTimer = null;
                }

                try{
                    Connect.HttpGetAndParam req =
                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/credit/check_credit/");
                    String res = req.execute();
                    System.out.println(res);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonTree = parser.parse(res);
                    if(jsonTree.isJsonObject()) {

                        JsonObject jsonObject = jsonTree.getAsJsonObject();
                        double new_using_credit = Double.parseDouble(jsonObject.get("using_credit").toString());
                        double new_sharing_credit= Double.parseDouble(jsonObject.get("sharing_credit").toString());

                        profilePage.sharingCreditValue.setText("" + new_sharing_credit);

                        profilePage.usingCreditValue.setText("" + new_using_credit);
                        switchPanels(profilePanel);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mntmProfile.setBorder(new LineBorder(new Color(0, 0, 0)));
        menuBar.add(mntmProfile);
        action();
        machinePage.refresh_machine_list();
        switchPanels(machinePanel);
    }
    public void action(){
        // Click Log Out button
        profilePage.btnLogou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginPage regFace = new LoginPage();
                regFace.setVisible(true);
                dispose();
            }
        });
    }
}
