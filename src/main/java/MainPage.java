import javax.swing.*;


public class MainPage extends JFrame {

    public static void main(String[] args) {
        MainPage frameTabel = new MainPage("Testing...");
    }

    JLabel welcome = new JLabel("Welcome to Distributed MarketPlace!");
    JPanel panel = new JPanel();

    MainPage(String msg){
        super("Distributed Marketplace");
        setSize(600,400);
        setLocation(400,200);
        panel.setLayout (null);

        welcome = new JLabel(msg);
        welcome.setBounds(70,50,300,60);

        panel.add(welcome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}