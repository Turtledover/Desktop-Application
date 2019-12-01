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
//<<<<<<< HEAD
//        // Click Add Job button
//        btnAddJob.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                //
//                try{
//                    Connect.HttpGetAndParam req =
//                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/job/submit/");
//                    String entry_file = entryFilePathTextField.getText();
//                    String archives = archivePathTextField.getText();
//                    String name =  nameTextField.getText();
//                    String app_params = appParamTextField.getText();
//                    String libs = libsTextField.getText();
//                    int processors = Runtime.getRuntime().availableProcessors();
//                    String core_num = String.valueOf(processors);
//                    System.out.println("CPU cores: " + processors);
//                    if (entry_file.equals("")) {
//                        JOptionPane.showMessageDialog(null,"Entry file can not be empty");
//                        return;
//                    }
//                    entryFilePathTextField.setText("");
//                    archivePathTextField.setText("");
//                    nameTextField.setText("");
//                    appParamTextField.setText("");
//                    libsTextField.setText("");
////                    if (archives.equals("")) {
////                        archives = "hdfs:///user/root/mnist/input/data/mnist.zip#mnist";
////                    }
////                    if (app_params.equals("")) {
////                        app_params = "--output mnist/output --format csv";
////                    }
//                    if (name.equals("")) {
//                        name = "Default Name";
//                    }
//                    // Connect.master_base_url + /services/job/submit/?&
//                    // entry_file: "hdfs:///user/root/mnist/input/code/mnist_data_setup.py"
//                    //app_params: --output mnist/output --format csv
//                    //archives: hdfs:///user/root/mnist/input/data/mnist.zip#mnist
//                    //name: MNIST Data Convert
//                    req.addParameter("entry_file", URLEncoder.encode(entry_file, "UTF-8"));
//                    req.addParameter("archives", URLEncoder.encode(archives, "UTF-8"));
//                    req.addParameter("app_params", URLEncoder.encode(app_params, "UTF-8"));
//                    req.addParameter("name", URLEncoder.encode(name, "UTF-8"));
//                    req.addParameter("libs", URLEncoder.encode(libs, "UTF-8"));
//
//
//                    // get message
//                    String premium_rate = "100%";
//                    //Credit by Haozhe
//                    try {
//	                    Connect.HttpGetAndParam price_req =
//	                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/credit/get_price/");
//	                    String price_res = price_req.execute();
//	                    JsonParser parser = new JsonParser();
//	                    JsonElement jsonTree = parser.parse(price_res);
//	                    if(jsonTree.isJsonObject()) {
//	                        JsonObject jsonObject = jsonTree.getAsJsonObject();
//		                    System.out.println(jsonObject.get("premium_rate").toString());
//	                        premium_rate = jsonObject.get("premium_rate").toString();
//	                    }
//	                } catch (IOException ex) {
//	                    ex.printStackTrace();
//	                }
//
//                    //Credit by Haozhe
//                    int reply = JOptionPane.showConfirmDialog(null, "The current Premium Ratio is " + premium_rate, "Confirm", JOptionPane.OK_CANCEL_OPTION);
//                    if (reply == JOptionPane.OK_OPTION) {
//                        String res = req.execute();
//                        System.out.println(res);
//                        JsonParser parser = new JsonParser();
//                        JsonElement jsonTree = parser.parse(res);
//                        JsonObject root = jsonTree.getAsJsonObject();
//                        JsonElement status = root.get("status");
//                        if (status.getAsString().equals("true")) {
//                            JOptionPane.showMessageDialog(frame,"Submit job successfully");
//                        } else {
//                            JOptionPane.showMessageDialog(frame,"Submit job failed");
//                        }
//                    }
//
//
//
//
//
//
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//
//
//            }
//        });
//
//        /* Test running python script */
//        btnAddNewMachine.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                System.out.println("add machine button click");
//                String authorized_key_path = authorizedKeyPathTextField.getText();
//                String cpu_cores = coreNumtextField.getText();
//                String memory_size = memoryTextField.getText();
//                String public_key = sshPublicKeyTextArea.getText();
//                // [TBD] Add the error handling of the input here.
//                boolean success = MachineLib.initWorker(authorized_key_path, cpu_cores, memory_size, public_key);
//
//                if(success) {
//                    JOptionPane.showMessageDialog(null,"Success!");
//                    refresh_machine_list();
//                    switchPanels(machinePanel);
//                } else {
//                    JOptionPane.showMessageDialog(null,"Fail!");
//                }
//
////                System.out.println("result=" + msg);
//            }
//
//        });
//        btnGetContributionHistory.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                Connect.HttpGetAndParam req = new Connect.HttpGetAndParam(Connect.master_base_url + "/services/machine/history/");
//                String res = "";
//                try {
//                    res = req.execute();
//                    final JTable table = new JTable(new MachineTableModel());
//                    table.setEnabled(false);
//                    table.setDefaultEditor(Object.class, null);
//                    table.setDefaultRenderer(JButton.class, new ButtonRenderer());
//                    table.addMouseListener(new MouseAdapter() {
//                        @Override
//                        public void mouseClicked(MouseEvent e) {
//                            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
//                            int row = e.getY()/table.getRowHeight();
//                            System.out.println("machine table mouse click row=" + row + " column=" + column);
//                            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
//                                Object value = table.getValueAt(row, column);
//                                if (value instanceof JButton) {
//                                    ((JButton)value).doClick();
//                                }
//                            }
//                        }
//                    });
//                    MachineTableModel machineModel = (MachineTableModel) table.getModel();
//                    machineModel = parseRes(machineModel, res);
//                    table.setModel(machineModel);
//                    JOptionPane.showMessageDialog(frame, new JScrollPane(table));
////                    switchPanels(machinePanel);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//        });
//
//    }
//
//    private void remove_machine(String id) {
//        try {
//            // Stop the HDFS datanode and the YARN nodemanager
//            Process yarn = Runtime.getRuntime().exec("/usr/local/hadoop/bin/yarn --daemon stop nodemanager");
//            int yarn_exitcode = yarn.waitFor();
//            System.out.println("YARN stop nodemanager exit with " + yarn_exitcode);
//            Process hdfs = Runtime.getRuntime().exec("/usr/local/hadoop/bin/hdfs --daemon stop datanode");
//            int hdfs_exitcode = hdfs.waitFor();
//            System.out.println("HDFS stop datanode exit with " + hdfs_exitcode);
//            // Send the remove machine request to the server
//            Connect.HttpPostAndParam req =
//                    new Connect.HttpPostAndParam(Connect.master_base_url + "/services/machine/remove/");
//            req.addParameter("machine_id", id);
//            req.addParameter("csrfmiddlewaretoken",
//                    Connect.getCookieByName("csrftoken"));
//            req.setHeader("X-CSRFToken", Connect.getCookieByName("csrftoken"));
//            String result = req.execute();
//            System.out.print(result);
//            Process rm_hadoop = Runtime.getRuntime().exec("rm -rf /usr/local/hadoop");
//            int rm_hadoop_exitcode = rm_hadoop.waitFor();
//            System.out.println("Remove the /usr/local/hadoop exit with " + rm_hadoop_exitcode);
//            Process rm_spark = Runtime.getRuntime().exec("rm -rf /usr/local/spark");
//            int rm_spark_exitcode = rm_spark.waitFor();
//            System.out.println("Remove the /usr/local/spark exit with " + rm_spark_exitcode);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//=======
//>>>>>>> master
    }
}
