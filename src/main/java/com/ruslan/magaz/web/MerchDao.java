package com.ruslan.magaz.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import com.ruslan.magaz.Item;
import com.ruslan.magaz.Order;
import com.ruslan.magaz.User;

public class MerchDao {
    private static final String ITEM_SEL_QUERY = "SELECT id, name, description, category, price, count FROM Merchandise";
    private static final String USER_SEL_QUERY = "SELECT id, login, password, role FROM Users WHERE login = ?";
    private static final String ORDER_SEL_QUERY = "select orders.orderid, users.login, orders.merchid, orders.count, orders.date, orders.status from orders join users on orders.userid = users.id where orderid = ? and users.login = '?'";
    private final JdbcOperations jdbc;
    
    @Autowired
    public MerchDao(JdbcOperations jdbc) {
        this.jdbc = jdbc;
    }
    
    public List<Item> getItemList(){
        return jdbc.query(ITEM_SEL_QUERY,new RowMapper<Item>(){
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item anItem = new Item();
                anItem.setId(rs.getInt("id"));
                anItem.setName(rs.getString("name"));
                anItem.setDescription(rs.getString("description"));
                anItem.setCategory(rs.getString("category"));
                anItem.setPrice(rs.getDouble("price"));
                anItem.setCount(rs.getInt("count"));
                return anItem;
            }
        });
        
    }
    
    public Item getItemById(int id){
        return jdbc.queryForObject(ITEM_SEL_QUERY + "where id = ?",new RowMapper<Item>(){
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item anItem = new Item();
                anItem.setId(rs.getInt("id"));
                anItem.setName(rs.getString("name"));
                anItem.setDescription(rs.getString("description"));
                anItem.setCategory(rs.getString("category"));
                anItem.setPrice(rs.getDouble("price"));
                anItem.setCount(rs.getInt("count"));
                return anItem;
            }
        },
        id);
        
    }
    
    public User getUserByLogin(String login){
        return jdbc.queryForObject(USER_SEL_QUERY, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User anUser = new User();
                anUser.setId(rs.getInt("id"));
                anUser.setLogin(rs.getString("login"));
                anUser.setPassword(rs.getString("password"));
                anUser.setRole(rs.getString("role"));
                return anUser;
            }
        }, 
        login);
    }
    
    public Order getOrderById(String orderId, String login){
    	private Order anOrder = new Order();
    	
    	return jdbc.query(USER_SEL_QUERY, new  RowCallbackHandler(){
            @Override
            public Order processRow(ResultSet rs) throws SQLException {
            	
                anOrder.setId(rs.getInt("id"));
                anOrder.setUser(rs.getString("login"));
                anOrder.setStatus(rs.getString("password"));
                anOrder.setRole(rs.getString("role"));
                anOrder.addToItemList()
            }
        }, 
        orderId, login);	
    	
    }
    
}
