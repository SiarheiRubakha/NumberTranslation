package rubacha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rubacha.translation.NumberToStringTranslator;


@SpringBootApplication(scanBasePackages = "rubacha")
public class Application {

    private static final String LOCALHOST = "http://localhost:8090/form";

    public static void main(String[] Args) {
        SpringApplication.run(Application.class, Args);
        openHomePage();

    }

    private static void openHomePage() {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 url.dll,FileProtocolHandler " +LOCALHOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
