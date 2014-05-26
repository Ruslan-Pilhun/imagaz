package com.ruslan.magaz.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.ruslan.magaz.Cart;
import com.ruslan.magaz.Item;
import com.ruslan.magaz.ItemTuple;
import com.ruslan.magaz.Order;
import com.ruslan.magaz.OrderStatus;
import com.ruslan.magaz.User;

public class MerchDao {
    private static final String ITEM_SEL_QUERY = "SELECT id, name, description, category, price, count FROM Merchandise";
    private static final String USER_SEL_QUERY = "SELECT id, login, password, role FROM Users WHERE login = ?";
    private static final String USER_SEL_QUERY_ID = "SELECT id, login, password, role FROM Users WHERE id = ?";
    private static final String ORDER_SEL_QUERY = "select orderdate, userid from orders where id = ?";
    private static final String ORDERED_SEL_QUERY = "select merchid, count from ordered where orderid = ?";
    private static final String ORDERSTATUS_SEL_QUERY = "select statusdate, statusplace, status from orderstatuses where orderid = ?";
    private static final String ORDERID_CHECK = "select ? in (select orders.id from orders join users on orders.userid = users.id where users.login = ?)";
    private static final String GET_MAX_ORDERID = "select id from orders order by id desc limit 1";
    private static final String INSERT_ORDER = "INSERT INTO orders (id, userid, orderdate) values (?, ?, ?)";
    private static final String INSERT_ORDERSTATUS = "INSERT INTO orderstatuses (orderid, statusdate, statusplace, status) values (?, ?, ?, ?)";
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
                anItem.setStock(rs.getInt("count"));
                return anItem;
            }
        });
        
    }
    
    public Item getItemById(int id){
        return jdbc.queryForObject(ITEM_SEL_QUERY + " where id = ?",new RowMapper<Item>(){
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item anItem = new Item();
                anItem.setId(rs.getInt("id"));
                anItem.setName(rs.getString("name"));
                anItem.setDescription(rs.getString("description"));
                anItem.setCategory(rs.getString("category"));
                anItem.setPrice(rs.getDouble("price"));
                anItem.setStock(rs.getInt("count"));
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
    public Boolean checkOrderId (int orderId, String login){
    	return jdbc.queryForObject(ORDERID_CHECK, Integer.class, orderId, login) != 0;
    }
    
    public Order getOrderById(final int orderId){
    	final Order anOrder = new Order();
    	final Cart aCart = new Cart();
    	
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
                aCart.addToItemList(anItem, count);
            }
        }, 
        orderId);	
    	anOrder.setCart(aCart);
    	
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
    public int confirmOrder(Cart aCart, User anUser){
    	int cartSize = aCart.getItemList().size();
    	if (cartSize > 0){
	    	
	    	Integer maxId =  jdbc.queryForObject(GET_MAX_ORDERID, Integer.class);
	    	int orderId = 1;
	    	if (maxId != null){
	    		orderId =  maxId + 1;
	    	}
	    	jdbc.update(INSERT_ORDER, orderId, anUser.getId(), new Date());
	    	String insertOrdered = "INSERT INTO ordered (orderid, merchid, count) values ";
	    	List argList = new ArrayList();
	    	
	    	for (ItemTuple tuple : aCart.getItemList()){
	    		insertOrdered+=("(?, ?, ?),");
	    		argList.add(orderId);
	    		argList.add(tuple.item.getId());
	    		argList.add(tuple.count);
	    	}
	
	    	insertOrdered = insertOrdered.substring(0, insertOrdered.length()-1);
	    	Object[] argArray = argList.toArray();
	    	jdbc.update(insertOrdered, argArray);
	    	
	    	jdbc.update(INSERT_ORDERSTATUS, orderId, new Date(), "Kyiv", "processing");
	    	return orderId;
    	}
    	else return -1;
    }
    
}
