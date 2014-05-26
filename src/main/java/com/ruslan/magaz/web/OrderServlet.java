package com.ruslan.magaz.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruslan.magaz.Cart;
import com.ruslan.magaz.Item;
import com.ruslan.magaz.ItemService;
import com.ruslan.magaz.Order;
import com.ruslan.magaz.User;

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
    Order showOrder(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	
    	String username = "";
    	if (session != null){
    		User anUser = (User) session.getAttribute("user");
    		username = (String) anUser.getLogin();
    	}
    	
        if (dao.checkOrderId(id, username)){
        	(res).setStatus(200);
        	return dao.getOrderById(id);    	
        }
        else{
        	(res).sendError(403);
        	return null;
        }
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value="/add")
    public @ResponseBody
    String addToCart(HttpServletRequest req, HttpServletResponse res, @RequestParam("itemid") int itemId) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	
    	if (session != null){
    		Cart aCart = (Cart) session.getAttribute("cart");
    		Item anItem = dao.getItemById(itemId);
    		aCart.addToItemList(anItem);
    		(res).setStatus(200);
    		return "added";
    	}
        else{
        	(res).sendError(403);
        	return "failed";
        }
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value="/remove")
    public @ResponseBody
    String removeFromCart(HttpServletRequest req, HttpServletResponse res, @RequestParam("itemid") int itemId) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	
    	if (session != null){
    		Cart aCart = (Cart) session.getAttribute("cart");
    		Item anItem = dao.getItemById(itemId);
    		aCart.removeFromItemList(anItem);
    		(res).setStatus(200);
    		return "removed";
    	}
        else{
        	(res).sendError(403);
        	return "failed";
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value="/confirm")
    public @ResponseBody
    String confirmOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	
    	if (session != null){
    		Cart aCart = (Cart) session.getAttribute("cart");
    		User anUser = (User) session.getAttribute("user");
    		int orderId = dao.confirmOrder(aCart, anUser);
    		if (orderId >0) {
    			(res).setStatus(200);
    			return "Confirmed! Your order id is: " + orderId;
    		}
    		else
    			return "failed: cart is empty!";
    	}
        else{
        	(res).sendError(403);
        	return "failed";
        }
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value="/cart")
    public @ResponseBody
    Cart showOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	
    	String username = "";
    	if (session != null){
    		Cart aCart = (Cart) session.getAttribute("cart");
    		(res).setStatus(200);
        	return aCart; 
    	}
        	   	
        else{
        	(res).sendError(403);
        	return null;
        }
    }

}