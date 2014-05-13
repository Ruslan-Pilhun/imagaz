package com.ruslan.magaz.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import com.ruslan.magaz.Item;
import com.ruslan.magaz.User;

public class MerchDao {
    private static final String ITEM_SEL_QUERY = "SELECT id, name, description, category, price, count FROM Merchandise";
    private static final String USER_SEL_QUERY = "SELECT id, login, password, role FROM Users WHERE login = ?";
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
    
}
