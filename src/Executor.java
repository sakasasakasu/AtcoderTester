import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Executor {
    public static String run(String targetFile, String inputData, String successData, String limitMemory, String limitTime) {
        StringBuilder results = new StringBuilder();
        try{
        ProcessBuilder check = new ProcessBuilder("java" , "-Xmx" + limitMemory + "m", targetFile);
        check.redirectErrorStream(true);
        Process process = check.start();

        try (PrintWriter result = new PrintWriter(new OutputStreamWriter(process.getOutputStream(), Charset.forName("MS932"))) ) {
            result.print(inputData);
            result.flush();
        }

        //タイムアウト
        boolean finish = process.waitFor(Integer.parseInt(limitTime), TimeUnit.SECONDS);
        if (!finish) {
            process.destroyForcibly();
            System.out.println("TLE");
            results.append("\nTLE\n");
        }


        int exitCode = process.waitFor();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),Charset.forName("MS932")))) {
            StringBuilder answer = new StringBuilder();
            String line;
            results.append("\n");
            while ((line = reader.readLine()) != null) {
                answer.append(line);
                results.append(line);
                results.append("\n");
            }

            String trimedSuccessData = successData.replace("\n", "").replace("\r", "").trim();

            if (exitCode != 0) {
                System.out.println("RE" + "\n" + answer.toString().trim() + trimedSuccessData);
                results.append("\nRE\n");
            }

            else if (Objects.equals(answer.toString().trim(), trimedSuccessData)) {
                System.out.println("AC" + "\n" + answer.toString().trim() + trimedSuccessData);
                results.append("\nAC\n");

            } else {
                System.out.println("WA" + "\n" + answer.toString().trim() + trimedSuccessData);
                results.append("\nWA\n");
            }
        }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results.toString();
    }
}
