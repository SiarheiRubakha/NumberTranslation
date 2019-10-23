package rubacha.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rubacha.config.WebConfig;
import rubacha.translation.NumberToStringTranslator;

import javax.management.Attribute;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class SampleControllerTest {

    SampleController controller =new SampleController();
    @Autowired
    private WebApplicationContext wac;

    public MockMvc mvc;
    @Test
    public void getForm() {
    }

    @Test
    public void submitForm() throws Exception{
        /*String number = "9223372036854775806";
        RedirectAttributes attributes;
        attributes = Mockito.mock(RedirectAttributes.class);
        String submit = controller.submitForm(number, attributes);
        assertTrue(controller.submitResult);
    */
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mvc = builder.build();

        mvc.perform(MockMvcRequestBuilders
                    .post("/submit_form")
                    .param("number","12"))
                    .andExpect(MockMvcResultMatchers.redirectedUrl("redirect:/form"));
                    //.andExpect(MockMvcResultMatchers.flash().attribute("result","12 - двенадцать"));
    }
    @Test
    public void submitForm_bigger_than_long() throws Exception{
        String number = "9223372036854775808";
        RedirectAttributes attributes;
        attributes = Mockito.mock(RedirectAttributes.class);
        String submit = controller.submitForm(number, attributes);
        assertTrue(!controller.submitResult);
    }
}