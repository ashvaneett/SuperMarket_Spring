package com.market.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.market.demo.exception.ResourceNotFoundException;
import com.market.demo.model.Item;
import com.market.demo.repository.ItemRepository;

public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllBooks(){
		List<Item> items=this.itemRepository.findAll();
		if(items.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(items));
	}
	
	
	//get employee by id
	
	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable(value="id") Integer id) throws ResourceNotFoundException {
		Item item=itemRepository.findById(id)
				.orElseThrow(() ->new  ResourceNotFoundException("Item not found for this id:: "+ id));
		
		return ResponseEntity.ok().body(item);
	}
	
	
	
	//save employee by insert in table 
	
	@PostMapping("/items")
	public Item createBook(@RequestBody Item item) {
		return this.itemRepository.save(item);
		
	}
	@PostMapping("/items/")
	public List<Item> createBooks(@RequestBody List<Item> list) {
		return this.itemRepository.saveAll(list);
	}
	
	
	//update employee
	
	@PutMapping("/items/{id}")
	public ResponseEntity<Item> updateEmployee(@PathVariable(value="id") Integer id,
			@Validated @RequestBody Item item) throws ResourceNotFoundException{
		
		Item item2=itemRepository.findById(id)
				.orElseThrow(() ->new  ResourceNotFoundException("Item not found for this id:: "+ id));
	item2.setCatagory(item.getCatagory());
	item2.setDate(item.getDate());
	item2.setId(item.getId());
	item2.setName(item.getName());
	item2.setQuantity(item.getQuantity());
	
	return ResponseEntity.ok(this.itemRepository.save(item2));
	
	}
	//delete employee
	
	@DeleteMapping("items/{id}")
	public Map<String,Boolean> deleteItem(@PathVariable(value="id") Integer id) throws ResourceNotFoundException{
		Item book=itemRepository.findById(id)
				.orElseThrow(() ->new  ResourceNotFoundException("Item not found for this id:: "+ id));
		this.itemRepository.delete(book);
		
		Map<String, Boolean> response=new HashMap<>();
		
		response.put("deleted", Boolean.TRUE);
		return response;
	}
//	@GetMapping("books/item/{name}")
//	public List<Item> getBookByAuthorName(@PathVariable(value = "name") String name) {
//		List<Item> book=(List<Item>) itemRepository.findBookByAuthor(author);
//		for(Book book2:book) {
//			System.out.println(book2);
//		}
//	
//		return book;
//	}
		


}
