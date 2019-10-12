package rubacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rubacha.translation.NumberToStringTranslator;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "rubacha")
public class Application {
    public static void main(String[] Args) {
        SpringApplication.run(Application.class, Args);
        openHomePage();

    }

    private static void openHomePage() {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8090/form");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
