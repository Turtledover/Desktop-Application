import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.ClientProtocolException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegisterPage extends JFrame {

    public static void main(String[] args) {
        LoginPage frameTabel = new LoginPage();
    }

    JPanel panel = new JPanel();
    JLabel lblUsername = new JLabel("Username");
    JLabel lblPassword = new JLabel("Password1");
    JLabel lblPassword2 = new JLabel("Password2");
    JButton bregister = new JButton("Register");
    JButton cancel = new JButton("Cancel");
    JTextField username = new JTextField(15);
    JPasswordField password = new JPasswordField(15);
    JPasswordField password2 = new JPasswordField(15);

    RegisterPage(){
        super("Welcome to Join Distributed Marketplace");
        setSize(600,400);
        setLocation(400,200);
        panel.setLayout (null);

        lblUsername.setBounds(180, 75, 90, 40);
        lblPassword.setBounds(180, 120, 90, 40);
        lblPassword2.setBounds(180, 165, 90, 40);

        username.setBounds(260,75,150,40);
        password.setBounds(260,120,150,40);
        password2.setBounds(260,165,150,40);
        bregister.setBounds(180,220,100,40);
        cancel.setBounds(310, 220, 100, 40);


        panel.add(lblUsername);
        panel.add(lblPassword);
        panel.add(lblPassword2);
        panel.add(username);
        panel.add(password);
        panel.add(password2);
        panel.add(bregister);
        panel.add(cancel);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionRegister();
    }

    public void actionRegister(){
        bregister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String name = username.getText();
                String pwd = password.getText();
                String pwd2 = password2.getText();

                Connect.HttpGetAndParam formRequest =
                        new Connect.HttpGetAndParam(Connect.master_base_url + "/signup/");
                Connect.HttpPostAndParam postRequest =
                        new Connect.HttpPostAndParam(Connect.master_base_url + "/signup/");
                try {
                    String res = formRequest.execute();

                    // post to board
                    postRequest.addParameter("username", name);
                    postRequest.addParameter("password1", pwd);
                    postRequest.addParameter("password2", pwd2);
                    postRequest.addParameter("csrfmiddlewaretoken",
                            Connect.getCookieByName("csrftoken")); // with csrf token
                    postRequest.setHeader("X-CSRFToken", Connect.getCookieByName("csrftoken"));
                    String postResponse = postRequest.execute();
                    postResponse= StringEscapeUtils.unescapeHtml4(postResponse);
//                    System.out.println(postResponse);
                    if(postResponse.contains("<p style=\"color: red\">")){
                        int start = postResponse.indexOf("<p style=\"color: red\">");
                        int last = postResponse.indexOf("</p>", start);
                        String errorMsg = postResponse.substring(start+22, last);

                        JOptionPane.showMessageDialog(null,errorMsg);
                    }else{
                        Navigation regFace =new Navigation();
                        regFace.setVisible(true);
                        dispose();
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
