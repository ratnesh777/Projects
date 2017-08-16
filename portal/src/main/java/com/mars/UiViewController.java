package com.mars;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UiViewController
{

	@RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
      return "forward:/";
    }
    
}
