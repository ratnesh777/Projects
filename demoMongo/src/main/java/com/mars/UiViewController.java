package com.mars;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava on 9/14/2016.
 */

@Controller
public class UiViewController
{

    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect()
    {
        return "forward:/";
    }

}
