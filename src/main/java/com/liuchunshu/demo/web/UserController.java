package com.liuchunshu.demo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.liuchunshu.demo.entity.UserEntity;
import com.liuchunshu.demo.mapper.test1.User1Mapper;
import com.liuchunshu.demo.mapper.test2.User2Mapper;
import com.liuchunshu.demo.util.JwtToken;

@RestController
public class UserController {

	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private User1Mapper user1Mapper;

	@Autowired
	private User2Mapper user2Mapper;
	
	@Value("${JWTToken}")
	private String screat;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView model=new ModelAndView("index");
		logger.info("index request");
		String token=JwtToken.createToken(screat);
		model.addObject("token", token);
		return model;
	}
	
	@RequestMapping("/getUsers")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=user1Mapper.getAll();
		System.out.println("textddd");
		return users;
	}
	
    @RequestMapping(value="/getUser",method=RequestMethod.POST,produces="application/json;charset=utf-8")
    public UserEntity getUser(Long id) {
    	UserEntity user=user2Mapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(UserEntity user) {
        user2Mapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
        user2Mapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        user1Mapper.delete(id);
    }
    
    
}
