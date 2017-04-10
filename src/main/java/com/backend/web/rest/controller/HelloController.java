package com.backend.web.rest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method= RequestMethod.OPTIONS,produces = "application/json")
    @ResponseBody
    public String hello()
    {
        return "";
    }

    @RequestMapping(value = "/login", method= RequestMethod.OPTIONS,produces = "application/json")
    public String login()
    {
        return "";
    }
    @RequestMapping(value = "/private/application ", method= RequestMethod.OPTIONS,produces = "application/json")
    public String app()
    {
        return "";
    }

}
