import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Executor {
    public static String run(String targetFile, String inputData, String successData) {
        StringBuilder results = new StringBuilder();
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
            results.append("\n");
            while ((line = reader.readLine()) != null) {
                answer.append(line);
                results.append(line);
                results.append("\n");
            }
            if (Objects.equals(answer.toString().trim(), successData.trim())) {
                System.out.println("AC" + "\n" + answer + successData);
                results.append("\nAC\n");

            } else {
                System.out.println("WA" + "\n" + answer + successData);
                results.append("\nAC\n");
            }
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results.toString();
    }
}
