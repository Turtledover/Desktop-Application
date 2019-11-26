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

    private JFrame frame;
    private JLayeredPane layeredPane;
    private JTable machineTable;
    private JTextField entryFilePathTextField;
    private JTextField libsTextField;
    private JTextField coreNumtextField;
    private JTextField memoryTextField;
    private JTable jobListTable;
    private JTextField archivePathTextField;
    private JTextField authorizedKeyPathTextField;
    private JTextField nameTextField;
    private JTextField appParamTextField;
    private JButton btnLogou;
    private JButton btnAddJob;
    private JButton btnAddNewMachine;
    private JButton btnGetContributionHistory;
    private JButton btnRemoveMachine;
    private JLabel entryFilePathLabel;
    private JLabel libsLabel;
    private JLabel archivePathLabel;
    private JLabel nameLabel;
    private JLabel appParamLabel;
    private JTextArea sshPublicKeyTextArea;
    private JPanel machinePanel;

    private double USING_CREDIT = 0.0;
    private double SHARING_CREDIT = 0.0;

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

        machinePanel = new JPanel();
        layeredPane.add(machinePanel, "machine_panel");
        machinePanel.setLayout(null);

        JLabel machineListLabel = new JLabel("Machine List");
        machineListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        machineListLabel.setBounds(186, 6, 180, 57);
        machinePanel.add(machineListLabel);

        btnAddNewMachine = new JButton("Add new machine");
        btnAddNewMachine.setBounds(86, 381, 180, 29);
        machinePanel.add(btnAddNewMachine);


        btnGetContributionHistory = new JButton("Get contribution history");
        btnGetContributionHistory.setBounds(286, 381, 220, 29);
        machinePanel.add(btnGetContributionHistory);
//        btnRemoveMachine = new JButton("Remove the machine");
//        btnRemoveMachine.setBounds(186, 415, 180, 29);
//        panel.add(btnRemoveMachine);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(93, 53, 392, 154);
        machinePanel.add(scrollPane);

        machineTable = new JTable(new MachineTableModel());

        machineTable.setEnabled(false);
        machineTable.setDefaultEditor(Object.class, null);
        machineTable.setDefaultRenderer(JButton.class, new ButtonRenderer());
        machineTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = machineTable.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY()/machineTable.getRowHeight();
                System.out.println("machine table mouse click row=" + row + " column=" + column);
                if (row < machineTable.getRowCount() && row >= 0 && column < machineTable.getColumnCount() && column >= 0) {
                    Object value = machineTable.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton)value).doClick();
                    }
                }
            }
        });


        scrollPane.setViewportView(machineTable);

        JLabel coreNumLabel = new JLabel("Core num");
        coreNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coreNumLabel.setBounds(93, 248, 158, 29);
        machinePanel.add(coreNumLabel);

        coreNumtextField = new JTextField();
        coreNumtextField.setColumns(10);
        coreNumtextField.setBounds(286, 249, 130, 26);
        machinePanel.add(coreNumtextField);

        JLabel memoryLabel = new JLabel("Memory");
        memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memoryLabel.setBounds(93, 280, 158, 27);
        machinePanel.add(memoryLabel);

        memoryTextField = new JTextField();
        memoryTextField.setColumns(10);
        memoryTextField.setBounds(286, 281, 130, 26);
        machinePanel.add(memoryTextField);

        JLabel authorizedKeyPathLabel = new JLabel("Authorized key path");
        authorizedKeyPathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorizedKeyPathLabel.setBounds(90, 219, 161, 29);
        machinePanel.add(authorizedKeyPathLabel);

        authorizedKeyPathTextField = new JTextField();
        authorizedKeyPathTextField.setColumns(10);
        authorizedKeyPathTextField.setBounds(286, 220, 130, 26);
        machinePanel.add(authorizedKeyPathTextField);

        JLabel sshPublicKeyLabel = new JLabel("SSH Public Key");
        sshPublicKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sshPublicKeyLabel.setBounds(93, 319, 158, 27);
        machinePanel.add(sshPublicKeyLabel);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(286, 324, 130, 44);
        machinePanel.add(scrollPane_2);

        sshPublicKeyTextArea = new JTextArea();
        scrollPane_2.setViewportView(sshPublicKeyTextArea);

        final JPanel jobPanel = new JPanel();
        layeredPane.add(jobPanel, "name_3361101841548929");
        jobPanel.setLayout(null);

        JLabel jobListLabel = new JLabel("Job List");
        jobListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jobListLabel.setBounds(200, 6, 147, 36);
        jobPanel.add(jobListLabel);

        btnAddJob = new JButton("Add Job");
        btnAddJob.setBounds(232, 397, 117, 29);
        jobPanel.add(btnAddJob);

        entryFilePathLabel = new JLabel("Entry File Path*");
        entryFilePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        entryFilePathLabel.setBounds(141, 250, 107, 20);
        jobPanel.add(entryFilePathLabel);

        entryFilePathTextField = new JTextField();
        entryFilePathTextField.setBounds(283, 250, 130, 20);
        jobPanel.add(entryFilePathTextField);
        entryFilePathTextField.setColumns(10);

        libsLabel = new JLabel("Libs");
        libsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        libsLabel.setBounds(141, 280, 107, 20);
        jobPanel.add(libsLabel);

        libsTextField = new JTextField();
        libsTextField.setBounds(283, 280, 130, 20);
        jobPanel.add(libsTextField);
        libsTextField.setColumns(10);


        final JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(48, 50, 454, 193);
        jobPanel.add(scrollPane_1);

        jobListTable = new JTable();


        scrollPane_1.setViewportView(jobListTable);
        DefaultTableModel model_1 = (DefaultTableModel) jobListTable.getModel();
        Object[] row_1 = { "", "", "", "", "" };
        model_1.addColumn("job_id");
        model_1.addColumn("name");
        model_1.addColumn("status");
        model_1.addColumn("used_credits");
        model_1.addColumn("duration");
        model_1.addColumn("added");
        model_1.addRow(row_1);
        jobListTable.setModel(model_1);

        appParamLabel = new JLabel("App Params");
        appParamLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appParamLabel.setBounds(141, 310, 107, 20);
        jobPanel.add(appParamLabel);

        appParamTextField = new JTextField();
        appParamTextField.setColumns(10);
        appParamTextField.setBounds(283, 310, 130, 20);
        jobPanel.add(appParamTextField);

        archivePathLabel = new JLabel("Archive Path");
        archivePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        archivePathLabel.setBounds(141, 340, 107, 20);
        jobPanel.add(archivePathLabel);

        archivePathTextField = new JTextField();
        archivePathTextField.setColumns(10);
        archivePathTextField.setBounds(283, 340, 130, 20);
        jobPanel.add(archivePathTextField);

        nameLabel = new JLabel("Name");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(141, 370, 107, 20);
        jobPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setColumns(10);
        nameTextField.setBounds(283, 370, 130, 20);
        jobPanel.add(nameTextField);


        final JPanel profilePanel = new JPanel();
        layeredPane.add(profilePanel, "profile_panel");
        profilePanel.setLayout(null);

        JLabel sharingCreditLabel = new JLabel("Sharing Credit");
        sharingCreditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sharingCreditLabel.setBounds(124, 108, 127, 39);
        profilePanel.add(sharingCreditLabel);

        final JLabel sharingCreditValue = new JLabel(""+SHARING_CREDIT);
        sharingCreditValue.setHorizontalAlignment(SwingConstants.CENTER);
        sharingCreditValue.setBounds(294, 108, 127, 39);
        profilePanel.add(sharingCreditValue);

        JLabel usingCreditLabel = new JLabel("Using Credit");
        usingCreditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usingCreditLabel.setBounds(124, 176, 127, 39);
        profilePanel.add(usingCreditLabel);

        final JLabel usingCreditValue = new JLabel(""+USING_CREDIT);
        usingCreditValue.setHorizontalAlignment(SwingConstants.CENTER);
        usingCreditValue.setBounds(294, 176, 127, 39);
        profilePanel.add(usingCreditValue);

        btnLogou = new JButton("Log Out");
        btnLogou.setBounds(212, 278, 137, 45);
        profilePanel.add(btnLogou);
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
                refresh_machine_list();
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
        class ListJobTask extends TimerTask {
            @Override
            public void run() {
                try{
                    Connect.HttpGetAndParam req =
                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/job/list/");
                    String res = req.execute();
                    System.out.println(res);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonTree = parser.parse(res);
                    if(jsonTree.isJsonObject()) {

                        JsonObject jsonObject = jsonTree.getAsJsonObject();
                        JsonElement elem = jsonObject.get("result");
                        if(elem.isJsonObject()){
                            JsonObject jo= elem.getAsJsonObject();

                            JsonArray jsonArr = jo.getAsJsonArray("jobs");
                            Gson googleJson = new Gson();
                            ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);

                            scrollPane_1.remove(jobListTable);

                            jobListTable = new JTable();

                            final JPopupMenu popupMenu = new JPopupMenu();
                            JMenuItem getLogItem = new JMenuItem("Get log");
                            getLogItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int row = jobListTable.getSelectedRow();
                                    String jobId = jobListTable.getModel().getValueAt(row, 0).toString();
                                    Connect.HttpGetAndParam req =
                                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/job/get_log/");
                                    req.addParameter("job_id", String.valueOf(Integer.parseInt(jobId)));
                                    String res = "";
                                    try {
                                        res = req.execute();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    System.out.println(res);
                                    // create a JTextArea
                                    JTextArea textArea = new JTextArea(16, 30);
                                    JsonParser parser = new JsonParser();
                                    JsonElement jsonTree = parser.parse(res);

                                    if(jsonTree.isJsonObject()) {

                                        JsonObject jsonObject = jsonTree.getAsJsonObject();
                                        JsonElement elem = jsonObject.get("result");
                                        res = elem.toString();
                                        if(elem.isJsonObject()){
                                            Gson gson = new GsonBuilder().setPrettyPrinting().create();


                                            res = gson.toJson(elem.getAsJsonObject().get("logs")); // done
                                        }
                                    }
                                    textArea.setText(res);
                                    textArea.setEditable(false);

                                    // wrap a scrollpane around it
                                    JScrollPane scrollPane = new JScrollPane(textArea);

                                    // display them in a message dialog
                                    JOptionPane.showMessageDialog(frame, scrollPane);
                                }
                            });
                            popupMenu.addPopupMenuListener(new PopupMenuListener() {

                                @Override
                                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            int rowAtPoint = jobListTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), jobListTable));
                                            if (rowAtPoint > -1) {
                                                jobListTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void popupMenuCanceled(PopupMenuEvent e) {
                                    // TODO Auto-generated method stub

                                }
                            });
                            popupMenu.add(getLogItem);
                            jobListTable.setComponentPopupMenu(popupMenu);

                            scrollPane_1.setViewportView(jobListTable);

                            boolean addCol = false;
                            DefaultTableModel model_1 = (DefaultTableModel) jobListTable.getModel();
                            jobListTable.setDefaultEditor(Object.class, null);
                            for(Object obj:jsonObjList) {
                                List<Object> list  = new ArrayList<>();
                                LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) obj;
                                for (Map.Entry<String, Object> entry: map.entrySet()) {
                                    String k = entry.getKey();
                                    String v = "";
                                    if(entry.getValue() instanceof Integer || k.equals("job_id")){
                                        double d_v = (Double) entry.getValue();
                                        int i_v = (int) d_v;
                                        v = ""+i_v;

                                    }else if (entry.getValue() instanceof Double){
                                        double d_v = (Double) entry.getValue();
                                        v = ""+d_v;
                                    }else{
                                        v = (String) entry.getValue();
                                    }
                                    if(!addCol){
                                        model_1.addColumn(k);
                                    }
                                    list.add(v);
                                }
                                model_1.addRow(list.toArray());
                                list.clear();
                                addCol = true;
                            }
                            jobListTable.setModel(model_1);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        mntmJob.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(jobTimer != null) {
                    return;
                }
                TimerTask task = new ListJobTask();
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

                        sharingCreditValue.setText("" + new_sharing_credit);
//                        profilePanel.remove(sharingCreditValue);
//                        JLabel label = new JLabel(""+new_sharing_credit);
//                        label.setHorizontalAlignment(SwingConstants.CENTER);
//                        label.setBounds(294, 108, 127, 39);
//                        profilePanel.add(label);

                        usingCreditValue.setText("" + new_using_credit);
//                        profilePanel.remove(usingCreditValue);
//                        JLabel label_2 = new JLabel(""+new_using_credit);
//                        label_2.setHorizontalAlignment(SwingConstants.CENTER);
//                        label_2.setBounds(294, 176, 127, 39);
//                        profilePanel.add(label_2);
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
        refresh_machine_list();
        switchPanels(machinePanel);
    }
    public void action(){
        // Click Log Out button
        btnLogou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginPage regFace = new LoginPage();
                regFace.setVisible(true);
                dispose();
            }
        });
        // Click Add Job button
        btnAddJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //
                try{
                    Connect.HttpGetAndParam req =
                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/job/submit/");
                    String entry_file = entryFilePathTextField.getText();
                    String archives = archivePathTextField.getText();
                    String name =  nameTextField.getText();
                    String app_params = appParamTextField.getText();
                    String libs = libsTextField.getText();
                    int processors = Runtime.getRuntime().availableProcessors();
                    String core_num = String.valueOf(processors);
                    System.out.println("CPU cores: " + processors);
                    if (entry_file.equals("")) {
                        JOptionPane.showMessageDialog(null,"Entry file can not be empty");
                        return;
                    }
                    entryFilePathTextField.setText("");
                    archivePathTextField.setText("");
                    nameTextField.setText("");
                    appParamTextField.setText("");
                    libsTextField.setText("");
//                    if (archives.equals("")) {
//                        archives = "hdfs:///user/root/mnist/input/data/mnist.zip#mnist";
//                    }
//                    if (app_params.equals("")) {
//                        app_params = "--output mnist/output --format csv";
//                    }
                    if (name.equals("")) {
                        name = "Default Name";
                    }
                    // Connect.master_base_url + /services/job/submit/?&
                    // entry_file: "hdfs:///user/root/mnist/input/code/mnist_data_setup.py"
                    //app_params: --output mnist/output --format csv
                    //archives: hdfs:///user/root/mnist/input/data/mnist.zip#mnist
                    //name: MNIST Data Convert
                    req.addParameter("entry_file", URLEncoder.encode(entry_file, "UTF-8"));
                    req.addParameter("archives", URLEncoder.encode(archives, "UTF-8"));
                    req.addParameter("app_params", URLEncoder.encode(app_params, "UTF-8"));
                    req.addParameter("name", URLEncoder.encode(name, "UTF-8"));
                    req.addParameter("libs", URLEncoder.encode(libs, "UTF-8"));


                    // get message
                    String premium_rate = "100%";
                    //Credit by Haozhe
                    try {
	                    Connect.HttpGetAndParam price_req =
	                            new Connect.HttpGetAndParam(Connect.master_base_url + "/services/credit/get_price/");
	                    String price_res = price_req.execute();
	                    JsonParser parser = new JsonParser();
	                    JsonElement jsonTree = parser.parse(price_res);
	                    if(jsonTree.isJsonObject()) {
	                        JsonObject jsonObject = jsonTree.getAsJsonObject();
		                    System.out.println(jsonObject.get("premium_rate").toString());
	                        premium_rate = jsonObject.get("premium_rate").toString();
	                    }
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
                    
                    //Credit by Haozhe
                    int reply = JOptionPane.showConfirmDialog(null, "The current Premium Ratio is " + premium_rate, "Confirm", JOptionPane.OK_CANCEL_OPTION);
                    if (reply == JOptionPane.OK_OPTION) {
                        String res = req.execute();
                        System.out.println(res);
                        JsonParser parser = new JsonParser();
                        JsonElement jsonTree = parser.parse(res);
                        JsonObject root = jsonTree.getAsJsonObject();
                        JsonElement status = root.get("status");
                        if (status.getAsString().equals("true")) {
                            JOptionPane.showMessageDialog(frame,"Submit job successfully");
                        } else {
                            JOptionPane.showMessageDialog(frame,"Submit job failed");
                        }
                    }






                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        /* Test running python script */
        btnAddNewMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("add machine button click");
                String authorized_key_path = authorizedKeyPathTextField.getText();
                String cpu_cores = coreNumtextField.getText();
                String memory_size = memoryTextField.getText();
                String public_key = sshPublicKeyTextArea.getText();
                // [TBD] Add the error handling of the input here.
                boolean success = MachineLib.initWorker(authorized_key_path, cpu_cores, memory_size, public_key);

                if(success) {
                    JOptionPane.showMessageDialog(null,"Success!");
                    refresh_machine_list();
                    switchPanels(machinePanel);
                } else {
                    JOptionPane.showMessageDialog(null,"Fail!");
                }

//                System.out.println("result=" + msg);
            }

        });
        btnGetContributionHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Connect.HttpGetAndParam req = new Connect.HttpGetAndParam(Connect.master_base_url + "/services/machine/history/");
                String res = "";
                try {
                    res = req.execute();
                    final JTable table = new JTable(new MachineTableModel());
                    table.setEnabled(false);
                    table.setDefaultEditor(Object.class, null);
                    table.setDefaultRenderer(JButton.class, new ButtonRenderer());
                    table.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                            int row = e.getY()/table.getRowHeight();
                            System.out.println("machine table mouse click row=" + row + " column=" + column);
                            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                                Object value = table.getValueAt(row, column);
                                if (value instanceof JButton) {
                                    ((JButton)value).doClick();
                                }
                            }
                        }
                    });
                    MachineTableModel machineModel = (MachineTableModel) table.getModel();
                    machineModel = parseRes(machineModel, res);
                    table.setModel(machineModel);
                    JOptionPane.showMessageDialog(frame, new JScrollPane(table));
//                    switchPanels(machinePanel);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });

    }

    private void remove_machine(String id) {
        try {
            // Stop the HDFS datanode and the YARN nodemanager
            Process yarn = Runtime.getRuntime().exec("/usr/local/hadoop/bin/yarn --daemon stop nodemanager");
            int yarn_exitcode = yarn.waitFor();
            System.out.println("YARN stop nodemanager exit with " + yarn_exitcode);
            Process hdfs = Runtime.getRuntime().exec("/usr/local/hadoop/bin/hdfs --daemon stop datanode");
            int hdfs_exitcode = hdfs.waitFor();
            System.out.println("HDFS stop datanode exit with " + hdfs_exitcode);
            // Send the remove machine request to the server
            Connect.HttpPostAndParam req =
                    new Connect.HttpPostAndParam(Connect.master_base_url + "/services/machine/remove/");
            req.addParameter("machine_id", id);
            req.addParameter("csrfmiddlewaretoken",
                    Connect.getCookieByName("csrftoken"));
            req.setHeader("X-CSRFToken", Connect.getCookieByName("csrftoken"));
            String result = req.execute();
            System.out.print(result);
            Process rm_hadoop = Runtime.getRuntime().exec("rm -rf /usr/local/hadoop");
            int rm_hadoop_exitcode = rm_hadoop.waitFor();
            System.out.println("Remove the /usr/local/hadoop exit with " + rm_hadoop_exitcode);
            Process rm_spark = Runtime.getRuntime().exec("rm -rf /usr/local/spark");
            int rm_spark_exitcode = rm_spark.waitFor();
            System.out.println("Remove the /usr/local/spark exit with " + rm_spark_exitcode);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private MachineTableModel parseRes(MachineTableModel machineModel, String res) {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(res);

        if(jsonTree == null || !jsonTree.isJsonObject()) {
            return machineModel;
        }

        JsonObject root = jsonTree.getAsJsonObject();
        JsonElement result = root.get("result");
        if(result == null || !result.isJsonObject()) {
            return machineModel;
        }
        JsonObject resObj = result.getAsJsonObject();
        JsonElement mlistEle = resObj.get("machines");
        if(mlistEle == null || !mlistEle.isJsonArray()) {
            return machineModel;
        }

        JsonArray mlist = mlistEle.getAsJsonArray();
        Iterator<JsonElement> mItt = mlist.iterator();
        while(mItt.hasNext()) {
            JsonElement mEle = mItt.next();
            if(!mEle.isJsonObject()) {
                continue;
            }
            ArrayList<Object> row = new ArrayList<>();
            JsonObject mObj = mEle.getAsJsonObject();

            try{
                final String id = mObj.get("id").getAsString();
                row.add(id);
                row.add(mObj.get("hostname").getAsString());
                row.add(mObj.get("type").getAsString());
                row.add(String.valueOf(mObj.get("core_num").getAsString()));
                row.add(String.valueOf(mObj.get("memory").getAsString()));
                row.add(mObj.get("starttime").getAsString());

                JButton rmBtn = new JButton("Remove");
                rmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("clicked ID = " + id);
                        remove_machine(id);
                    }
                });

                row.add(rmBtn);
                machineModel.addRow(row);
            } catch (NullPointerException ex) {
                System.err.println("Missing attributes");
                ex.printStackTrace();
            }

        }
        return machineModel;
    }
    private void refresh_machine_list() {
        Connect.HttpGetAndParam req =
                new Connect.HttpGetAndParam(Connect.master_base_url + "/services/machine/list/");

        MachineTableModel machineModel = (MachineTableModel) machineTable.getModel();
        machineModel.clearData();

        String res = null;
        try {
            res = req.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        System.out.println(res);

        machineModel = parseRes(machineModel, res);
        machineTable.setModel(machineModel);

        return;
    }
    private void get_history_machine_list() {
        Connect.HttpGetAndParam req =
                new Connect.HttpGetAndParam(Connect.master_base_url + "/services/machine/history/");

        MachineTableModel machineModel = (MachineTableModel) machineTable.getModel();
        machineModel.clearData();

        String res = null;
        try {
            res = req.execute();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        System.out.println(res);

        machineModel = parseRes(machineModel, res);
        machineTable.setModel(machineModel);

        return;
    }

}
