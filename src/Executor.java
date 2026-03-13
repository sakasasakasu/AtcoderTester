import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

public class Executor {
    public static String run(String targetFile, String inputData, String successData) {
        StringBuilder results = new StringBuilder();
        try{
        ProcessBuilder check = new ProcessBuilder("java" , targetFile);
        check.redirectErrorStream(true);
        Process process = check.start();
        int exitCode = process.waitFor();

        try (PrintWriter result = new PrintWriter(new OutputStreamWriter(process.getOutputStream(), Charset.forName("MS932"))) ) {
            result.print(inputData);
            result.flush();
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),Charset.forName("MS932")))) {
            StringBuilder answer = new StringBuilder();
            String line;
            results.append("\n");
            while ((line = reader.readLine()) != null) {
                answer.append(line);
                results.append(line);
                results.append("\n");
            }

            if (exitCode != 0) {
                System.out.println("RE" + "\n" + answer.toString().trim() + successData.trim());
                results.append("\nRE\n");
            }

            else if (Objects.equals(answer.toString().trim(), successData.trim())) {
                System.out.println("AC" + "\n" + answer.toString().trim() + successData.trim());
                results.append("\nAC\n");

            } else {
                System.out.println("WA" + "\n" + answer.toString().trim() + successData.trim());
                results.append("\nWA\n");
            }
        }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results.toString();
    }
}
