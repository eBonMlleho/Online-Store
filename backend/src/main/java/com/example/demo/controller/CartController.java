package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Cart;
import com.example.demo.model.Item;
import com.example.demo.model.User;

@Controller	
@RequestMapping(path="/cart")
public class CartController {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * add item to cart in database
	 */
	@RequestMapping(path="/additemtocart")
	public	@ResponseBody Map<String, String>  additemtocart (@RequestParam Integer userid, @RequestParam Integer itemid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("create new cart:");
		Cart cart = new Cart();
		Item item=new Item();
		if(userid==null){
			response.put("code", "0");
			response.put("message", "userid is null");
			return response;
		}
		if(itemid==null){
			response.put("code", "0");
			response.put("message", "itemid is null");
			return response;
		}
		itemRepository.findById(itemid);
		cart=cartRepository.findByItemid(itemid);
		if(item!=null){
			if(cart!=null&&cart.getUserid()==userid){
				response.put("code", "0");
				response.put("message", "item already in cart");
				return response;
			}
			if(cart!=null){
				response.put("code", "0");
				response.put("message", "item already in other cart");
				return response;
			}
			cart=new Cart();
			cart.setUserid(userid);
			cart.setItemid(itemid);
		}else{
			response.put("code", "0");
			response.put("message", "item not exist!");
			return response;
		}
		//update database
		cartRepository.save(cart);
		response.put("code", "1");
		response.put("message", "additem success");
		return response;
	}

	/*
	 * remove item from cart
	 */
	@RequestMapping(path="/removeitemfromcart")
	public	@ResponseBody Map<String, String>  removeitemfromcart (@RequestParam Integer userid, @RequestParam Integer itemid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("remove item from cart: "+itemid);
		List<Cart> carts = cartRepository.findByUserid(userid);
		if(carts != null) {
			for(int i=0;i<carts.size();i++){
				if(carts.get(i).getItemid()==itemid){
					cartRepository.delete(carts.get(i));
					break;
				}
			}
			response.put("code", "1");
			response.put("message", "remove item success!");
		}
		else {
			response.put("code", "0");
			response.put("message", "cart not exist!");
		}
		return response;
	}

	/*
	 * getall item from cart
	 */
	@RequestMapping(path="/getcartitems")
	public	@ResponseBody Map<String, Object>  getall (@RequestParam Integer userid) {
		Map<String, Object>  response= new HashMap<>();
		ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		System.out.println("get all item in cart: userid"+userid);
		List<Cart> carts = cartRepository.findByUserid(userid);
		if(carts != null) {
			for(int i=0;i<carts.size();i++){
				Item item=itemRepository.findById(carts.get(i).getItemid());
				Map<String, Object>  response1= new HashMap<>();
				response1.put("item",item);
				response1.put("cart",carts.get(i));
				list.add(response1);
			}
			response.put("code", "1");
			response.put("message", "get all items!");
			response.put("carts",list);
		}
		else {
			response.put("code", "0");
			response.put("message", "no item in cart!");
		}
		return response;
	}

	/*
	 * pay for item by cart given buyerid and itemid
	 * if success calculate money for buyer's and seller's wallet
	 */
	@RequestMapping(path="/paybycart")
	public @ResponseBody Map<String, String> paybycart(@RequestParam Integer buyerid) {
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerid + "want to pay by cart");
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerid);
		if (buyer == null) {
			response.put("code", "0");
			response.put("message", "user not exist!");
			return response;
		}
		//find item by id in database
		List<Cart> carts = cartRepository.findByUserid(buyerid);
		//find seller by id in database
		if (carts == null) {
			response.put("code", "0");
			response.put("message", "no item to pay!");
			return response;
		}
		double totalprice=0;
		ArrayList<Cart> list=(ArrayList<Cart>)carts;
		for(int i=0;i<list.size();i++){
			Item item=itemRepository.findById(list.get(i).getItemid());
			totalprice=totalprice+item.getPrice();
		}
		if(totalprice>buyer.getWallet()){
			response.put("code", "0");
			response.put("message", "don't have enough money to pay!");
			return response;
		}
		for(int i=0;i<list.size();i++){
			Item item=itemRepository.findById(list.get(i).getItemid());
			User seller=userRepository.findById(item.getUserid());
			seller.setWallet(seller.getWallet()+item.getPrice());
			item.setIsSold(1);
			item.setBuyerid(buyerid);
			userRepository.save(seller);
			itemRepository.save(item);
			cartRepository.delete(list.get(i));
		}
		buyer.setWallet(buyer.getWallet()-totalprice);
		//update database
		userRepository.save(buyer);
		response.put("code","1");
		response.put("message", "pay success!");
		return response;
	}
}
