package spring.test.htmlunit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import spring.test.htmlunit.configuration.SpringBootStarter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = {SpringBootStarter.class})
class WelcomeControllerNativeWebClientTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    void setup() throws Exception {
        webClient = new WebClient();
        // client settings
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
    }

    @Test
    void textPresent() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        HtmlPage page = webClient.getPage("http://localhost:"
                + port);
        assertNotNull(page.getFirstByXPath("//h4[contains(text(), 'Hello to Thymeleaf Guillaume')]"));
    }

    @Test
    void ajaxPost() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        HtmlPage page = webClient.getPage("http://localhost:"
                + port);
        HtmlButton button = page.getFirstByXPath("//button[contains(text(), 'Test post')]");
        button.click();

        // on attends la fin des traitements ajax
        webClient.waitForBackgroundJavaScript(10000);
        HtmlElement span = page.getHtmlElementById("result");
        assertNotNull(span);
        assertEquals("subject test message test", span.asText());
    }
}
