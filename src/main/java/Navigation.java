import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.net.URLEncoder;

public class Navigation extends JFrame{

    private JFrame frame;
    private JLayeredPane layeredPane;
    private JTable table;
    private JTextField textField;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTable table_1;
    private JTextField textField_8;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_App;
    private JButton btnLogou;
    private JButton btnAddJob;
    private JLabel lblNewLabel_2;
    private JLabel lblModelPath;
    private JLabel lblAchivePath;
    private JLabel lblAppParam;

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

        JLabel lblNewLabel = new JLabel("Machine List");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(186, 6, 180, 57);
        panel.add(lblNewLabel);

        JButton btnAddNewMachine = new JButton("Add new machine");
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
        scrollPane.setViewportView(table);

        JLabel lblCoreNum_1 = new JLabel("Core num");
        lblCoreNum_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoreNum_1.setBounds(93, 248, 158, 29);
        panel.add(lblCoreNum_1);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(286, 249, 130, 26);
        panel.add(textField_6);

        JLabel lblIpPort = new JLabel("Memory");
        lblIpPort.setHorizontalAlignment(SwingConstants.CENTER);
        lblIpPort.setBounds(93, 280, 158, 27);
        panel.add(lblIpPort);

        textField_7 = new JTextField();
        textField_7.setColumns(10);
        textField_7.setBounds(286, 281, 130, 26);
        panel.add(textField_7);

        JLabel lblSshKeyPath = new JLabel("Authorized key path");
        lblSshKeyPath.setHorizontalAlignment(SwingConstants.CENTER);
        lblSshKeyPath.setBounds(90, 219, 161, 29);
        panel.add(lblSshKeyPath);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(286, 220, 130, 26);
        panel.add(textField_3);

        JLabel lblSshPublicKey = new JLabel("SSH Public Key");
        lblSshPublicKey.setHorizontalAlignment(SwingConstants.CENTER);
        lblSshPublicKey.setBounds(93, 319, 158, 27);
        panel.add(lblSshPublicKey);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(286, 324, 130, 44);
        panel.add(scrollPane_2);

        JTextArea textArea = new JTextArea();
        scrollPane_2.setViewportView(textArea);

        final JPanel panel_1 = new JPanel();
        layeredPane.add(panel_1, "name_3361101841548929");
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Job List");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(200, 6, 147, 36);
        panel_1.add(lblNewLabel_1);

        btnAddJob = new JButton("Add Job");
        btnAddJob.setBounds(232, 387, 117, 29);
        panel_1.add(btnAddJob);

        lblNewLabel_2 = new JLabel("Entry File Path*");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(141, 240, 107, 29);
        panel_1.add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(283, 240, 130, 26);
        panel_1.add(textField);
        textField.setColumns(10);


        final JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(48, 50, 454, 193);
        panel_1.add(scrollPane_1);

        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);
        DefaultTableModel model_1 = (DefaultTableModel) table_1.getModel();
        Object[] row_1 = { "", "", "", "", "" };
        model_1.addColumn("job_id");
        model_1.addColumn("name");
        model_1.addColumn("status");
        model_1.addColumn("used_credits");
        model_1.addColumn("duration");
        model_1.addColumn("added");
        model_1.addRow(row_1);
        table_1.setModel(model_1);

        lblAppParam = new JLabel("App Params");
        lblAppParam.setHorizontalAlignment(SwingConstants.CENTER);
        lblAppParam.setBounds(141, 275, 107, 29);
        panel_1.add(lblAppParam);

        textField_App = new JTextField();
        textField_App.setColumns(10);
        textField_App.setBounds(283, 275, 130, 26);
        panel_1.add(textField_App);

        lblModelPath = new JLabel("Archive Path");
        lblModelPath.setHorizontalAlignment(SwingConstants.CENTER);
        lblModelPath.setBounds(141, 307, 107, 29);
        panel_1.add(lblModelPath);

        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(283, 307, 130, 26);
        panel_1.add(textField_8);

        lblAchivePath = new JLabel("Name");
        lblAchivePath.setHorizontalAlignment(SwingConstants.CENTER);
        lblAchivePath.setBounds(141, 345, 107, 29);
        panel_1.add(lblAchivePath);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(283, 345, 130, 26);
        panel_1.add(textField_4);


        final JPanel panel_2 = new JPanel();
        layeredPane.add(panel_2, "name_3361104455587356");
        panel_2.setLayout(null);

        JLabel lblTest = new JLabel("Sharing Credit");
        lblTest.setHorizontalAlignment(SwingConstants.CENTER);
        lblTest.setBounds(124, 108, 127, 39);
        panel_2.add(lblTest);

        final JLabel label = new JLabel(""+SHARING_CREDIT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(294, 108, 127, 39);
        panel_2.add(label);

        JLabel lblUsingCredit = new JLabel("Using Credit");
        lblUsingCredit.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsingCredit.setBounds(124, 176, 127, 39);
        panel_2.add(lblUsingCredit);

        final JLabel label_2 = new JLabel(""+USING_CREDIT);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(294, 176, 127, 39);
        panel_2.add(label_2);

        btnLogou = new JButton("Log Out");
        btnLogou.setBounds(212, 278, 137, 45);
        panel_2.add(btnLogou);
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
                            new Connect.HttpGetAndParam("http://localhost:8000/services/job/list/");
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

                            scrollPane_1.remove(table_1);

                            table_1 = new JTable();
                            scrollPane_1.setViewportView(table_1);

                            boolean addCol = false;
                            DefaultTableModel model_1 = (DefaultTableModel) table_1.getModel();
                            for(Object obj:jsonObjList){
                                List<Object> list  = new ArrayList<>();
                                LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) obj;
                                for (Map.Entry<String, Object> entry: map.entrySet()) {
                                    String k = entry.getKey();
                                    String v = "";
                                    if(entry.getValue() instanceof Integer){
                                        int i_v = (Integer) entry.getValue();
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
                            table_1.setModel(model_1);
                        }
                        switchPanels(panel_1);
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
                            new Connect.HttpGetAndParam("http://localhost:8000/services/credit/check_credit/");
                    String res = req.execute();
                    System.out.println(res);
                    JsonParser parser = new JsonParser();
                    JsonElement jsonTree = parser.parse(res);
                    if(jsonTree.isJsonObject()) {

                        JsonObject jsonObject = jsonTree.getAsJsonObject();
                        double new_using_credit = Double.parseDouble(jsonObject.get("using_credit").toString());
                        double new_sharing_credit= Double.parseDouble(jsonObject.get("sharing_credit").toString());

                        panel_2.remove(label);
                        JLabel label = new JLabel(""+new_sharing_credit);
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setBounds(294, 108, 127, 39);
                        panel_2.add(label);
                        panel_2.add(label);

                        panel_2.remove(label_2);
                        JLabel label_2 = new JLabel(""+new_using_credit);
                        label_2.setHorizontalAlignment(SwingConstants.CENTER);
                        label_2.setBounds(294, 176, 127, 39);
                        panel_2.add(label_2);
                        switchPanels(panel_2);
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
                            new Connect.HttpGetAndParam("http://localhost:8000/services/job/submit/");
                    String entry_file = textField.getText();
                    String archives = textField_8.getText();
                    String name =  textField_4.getText();
                    String app_params = textField_App.getText();

                    if (entry_file.equals("")) {
                        JOptionPane.showMessageDialog(null,"Entry file can not be empty");
                        return;
                    }
                    if (archives.equals("")) {
                        archives = "hdfs:///user/root/mnist/input/data/mnist.zip#mnist";
                    }
                    if (app_params.equals("")) {
                        app_params = " --output mnist/output --format csv";
                    }
                    if (name.equals("")) {
                        name = "MNIST Data Convert";
                    }
                    // http://localhost:8000/services/job/submit/?&
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
                    JOptionPane.showMessageDialog(null,"Submit job successfully, message:" + res);



                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

    }
}
