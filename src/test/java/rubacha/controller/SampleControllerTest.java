package rubacha.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;
import rubacha.config.WebConfig;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class SampleControllerTest {

    private SampleController controller =new SampleController();
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getForm() throws Exception{
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/form"))
                .andExpect(status().isOk())
                .andDo(print()).
                        andReturn();
    }

    @Test
    public void submitForm() throws Exception{

        doTestSubmitForm("12","12 - двенадцать");
    }
    @Test
    public void submitForm_bigger_than_long() throws Exception{
        doTestSubmitForm("9223372036854775808","Unable to parse number");
    }

    @Test
    public void submitForm_maximum_long() throws Exception{
        doTestSubmitForm("9223372036854775807","9223372036854775807 - девять квинтиллионов двести двадцать три квадриллиона триста семьдесят два триллиона тридцать шесть миллиардов восемьсот пятьдесят четыре миллиона семьсот семьдесят пять тысяч восемьсот семь");

    }
    private void doTestSubmitForm(String number, String expected)throws Exception{

        MvcResult mvcResult = resultSubmitForm(number);
        FlashMap flashMap = mvcResult.getFlashMap();

        String actual;
        if(flashMap.containsKey("resultSubmitForm"))
            actual = (String) flashMap.get("resultSubmitForm");
        else
            actual = (String) flashMap.get("error");
        assertEquals(expected.trim(), actual.trim());
    }
    private MvcResult resultSubmitForm(String number)throws Exception{
        return mvc.perform(MockMvcRequestBuilders
                .post("/submit_form")
                .param("number", number))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form"))
                .andDo(print()).
                        andReturn();
    }

}