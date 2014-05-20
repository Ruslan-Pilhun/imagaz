package com.ruslan.magaz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruslan.magaz.Item;
import com.ruslan.magaz.ItemService;
import com.ruslan.magaz.Order;

@Controller
public class OrderServlet {
    
    private final MerchDao dao;
    
    @Autowired
    public OrderServlet(MerchDao dao) {
        this.dao = dao;
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value="/order")
    public @ResponseBody
    Order showOrder() throws ServletException, IOException {
        return dao.getOrderById(1);
    }

}