package com.market.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.demo.model.Item;

public interface ItemRepository  extends JpaRepository<Item, Integer>{
	
	public Item findItemById(int id);
	public Item findItemByName(String name);
	

}
