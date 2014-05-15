package com.ruslan.magaz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruslan.magaz.Item;
import com.ruslan.magaz.ItemService;

@Controller
public class SelectQuery {
    
    private final ItemService itemService;
    
    @Autowired
    public SelectQuery(JdbcOperations jdbc, ItemService itemService) {
        this.itemService = itemService;
    }
    
    
    @RequestMapping(value="/list")
    public @ResponseBody
    List<Item> showItemList() throws ServletException, IOException {
        return itemService.getItemList();
    }

}
