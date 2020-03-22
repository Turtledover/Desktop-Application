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

    public static boolean initWorker(String authorized_key_path, String cpu_share, String memory_share, String public_key, String start_time, String end_time, boolean enable_machine_interval) {
        try {
            InputStream ins = MachineLib.class.getResourceAsStream("init_worker.py");
            String tempPath = copyToTemp(ins);
            ins.close();

            System.out.println("tempFile path=" + tempPath);
            System.out.println("start_time" + end_time);
            System.out.println("end_time" + end_time);

            Connect.getCookies();
            Map<String, String> args = new HashMap<>();
            args.put("authorized-key-path", authorized_key_path);
            args.put("cpu-cores", cpu_share);
            args.put("memory-size", memory_share);
            if (enable_machine_interval) {
                args.put("start-time", start_time);
                args.put("end-time", end_time);
            }

//            args.put("public-key", public_key);
            args.put("sessionid", Connect.getCookieByName("sessionid"));
            args.put("csrftoken", Connect.getCookieByName("csrftoken"));
            args.put("master-url", Connect.master_base_url);

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

            if(exitCode == 0) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
