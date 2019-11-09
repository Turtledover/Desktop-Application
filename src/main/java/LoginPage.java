import org.apache.http.client.ClientProtocolException;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginPage extends JFrame {

    public static void main(String[] args) {
        LoginPage frameTabel = new LoginPage();
    }

    JPanel panel = new JPanel();
    JLabel lblUsername = new JLabel("Username");
    JLabel lblPassword = new JLabel("Password");
    JLabel lblRegister = new JLabel("New User? Register Here");
    JButton blogin = new JButton("Login");
    JButton cancel = new JButton("Cancel");
    JTextField username = new JTextField(15);
    JPasswordField password = new JPasswordField(15);

    LoginPage(){
        super("Distributed Marketplace");
        setSize(600,400);
        setLocation(400,200);
        panel.setLayout (null);

        lblUsername.setBounds(180, 95, 90, 40);
        lblPassword.setBounds(180, 140, 90, 40);
        lblRegister.setBounds(220, 230, 200, 40);
        username.setBounds(260,95,150,40);
        password.setBounds(260,140,150,40);
        blogin.setBounds(180,190,100,40);
        cancel.setBounds(310, 190, 100, 40);


        panel.add(lblUsername);
        panel.add(lblPassword);
        panel.add(lblRegister);
        panel.add(username);
        panel.add(password);
        panel.add(blogin);
        panel.add(cancel);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionlogin();
    }

    public void actionlogin(){
        blogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String name = username.getText();
                String pwd = password.getText();
                Connect.HttpGetAndParam logoutRequest =
                        new Connect.HttpGetAndParam(Connect.master_base_url + "/login/");

                Connect.HttpGetAndParam formRequest =
                        new Connect.HttpGetAndParam(Connect.master_base_url + "/login/");
                Connect.HttpPostAndParam postRequest =
                        new Connect.HttpPostAndParam(Connect.master_base_url + "/login/");
                try {
                    String response2 = formRequest.execute();

                    // post to board
                    postRequest.addParameter("username", name);
                    postRequest.addParameter("password", pwd);
                    postRequest.addParameter("csrfmiddlewaretoken",
                            Connect.getCookieByName("csrftoken")); // with csrf token
                    postRequest.setHeader("X-CSRFToken", Connect.getCookieByName("csrftoken"));
                    String postResponse = postRequest.execute();
                    if (postResponse.indexOf("Log") <= 0) {
                        Navigation regFace =new Navigation();
                        regFace.setVisible(true);
                        dispose();
                    } else {
                      JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                        username.setText("");
                        password.setText("");
                        username.requestFocus();
                    }
                } catch (ClientProtocolException e) {
                    System.out.println("Bad Protocol");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
    }

}