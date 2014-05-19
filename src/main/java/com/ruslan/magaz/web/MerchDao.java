package com.ruslan.magaz.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.ruslan.magaz.Item;
import com.ruslan.magaz.Order;
import com.ruslan.magaz.OrderStatus;
import com.ruslan.magaz.User;

public class MerchDao {
    private static final String ITEM_SEL_QUERY = "SELECT id, name, description, category, price, count FROM Merchandise";
    private static final String USER_SEL_QUERY = "SELECT id, login, password, role FROM Users WHERE login = ?";
    private static final String USER_SEL_QUERY_ID = "SELECT id, login, password, role FROM Users WHERE id = ?";
    private static final String ORDER_SEL_QUERY = "select orderdate, userid, status from orders where orderid = ?";
    private static final String ORDERED_SEL_QUERY = "select merchid, count from ordered where orderid = ?";
    private static final String ORDERSTATUS_SEL_QUERY = "select statusdate, statusplace, status from orderstatuses where orderid = ?";
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
    
    public User getUserById(int id){
        return jdbc.queryForObject(USER_SEL_QUERY_ID, new RowMapper<User>(){
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
        id);
    }
    
    public Order getOrderById(final int orderId){
    	final Order anOrder = new Order();
    	
    	jdbc.query(ORDER_SEL_QUERY, new  RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	User anUser = getUserById(rs.getInt("userid"));
                anOrder.setId(orderId);
                anOrder.setUser(anUser);
                anOrder.setOrderDate(rs.getDate("orderdate"));
            }
        }, 
        orderId);
    	
    	jdbc.query(ORDERED_SEL_QUERY, new  RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	Item anItem = getItemById(rs.getInt("merchid"));
            	int count = rs.getInt("count");
                anOrder.addToItemList(anItem, count);
            }
        }, 
        orderId);	
    	
    	jdbc.query(ORDERSTATUS_SEL_QUERY, new  RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
            	OrderStatus status = new OrderStatus(rs.getDate("statusdate"), rs.getString("statusplace"), rs.getString("status"));
                anOrder.addToStatusList(status);
            }
        }, 
        orderId);	
    	
    	return anOrder;
    }
    
}
