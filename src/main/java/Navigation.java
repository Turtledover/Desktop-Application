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

public class Navigation extends JFrame{

    private JFrame frame;
    private JLayeredPane layeredPane;
    private JTable table;
    private JTextField entryFilePathTextField;
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
    private JLabel entryFilePathLabel;
    private JLabel archivePathLabel;
    private JLabel nameLabel;
    private JLabel appParamLabel;

    private double USING_CREDIT = 0.0;
    private double SHARING_CREDIT = 0.0;

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
        getContentPane().add(layeredPane, "name_3361081430437205");
        layeredPane.setLayout(new CardLayout(0, 0));

        final JPanel panel = new JPanel();
        layeredPane.add(panel, "name_3361087879639943");
        panel.setLayout(null);

        JLabel machineListLabel = new JLabel("Machine List");
        machineListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        machineListLabel.setBounds(186, 6, 180, 57);
        panel.add(machineListLabel);

        btnAddNewMachine = new JButton("Add new machine");
        btnAddNewMachine.setBounds(186, 381, 180, 29);
        panel.add(btnAddNewMachine);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(93, 53, 392, 154);
        panel.add(scrollPane);

        table = new JTable();
        Object[] row = { "1", "8", "8GB", "Available" };

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("ID");
//		model.addColumn("Machine Type");
        model.addColumn("CPU Core");
        model.addColumn("Memory");
        model.addColumn("Status");
        model.addRow(row);
        table.setModel(model);
        table.setDefaultEditor(Object.class, null);

        scrollPane.setViewportView(table);

        JLabel coreNumLabel = new JLabel("Core num");
        coreNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coreNumLabel.setBounds(93, 248, 158, 29);
        panel.add(coreNumLabel);

        coreNumtextField = new JTextField();
        coreNumtextField.setColumns(10);
        coreNumtextField.setBounds(286, 249, 130, 26);
        panel.add(coreNumtextField);

        JLabel memoryLabel = new JLabel("Memory");
        memoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        memoryLabel.setBounds(93, 280, 158, 27);
        panel.add(memoryLabel);

        memoryTextField = new JTextField();
        memoryTextField.setColumns(10);
        memoryTextField.setBounds(286, 281, 130, 26);
        panel.add(memoryTextField);

        JLabel authorizedKeyPathLabel = new JLabel("Authorized key path");
        authorizedKeyPathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorizedKeyPathLabel.setBounds(90, 219, 161, 29);
        panel.add(authorizedKeyPathLabel);

        authorizedKeyPathTextField = new JTextField();
        authorizedKeyPathTextField.setColumns(10);
        authorizedKeyPathTextField.setBounds(286, 220, 130, 26);
        panel.add(authorizedKeyPathTextField);

        JLabel sshPublicKeyLabel = new JLabel("SSH Public Key");
        sshPublicKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sshPublicKeyLabel.setBounds(93, 319, 158, 27);
        panel.add(sshPublicKeyLabel);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(286, 324, 130, 44);
        panel.add(scrollPane_2);

        JTextArea sshPublicKeyTextArea = new JTextArea();
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
        entryFilePathLabel.setBounds(141, 250, 107, 30);
        jobPanel.add(entryFilePathLabel);

        entryFilePathTextField = new JTextField();
        entryFilePathTextField.setBounds(283, 250, 130, 30);
        jobPanel.add(entryFilePathTextField);
        entryFilePathTextField.setColumns(10);


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
        appParamLabel.setBounds(141, 285, 107, 30);
        jobPanel.add(appParamLabel);

        appParamTextField = new JTextField();
        appParamTextField.setColumns(10);
        appParamTextField.setBounds(283, 285, 130, 30);
        jobPanel.add(appParamTextField);

        archivePathLabel = new JLabel("Archive Path");
        archivePathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        archivePathLabel.setBounds(141, 320, 107, 30);
        jobPanel.add(archivePathLabel);

        archivePathTextField = new JTextField();
        archivePathTextField.setColumns(10);
        archivePathTextField.setBounds(283, 320, 130, 30);
        jobPanel.add(archivePathTextField);

        nameLabel = new JLabel("Name");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBounds(141, 355, 107, 30);
        jobPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setColumns(10);
        nameTextField.setBounds(283, 355, 130, 30);
        jobPanel.add(nameTextField);


        final JPanel profilePanel = new JPanel();
        layeredPane.add(profilePanel, "name_3361104455587356");
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
                switchPanels(panel);
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
//                                    http://localhost:8000/services/job/get_log/?job_id=1
                                    // create a JTextArea
                                    JTextArea textArea = new JTextArea(16, 30);
                                    JsonParser parser = new JsonParser();
                                    JsonElement jsonTree = parser.parse(res);

                                    if(jsonTree.isJsonObject()) {

                                        JsonObject jsonObject = jsonTree.getAsJsonObject();
                                        JsonElement elem = jsonObject.get("result");
                                        res = elem.toString();
                                        if(elem.isJsonObject()){
//                                            res = elem.getAsJsonObject().get("logs").toString();
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
                            for(Object obj:jsonObjList){
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
                        switchPanels(jobPanel);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mntmJob.setBorder(new LineBorder(new Color(0, 0, 0)));
        mntmJob.setSelected(true);
        menuBar.add(mntmJob);

        JMenuItem mntmProfile = new JMenuItem("Profile");
        mntmProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

                        profilePanel.remove(sharingCreditValue);
                        JLabel label = new JLabel(""+new_sharing_credit);
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setBounds(294, 108, 127, 39);
                        profilePanel.add(label);
                        profilePanel.add(label);

                        profilePanel.remove(usingCreditValue);
                        JLabel label_2 = new JLabel(""+new_using_credit);
                        label_2.setHorizontalAlignment(SwingConstants.CENTER);
                        label_2.setBounds(294, 176, 127, 39);
                        profilePanel.add(label_2);
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

                    if (entry_file.equals("")) {
                        JOptionPane.showMessageDialog(null,"Entry file can not be empty");
                        return;
                    }
                    entryFilePathTextField.setText("");
                    archivePathTextField.setText("");
                    nameTextField.setText("");
                    appParamTextField.setText("");
                    if (archives.equals("")) {
                        archives = "hdfs:///user/root/mnist/input/data/mnist.zip#mnist";
                    }
                    if (app_params.equals("")) {
                        app_params = "--output mnist/output --format csv";
                    }
                    if (name.equals("")) {
                        name = "MNIST Data Convert 2";
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

                    String res = req.execute();
                    System.out.println(res);
                    JOptionPane.showMessageDialog(null,"Submit job successfully");



                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        /* Test running python script */
        btnAddNewMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("add machine button click");
                String cpu_cores = coreNumtextField.getText();
                String memory_size = memoryTextField.getText();
                // [TBD] Add the error handling of the input here.
                String msg = MachineLib.initWorker(cpu_cores, memory_size);
                System.out.println("result=" + msg);
            }

//            public String initWorker(String cpu_share, String memory_share) {
//                try {
//                    InputStream ins = getClass().getResourceAsStream("init_worker.py");
//                    File tempFile = File.createTempFile("turtle", "dover");
//                    FileOutputStream outputStream = new FileOutputStream(tempFile);
//                    byte[] buffer = new byte[1024];
//                    int numbytes = 0;
//                    while((numbytes = ins.read(buffer)) > -1) {
//                        System.out.println("numbytes=" + numbytes);
//                        outputStream.write(buffer, 0, numbytes);
//                    }
//                    outputStream.close();
//                    tempFile.deleteOnExit();
//
//                    System.out.println("tempFile path=" + tempFile.getAbsolutePath());
//                    Process process = Runtime.getRuntime().exec("python3 " + tempFile.getAbsolutePath());
//                    int exitCode = process.waitFor();
//                    System.out.println("exitCode=" + exitCode);
//                    Scanner scanner = new Scanner(process.getInputStream());
//                    return scanner.next();
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                    return exception.getMessage();
//                }
//            }
        });

    }
}
