import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

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
    private JButton btnLogou;
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
        lblNewLabel_1.setBounds(211, 6, 147, 36);
        panel_1.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Add Job");
        btnNewButton.setBounds(232, 417, 117, 29);
        panel_1.add(btnNewButton);

        JLabel lblNewLabel_2 = new JLabel("Entry File Path*");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(141, 286, 107, 29);
        panel_1.add(lblNewLabel_2);

        textField = new JTextField();
        textField.setBounds(283, 287, 130, 26);
        panel_1.add(textField);
        textField.setColumns(10);


        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(122, 50, 365, 193);
        panel_1.add(scrollPane_1);

        table_1 = new JTable();
        scrollPane_1.setViewportView(table_1);
        DefaultTableModel model_1 = (DefaultTableModel) table_1.getModel();
        Object[] row_1 = { "1", "ImageNet", "/code", "/model", "Running" };
        model_1.addColumn("ID");
        model_1.addColumn("Job Name");
        model_1.addColumn("Root Path");
        model_1.addColumn("Model Path");
        model_1.addColumn("Status");
        model_1.addRow(row_1);
        table_1.setModel(model_1);

        JLabel lblModelPath = new JLabel("Python File Path");
        lblModelPath.setHorizontalAlignment(SwingConstants.CENTER);
        lblModelPath.setBounds(141, 327, 107, 29);
        panel_1.add(lblModelPath);

        textField_8 = new JTextField();
        textField_8.setColumns(10);
        textField_8.setBounds(283, 328, 130, 26);
        panel_1.add(textField_8);

        JLabel lblAchivePath = new JLabel("Achive Path");
        lblAchivePath.setHorizontalAlignment(SwingConstants.CENTER);
        lblAchivePath.setBounds(141, 371, 107, 29);
        panel_1.add(lblAchivePath);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(283, 372, 130, 26);
        panel_1.add(textField_4);




        final JPanel panel_2 = new JPanel();
        layeredPane.add(panel_2, "name_3361104455587356");
        panel_2.setLayout(null);

        JLabel lblTest = new JLabel("Sharing Credit");
        lblTest.setHorizontalAlignment(SwingConstants.CENTER);
        lblTest.setBounds(124, 118, 127, 39);
        panel_2.add(lblTest);

        JLabel label = new JLabel("0");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(294, 118, 127, 39);
        panel_2.add(label);

        JLabel lblUsingCredit = new JLabel("Using Credit");
        lblUsingCredit.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsingCredit.setBounds(124, 186, 127, 39);
        panel_2.add(lblUsingCredit);

        JLabel label_2 = new JLabel("0");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(294, 186, 127, 39);
        panel_2.add(label_2);

        btnLogou = new JButton("Logout");
        btnLogou.setBounds(212, 288, 117, 29);
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
                switchPanels(panel_1);
            }
        });
        mntmJob.setBorder(new LineBorder(new Color(0, 0, 0)));
        mntmJob.setSelected(true);
        menuBar.add(mntmJob);

        JMenuItem mntmProfile = new JMenuItem("Profile");
        mntmProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchPanels(panel_2);
            }
        });
        mntmProfile.setBorder(new LineBorder(new Color(0, 0, 0)));
        menuBar.add(mntmProfile);
        action();
    }
    public void action(){
        btnLogou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                LoginPage regFace = new LoginPage();
                regFace.setVisible(true);
                dispose();
            }
        });
    }
}
