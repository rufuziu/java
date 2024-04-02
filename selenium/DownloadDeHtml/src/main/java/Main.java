import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.JavascriptExecutor;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static String url =
            "https://www.google.com.br";
    private static String currentUrl,initialWord, domain;
    private static int start,end;
    private static void CheckDomain(WebDriver wd)
    {
        currentUrl = wd.getCurrentUrl();
        initialWord = currentUrl.contains("www.") ? "www." : "https://";
        start = currentUrl.lastIndexOf(initialWord) + initialWord.length();
        end = currentUrl.indexOf(".", start, currentUrl.length());
        domain = currentUrl.substring(start, end);
    }

    public static void main(String[] args) {

        String scStr, path, fileName, oldDomain, userAgent;
        int fileCounter;
        Boolean running;
        EdgeOptions options;
        WebDriver wd;
        JavascriptExecutor js;

        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");

        running = Boolean.TRUE;

        try {
            // Getting the project root directory
            path = new StringBuilder()
                    .append(System.getProperty("user.dir"))
                    .append("\\body_contents")
                    .toString();

            // Get first URL
            System.out.print("Inform the initial URL:\nhttps://");
            try (Scanner sc = new Scanner(System.in)) {
                scStr = sc.nextLine().trim();
                url = new StringBuilder()
                        .append("https://")
                        .append(scStr)
                        .append("/")
                        .toString();
            } catch (Exception e) {
                System.out.println("Invalid URL!");

                url = "https://www.google.com.br/";
            }

            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0";

            System.out.println(url);
            // Initializing WebDriver
            options = new EdgeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("user-agent="+userAgent);
            options.addArguments("--disable-features=OriginTrials");
            options.addArguments("--disable-features=InterestCohort");
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

            wd = new EdgeDriver(options);
            js = (JavascriptExecutor) wd;
            js.executeScript
                    ("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

            wd.get(url);

            Thread.sleep(3000);

            fileCounter = 1;
            CheckDomain(wd);
            oldDomain = domain;

            while (running) {
                CheckDomain(wd);
                if (!domain.contains(oldDomain)) {
                    oldDomain = domain;
                    fileCounter=1;
                } else {
                    fileCounter++;
                }

                fileName = new StringBuilder()
                        .append(path)
                        .append("\\")
                        .append(domain)
                        .append("_")
                        .append(fileCounter)
                        .append(".html")
                        .toString();

                try (FileWriter fw = new FileWriter(fileName)) {
                    fw.write(wd.getPageSource());
                    System.out.println(
                            new StringBuilder()
                                    .append("Successfully written")
                                    .append("  ")
                                    .append(domain)
                                    .append("_")
                                    .append(fileCounter)
                                    .toString());
                } catch (Exception e) {
                    System.out.println("Excpetion");
                }

                Thread.sleep(3000);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}