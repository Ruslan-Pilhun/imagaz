package com.ruslan.magaz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruslan.magaz.web.MerchDao;
public class ItemService {
    
    private MerchDao dao;
    @Autowired
    public ItemService(MerchDao dao) {
        this.dao = dao;
    }

    public List<Item> getItemList(){
        
        List<Item> itemList = dao.getItemList();
        return itemList;
    }
    

}
