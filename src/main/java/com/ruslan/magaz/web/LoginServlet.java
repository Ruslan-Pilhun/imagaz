package com.ruslan.magaz.web;

import java.io.IOException;




import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ruslan.magaz.Cart;
import com.ruslan.magaz.User;
@Controller
public class LoginServlet{

    private MerchDao mDao;
    
    @Autowired
    public LoginServlet(MerchDao mDao) {
        this.mDao = mDao;
    }
    
    @RequestMapping(value="/login")
    public @ResponseBody
    void logIn(HttpServletRequest req, HttpServletResponse res, @RequestParam("j_username") String login, @RequestParam("j_password") String password) throws ServletException, IOException{
        
        // TODO Auto-generated method stub
        //String login = @RequestParam("login") String login;
        //String pass = req.getParameter("pass");
        User thisUser = mDao.getUserByLogin(login);
        if (password.equals(thisUser.getPassword())){
            //HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpSession session = req.getSession(true);
            session.setAttribute("user", thisUser);
            session.setAttribute("cart", new Cart());
            (res).setStatus(200);
        }else{
            (res).sendError(403);
        }
    }


}
