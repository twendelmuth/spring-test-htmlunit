package spring.test.htmlunit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import spring.test.htmlunit.configuration.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
class WelcomeControllerTest {

	private WebClient webClient;

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	void setup() throws Exception {
		webClient = MockMvcWebClientBuilder.webAppContextSetup(wac).build();
		  //client settings
		webClient.getOptions().setJavaScriptEnabled(true); 
	    webClient.getOptions().setThrowExceptionOnScriptError(false); 
	    webClient.getOptions().setCssEnabled(false); 
	}

	@Test
	void textPresent() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlPage page = webClient.getPage("http://localhost:8080");
		assertNotNull(page.getFirstByXPath("//h4[contains(text(), 'Hello to Thymeleaf Guillaume')]"));
	}

	@Test
	void ajaxPost() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlPage page = webClient.getPage("http://localhost:8080");
		System.out.println(page.asXml());
		HtmlButton button = page.getFirstByXPath("//button[contains(text(), 'Test post')]");
		button.click();
		// on attends la fin des traitements ajax
		webClient.waitForBackgroundJavaScript(10000);
		HtmlElement span = page.getHtmlElementById("result");
		assertNotNull(span);
		assertEquals("subject test message test", span.asText());
	}
}
