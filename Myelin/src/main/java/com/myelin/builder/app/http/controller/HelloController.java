package com.myelin.builder.app.http.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myelin.builder.app.entity.MyelinContentPlan;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

//	@Autowired
//	private HelloWorldService helloWorldService;
	
   
    
    @RequestMapping(value = "/addLtmContent", method = RequestMethod.POST)
	@ResponseBody    
    public String submit(@Valid @ModelAttribute("ltmContent")MyelinContentPlan ltmContentPlan, 
      BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("email", ltmContentPlan.getCustEmail());
        model.addAttribute("content", ltmContentPlan.getMyelinContent());
        model.addAttribute("id", ltmContentPlan.getId());
        return "employeeView";
    }
}
