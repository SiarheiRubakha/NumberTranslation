package rubacha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rubacha.translation.NumberToStringTranslator;

import java.io.IOException;

@Controller
public class SampleController {
    @GetMapping("/form")
    public String getForm(ModelMap map) {


        //map.addAttribute("result","message");
        return "index";
    }


    @PostMapping("/submit_form")
    public String submitForm(@RequestParam("number") Integer asd, RedirectAttributes redirectAttributes) {

        System.out.println(asd);

        NumberToStringTranslator number = new NumberToStringTranslator();
        try {
            redirectAttributes.addFlashAttribute("result", asd.toString() + ": " + number.translate(asd));
        } catch (IOException io) {
            /*Some error notification on UI will be good*/
            redirectAttributes.addFlashAttribute("Unable to parse number");
            return "redirect:/form";
        }
        return "redirect:/form";

    }

}
