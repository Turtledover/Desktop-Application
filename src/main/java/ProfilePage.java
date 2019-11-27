import javax.swing.*;

public class ProfilePage {
    public JPanel profilePanel;
    private double USING_CREDIT = 0.0;
    private double SHARING_CREDIT = 0.0;
    public JLabel sharingCreditLabel;
    public JLabel sharingCreditValue;
    public JLabel usingCreditLabel;
    public JLabel usingCreditValue;
    public JButton btnLogou;

    public ProfilePage() {
        profilePanel = new JPanel();
        profilePanel.setLayout(null);

        sharingCreditLabel = new JLabel("Sharing Credit");
        sharingCreditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sharingCreditLabel.setBounds(124, 108, 127, 39);
        profilePanel.add(sharingCreditLabel);

        sharingCreditValue = new JLabel(""+SHARING_CREDIT);
        sharingCreditValue.setHorizontalAlignment(SwingConstants.CENTER);
        sharingCreditValue.setBounds(294, 108, 127, 39);
        profilePanel.add(sharingCreditValue);

        usingCreditLabel = new JLabel("Using Credit");
        usingCreditLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usingCreditLabel.setBounds(124, 176, 127, 39);
        profilePanel.add(usingCreditLabel);

        usingCreditValue = new JLabel(""+USING_CREDIT);
        usingCreditValue.setHorizontalAlignment(SwingConstants.CENTER);
        usingCreditValue.setBounds(294, 176, 127, 39);
        profilePanel.add(usingCreditValue);

        btnLogou = new JButton("Log Out");
        btnLogou.setBounds(212, 278, 137, 45);
        profilePanel.add(btnLogou);
    }
}
