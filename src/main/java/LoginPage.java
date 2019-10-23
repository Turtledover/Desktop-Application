import okhttp3.*;
import org.riversun.okhttp3.OkHttp3CookieHelper;

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

                OkHttpClient client = new OkHttpClient();
                String url = "http://localhost:8000/login/";
//                OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
//                cookieHelper.setCookie(url, "csrfmiddlewaretoken",
//                        "jSgfpuhKKZcK8K2NKd1J5etriDxQqRFoQGsTTNeB0O6fSPwyQFXb3DV10FPGxovB");
//
//                OkHttpClient client = new OkHttpClient.Builder()
//                        .cookieJar(cookieHelper.cookieJar())
//                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("username", name) // luyao
                        .add("password", pwd) // liluyao123
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if(response.code() == 200){
                        MachinePage regFace =new MachinePage();
                        regFace.setVisible(true);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                        username.setText("");
                        password.setText("");
                        username.requestFocus();
                    }
                    // Do something with the response.
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                if(puname.equals("test") && ppaswd.equals("12345")) {
//                    MachinePage regFace =new MachinePage();
//                    regFace.setVisible(true);
//                    dispose();
//                } else {
//
//                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
//                    username.setText("");
//                    password.setText("");
//                    username.requestFocus();
//                }

            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
    }

}