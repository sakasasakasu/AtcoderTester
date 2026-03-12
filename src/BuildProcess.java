import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BuildProcess {
    public static void run(String targetFile, String inputData, String successData) {
        try{
        ProcessBuilder check = new ProcessBuilder("java", targetFile);
        check.redirectErrorStream(true);
        Process process = check.start();

        try (PrintWriter result = new PrintWriter(new OutputStreamWriter(process.getOutputStream(),StandardCharsets.UTF_8)) ) {
            result.print(inputData);
            result.flush();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),StandardCharsets.UTF_8))) {
            StringBuilder answer = new StringBuilder();
            String line;
            System.out.println("==結果==");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                answer.append(line);
            }
            if (Objects.equals(answer.toString().trim(), successData.trim())) {
                System.out.println("AC" + "\n" + answer + successData);

            } else {
                System.out.println("WA" + "\n" + answer + successData);
            }
        }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
