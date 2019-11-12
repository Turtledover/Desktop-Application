import java.io.*;
import java.lang.*;
import java.util.*;

final public class MachineLib {

    private static String copyToTemp(InputStream ins) {
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

    public static String initWorker(String authorized_key_path, String cpu_share, String memory_share, String public_key) {
        try {
            InputStream ins = MachineLib.class.getResourceAsStream("init_worker.py");
            String tempPath = copyToTemp(ins);
            ins.close();

            System.out.println("tempFile path=" + tempPath);
            Connect.getCookies();
            Map<String, String> args = new HashMap<>();
            args.put("authorized-key-path", authorized_key_path);
            args.put("cpu-cores", cpu_share);
            args.put("memory-size", memory_share);
//            args.put("public-key", public_key);
            args.put("sessionid", Connect.getCookieByName("sessionid"));
            args.put("csrftoken", Connect.getCookieByName("csrftoken"));
            StringBuilder script_args = new StringBuilder();
            for (String key : args.keySet()) {
                script_args.append(" --" + key + " " + args.get(key));
            }
            System.out.println("python3 " + tempPath + script_args.toString());
            Process process = Runtime.getRuntime().exec("python3 " + tempPath + script_args.toString());
            int exitCode = process.waitFor();
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            Scanner scanner2 = new Scanner(process.getErrorStream());
            while (scanner2.hasNext()) {
                System.out.println(scanner2.nextLine());
            }
            System.out.println("exitCode=" + exitCode);
            return "Success!";
        } catch (Exception exception) {
            exception.printStackTrace();
            return exception.getMessage();
        }
    }
}
