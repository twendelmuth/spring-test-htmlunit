package spring.test.htmlunit.controller;

import java.util.Arrays;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import spring.test.htmlunit.configuration.AppConfig;

@WebMvcTest(WelcomeController.class)
@ContextConfiguration(classes = {AppConfig.class})
public class WelcomeControllerMockMvc {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void ajaxPost() throws Exception {
        final String content = EntityUtils
                .toString(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("subject", "subject test"),
                        new BasicNameValuePair("message", "message test"))));

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/test/post").content(content)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("subject test message test"));
    }

    @Test
    void ajaxPost_asMockMvcWebClientBuilder() throws Exception {
        final String content = "subject test message test";

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/test/post").content(content)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("subject test message test"));
    }

}
