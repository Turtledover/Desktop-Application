import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class JobPage {
    private JButton btnAddJob;
    private JLabel entryFilePathLabel;
    private JLabel libsLabel;
    private JLabel archivePathLabel;
    private JLabel nameLabel;
    private JLabel appParamLabel;
    private JTextField archivePathTextField;
    private JTextField nameTextField;
    private JTextField appParamTextField;
    private JTextField entryFilePathTextField;
    private JTextField libsTextField;
    private JTable jobListTable;
    final JScrollPane scrollPane_1;
    public ListJobTask listJobTask;

    public JPanel jobPanel;
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
                                JsonObject jsonObject = jsonTree.getAsJsonObject();
                        		JsonElement result = jsonObject.get("result");
                        		JsonElement logs = result.getAsJsonObject().get("logs");
                        		JsonElement executors = logs.getAsJsonArray().get(0).getAsJsonObject().get("executors");

                        		Container cont = new Container();                        		
                        		int rows = executors.getAsJsonArray().size();
                        		cont.setLayout(new GridLayout(1, rows, 0, 0));
                        		for(int i = 0; i < rows; i++) {
                        			JsonElement executor = executors.getAsJsonArray().get(i);
                        			String isDriver = executor.getAsJsonObject().get("isDriver").toString();
                        			String stdout = executor.getAsJsonObject().get("stdout").toString();
                        			String stderr = executor.getAsJsonObject().get("stderr").toString();
                        			Container innercont = new Container();
                        			BoxLayout layout = new BoxLayout(innercont, BoxLayout.Y_AXIS); 
                        			innercont.setLayout(layout);			
                        			
                        			JTextArea isDriverText = new JTextArea(2, 30);
                        			isDriverText.setLineWrap(true);
                        			isDriverText.setWrapStyleWord(true); 
                        			isDriverText.setEditable(false);
                        			isDriverText.setText(isDriver);
                        			innercont.add(isDriverText);
                        			
                        			JTextArea stdoutText = new JTextArea(30, 30);
                        			stdoutText.setLineWrap(true);
                        			stdoutText.setWrapStyleWord(true); 
                        			stdoutText.setEditable(false);
                        			stdoutText.setText(stdout);
                        			innercont.add(stdoutText);
                        			
                        			JTextArea stderrText = new JTextArea(30, 30);
                        			stderrText.setLineWrap(true);
                        			stderrText.setWrapStyleWord(true); 
                        			stderrText.setEditable(false);
                        			stderrText.setText(stderr);
                        			innercont.add(stderrText);
                        	
                        			cont.add(innercont);
                        		}
                                

                                // wrap a scrollpane around it
                                JScrollPane scrollPane = new JScrollPane(textArea);
                        		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        		
                        		scrollPane.getViewport().setView(cont);

                                // display them in a message dialog
                                JOptionPane.showMessageDialog(null, scrollPane);
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
    public JobPage() {
        jobPanel = new JPanel();
        jobPanel.setLayout(null);
        listJobTask = new ListJobTask();
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


        scrollPane_1 = new JScrollPane();
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
                            JOptionPane.showMessageDialog(null,"Submit job successfully");
                        } else {
                            JOptionPane.showMessageDialog(null,"Submit job failed");
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }
}
