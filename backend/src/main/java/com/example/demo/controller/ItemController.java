package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ItemRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Item;
import com.example.demo.model.User;

@RestController
//@CrossOrigin(origins = "http://localhost:8181")
@RequestMapping(path="/item")
public class ItemController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	/*
	 * add new item in databse by given userid,title,desc and price
	 */
	@RequestMapping(path="/post")
	public	@ResponseBody Map<String, String>  addItem (@RequestParam Integer userid,
									@RequestParam String title,@RequestParam String desc, @RequestParam double price) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("addnewitem: "+"title"+title+" desc-"+desc+" price-"+price);
		Item item = new Item();
		item.setUserid(userid);
		item.setTitle(title);
		item.setDescription(desc);
		item.setPrice(price);
		try{
			//update database
			itemRepository.save(item);
			response.put("code", "1");
			response.put("message", "item added!");
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "item added fail!");
		}
		return response;
	}

	/*
	 * add new item in databse by given userid,title,desc and price
	 */
	@RequestMapping(path="/purchaseitem")
	public	@ResponseBody Map<String, String>  purchaseitem (@RequestParam Integer userid,
														  @RequestParam Integer itemid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("purchaseitem: "+"userid"+userid+"itemid"+itemid);
		User user=new User();
		user=userRepository.findById(userid);
		if(user!=null) {
			Item item = new Item();
			item=itemRepository.findById(itemid);
			if(item!=null||item.getIsSold()==1){
				double moneyleft=user.getWallet()-item.getPrice();
				if(moneyleft<0){
					response.put("code", "0");
					response.put("message", "user does not have enough money!");
					return response;
				}
				user.setWallet(moneyleft);
				item.setIsSold(1);
				item.setBuyerid(userid);
				User seller=userRepository.findById(item.getUserid());
				seller.setWallet(seller.getWallet()+item.getPrice());
				//update
				itemRepository.save(item);
				userRepository.save(user);
				userRepository.save(seller);
				response.put("code", "1");
				response.put("message", "purchase success!");
			}else{
				response.put("code", "0");
				response.put("message", "invalid item!");
			}

		}else{
			response.put("code", "0");
			response.put("message", "invalid user!");
		}
		return response;
	}


	/*
	 * delete item in database by given itemid
	 */
	@RequestMapping(path="/delete")
	public	@ResponseBody Map<String, String>  deleteItem (@RequestParam Integer id) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("deleteitem: "+"id"+id);
		Item item = new  Item();
		//find item by id in database
		item=itemRepository.findById(id);
		if(item != null) {
			try{
				//move item in database
				itemRepository.delete(item);
				response.put("code", "1");
				response.put("message", "delete success!");
			}catch(Exception e) {			
				response.put("code", "0");
				response.put("message", "delete fail!");
			}			
		}
		else {
			response.put("code", "0");
			response.put("message", "item not found!");
			
		}

		return response;
	}
	
	//update item in database by given itemid, title, desc and price
	@RequestMapping(path="/edit")
	public	@ResponseBody Map<String, String>  edititem (@RequestParam(value="id", required = true) Integer id,
														   @RequestParam(value="title", required = false) String title,
														   @RequestParam(value="desc", required = false) String desc,
														   @RequestParam(value="price", required = true, defaultValue="0") double price,
														   @RequestParam(value="comments", required = false) String comments,
														   @RequestParam(value="issold", required = false) Integer issold) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("edititem: "+"id"+id);
		Item item = new  Item();
		item=itemRepository.findById(id);
		if(item != null) {
			if(title!=null)	{
				item.setTitle(title);
			}
			if(desc!=null) {
				item.setDescription(desc);
			}
			try{
				item.setPrice(price);
			}catch(Exception e) {
				response.put("code", "0");
				response.put("message", "edit item fail");
			}
			if(comments!=null) {
				item.setComments(comments);
			}
			if(issold!=null) {
				item.setIsSold(issold);
			}
			try {
				//update item in database
				itemRepository.save(item);
				response.put("code", "1");
				response.put("message", "edited item success!");
			}catch(Exception e) {
				
			}
		}
		else {
			response.put("code", "0");
			response.put("message", "item not found!");
		}
		return response;
	}
	
	/*
	 * get item info from database by given item id
	 */
	@RequestMapping(path="/getitem")
	public	@ResponseBody Object  getitem (@RequestParam Integer id) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getitem: "+"id"+id);
		Item item = new  Item();
		//find item by id in database
		item=itemRepository.findById(id);
		if(item != null) {
			return item;
		}
		else {
			response.put("code", "0");
			response.put("message", "item not found!");		
		}
		return response;
	}



	/*
	 * get all items from database
	 */
	@RequestMapping(path="/getallitems")
	public	@ResponseBody Object getallitems () {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getallitems");
		List<Item> list = new ArrayList<Item>();
		//list all items from database
		list=(List<Item>) itemRepository.findByIsSold(0);
		if(list.size()>0) {
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "Does not have any items!");
			return response;
		}
	}

	/*
	 * get all orders from database
	 */
	@RequestMapping(path="/getorder")
	public	@ResponseBody Object getorder (@RequestParam Integer buyerid) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getorder: buyerid "+buyerid);
		List<Item> list = new ArrayList<Item>();
		//list all items from database
		list=(List<Item>) itemRepository.findByIsSold(1);
		if(list.size()>0) {
			for(int i=0;i<list.size();i++){
				if(list.get(i).getBuyerid()!=buyerid){
					list.remove(i);
				}
			}
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "Does not have any orders!");
			return response;
		}
	}

	/*
	 * get all post from database
	 */
	@RequestMapping(path="/getpost")
	public	@ResponseBody Object getpost (@RequestParam Integer userid) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("get post : userid "+userid);
		List<Item> list = new ArrayList<Item>();
		//list all items from database
		list=(List<Item>) itemRepository.findByUserid(userid);
		if(list.size()>0) {
			for(int i=0;i<list.size();i++){
				if(list.get(i).getIsSold()==1){
					list.remove(i);
				}
			}
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "Does not have any post!");
			return response;
		}
	}

	/*
	 * return item
	 */
	@RequestMapping(path="/return")
	public	@ResponseBody Object returnitem (@RequestParam Integer buyerid, @RequestParam Integer itemid) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("return item: "+itemid);
		User buyer=userRepository.findById(buyerid);
		Item item=itemRepository.findById(itemid);
		User seller=userRepository.findById(item.getUserid());
		seller.setWallet(seller.getWallet()-item.getPrice());
		buyer.setWallet(buyer.getWallet()+item.getPrice());
		item.setIsSold(0);
		item.setBuyerid(-1);
		//update
		userRepository.save(seller);
		userRepository.save(buyer);
		itemRepository.save(item);
		response.put("code", "1");
		response.put("message", "return success");
		return response;
	}

	/*
	 * search item title and desc from database by given key
	 * return items
	 */
	@RequestMapping(path="/search")
	public	@ResponseBody Object searchitem(@RequestParam(required = true) String key) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("searchitem: "+key);
		//key exist
		if(key == null) {
			response.put("message", "searchquery cannot be empty");	
		}
		else {
			key = "%"+key+"%";
			List<Item> list = new ArrayList<Item>();
			list = (List<Item>) itemRepository.findAllByTitleLike(key);
			if(list.size()>0) {
				return list;
			}
			else {
				response.put("code", "0");
				response.put("message", "No items found!");
				return response;
			}
			
		}
		return response;
	}

}
