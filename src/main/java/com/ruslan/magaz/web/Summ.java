package com.ruslan.magaz.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cal")
public class Summ {

    @RequestMapping(value = "/sum")
    public @ResponseBody
    Integer service(@RequestParam(value="parm1", required=true) Integer par1,
                    @RequestParam(value="parm2", required=true) Integer par2) throws ServletException, IOException {
        return par1 + par2;
    }
    
    @RequestMapping(value = "/mul")
    public @ResponseBody
    Integer service1(@RequestParam(value="parm1", required=true) Integer par1,
                    @RequestParam(value="parm2", required=true) Integer par2) throws ServletException, IOException {
        return par1 * par2;
    }

}
