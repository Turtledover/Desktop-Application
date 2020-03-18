import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MachinePage {
    public  JPanel machinePanel;
    public JButton btnAddNewMachine;
    public JButton btnGetContributionHistory;
    public JTable machineTable;
    public JTextField coreNumtextField;
    public JTextField memoryTextField;
    public JButton btnRemoveMachine;
    public JTextField authorizedKeyPathTextField;
    public JTextField startTimeTextField;
    public JTextField EndTimeTextField;
    public JTextArea sshPublicKeyTextArea;

    public MachinePage() {
        machinePanel = new JPanel();

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
        scrollPane.setBounds(93, 53, 392, 100);
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

        JLabel startTimeLabel = new JLabel("Start time");
        startTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startTimeLabel.setBounds(90, 164, 161, 29);
        machinePanel.add(startTimeLabel);

        startTimeTextField = new JTextField();
        startTimeTextField.setColumns(10);
        startTimeTextField.setBounds(286, 164, 130, 26);
        machinePanel.add(startTimeTextField);

        JLabel EndTimeLabel = new JLabel("End time");
        EndTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        EndTimeLabel.setBounds(90, 192, 161, 29);
        machinePanel.add(EndTimeLabel);

        EndTimeTextField = new JTextField();
        EndTimeTextField.setColumns(10);
        EndTimeTextField.setBounds(286, 192, 130, 26);
        machinePanel.add(EndTimeTextField);

        JLabel authorizedKeyPathLabel = new JLabel("Authorized key path");
        authorizedKeyPathLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorizedKeyPathLabel.setBounds(90, 219, 161, 29);
        machinePanel.add(authorizedKeyPathLabel);

        authorizedKeyPathTextField = new JTextField();
        authorizedKeyPathTextField.setColumns(10);
        authorizedKeyPathTextField.setBounds(286, 220, 130, 26);
        machinePanel.add(authorizedKeyPathTextField);

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



        JLabel sshPublicKeyLabel = new JLabel("SSH Public Key");
        sshPublicKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sshPublicKeyLabel.setBounds(93, 319, 158, 27);
        machinePanel.add(sshPublicKeyLabel);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(286, 324, 130, 44);
        machinePanel.add(scrollPane_2);

        sshPublicKeyTextArea = new JTextArea();
        scrollPane_2.setViewportView(sshPublicKeyTextArea);
        btnAddNewMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("add machine button click");
                String authorized_key_path = authorizedKeyPathTextField.getText();
                String cpu_cores = coreNumtextField.getText();
                String memory_size = memoryTextField.getText();
                String public_key = sshPublicKeyTextArea.getText();
                String start_time = startTimeTextField.getText();
                String end_time = EndTimeTextField.getText();
                // [TBD] Add the error handling of the input here.
                boolean success = MachineLib.initWorker(authorized_key_path, cpu_cores, memory_size, public_key, start_time, end_time);

                if(success) {
                    JOptionPane.showMessageDialog(null,"Success!");
                    refresh_machine_list();
//                    switchPanels(machinePanel);
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
                    JOptionPane.showMessageDialog(null, new JScrollPane(table));
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
    public void refresh_machine_list() {
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
