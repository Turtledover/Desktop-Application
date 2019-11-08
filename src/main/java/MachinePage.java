import sun.nio.ch.IOUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class MachinePage extends JFrame {

    private int numCPU = 8;
    private int numGPU = 1;
    private int numMemory = 16;

    public static void main(String[] args) {
        MachinePage frameTabel = new MachinePage();
    }

    JPanel panel = new JPanel();
    JLabel myCPU = new JLabel("out of  " + numCPU);
    JLabel myGPU = new JLabel("out of  " + numGPU);
    JLabel myMemory = new JLabel("out of  " + numMemory + " GB");
    JLabel lblCPU = new JLabel("CPU Core");
    JLabel lblGPU = new JLabel("GPU");
    JLabel lblMemory = new JLabel("Memory");
    JTextField cpu = new JTextField(10);
    JTextField gpu = new JTextField(10);
    JTextField memory = new JTextField(10);
    JButton bShare = new JButton("Share and Join Marketplace");

    MachinePage(){
        super("Distributed Marketplace");
        setSize(600,400);
        setLocation(400,200);
        panel.setLayout (null);

        lblCPU.setBounds(170, 70, 80, 40);
        lblGPU.setBounds(190, 120, 80, 40);
        lblMemory.setBounds(180, 170, 80, 40);
        myCPU.setBounds(320, 70, 80, 40);
        myGPU.setBounds(320, 120, 80, 40);
        myMemory.setBounds(320, 170, 100, 40);
        cpu.setBounds(240,70,70,40);
        gpu.setBounds(240,120,70,40);
        memory.setBounds(240,170,70,40);
        bShare.setBounds(180,220,190,40);

        panel.add(lblCPU);
        panel.add(lblGPU);
        panel.add(lblMemory);
        panel.add(myCPU);
        panel.add(myGPU);
        panel.add(myMemory);
        panel.add(cpu);
        panel.add(gpu);
        panel.add(memory);
        panel.add(bShare);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        actionMachineShare();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println("machinepage init");

    }

    private String copyToTemp(InputStream ins) {
        try {
            File tempFile = File.createTempFile("turtle", "dover");
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int numbytes = 0;
            while((numbytes = ins.read(buffer)) > -1) {
                outputStream.write(buffer, 0, numbytes);
            }
            outputStream.close();
            tempFile.deleteOnExit();
            return tempFile.getAbsolutePath();
        } catch(IOException e) {
            return null;
        }
    }

    public String initWorker(String cpu_share, String memory_share) {
        try {
            InputStream ins = getClass().getResourceAsStream("init_worker.py");
            String tempPath = copyToTemp(ins);
            ins.close();

            System.out.println("tempFile path=" + tempPath);
            Process process = Runtime.getRuntime().exec("python3 " + tempPath);
            int exitCode = process.waitFor();
            System.out.println("exitCode=" + exitCode);
            Scanner scanner = new Scanner(process.getInputStream());
            return scanner.next();
        } catch (Exception exception) {
            exception.printStackTrace();
            return exception.getMessage();
        }

//        try {
//            Process process = Runtime.getRuntime().exec("python3 init_worker.py " + cpu_share + " " + memory_share);
//            process.waitFor();
//            Scanner scanner = new Scanner(process.getInputStream());
//            return scanner.next();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return exception.getMessage();
//        }
    }

    public void actionMachineShare() {
        bShare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("actionshare click");

                String cpu_share = cpu.getText();
                String gpu_share = gpu.getText();
                String memory_share = memory.getText();
                if(cpu_share.length() > 0 && memory_share.length() > 0) {
                    MainPage regFace =new MainPage(initWorker(cpu_share, memory_share));
                    regFace.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,"CPU Core and Memory cannot be empty");
                    if(cpu_share.length() == 0) cpu.requestFocus();
                    else if(memory_share.length() == 0) memory.requestFocus();
                }

            }
        });
    }
}
