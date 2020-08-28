package spring.test.htmlunit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import spring.test.htmlunit.configuration.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
public class WelcomeControllerTest {

	private MockMvc mockMvc;	

    private WebDriver driver;    

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.driver = MockMvcHtmlUnitDriverBuilder.webAppContextSetup(this.wac).build();
	}

	@Test
	public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
	}
	
	
	@Test
    public void driverTest() {
        driver.get("http://localhost:8080/");
        assertEquals("Hello to Thymeleaf Guillaume",driver.findElement(By.xpath("/html/body/h4")).getText());
    }
}
