package com.us.example.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.us.example.dao.UserJpaDao;
import com.us.example.bean.User;
import com.us.example.service.UserService;

/**
 * 
 * @ClassName UserServiceImpl
 * @author abel
 * @date 2016年11月10日
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserJpaDao userJpaDao;
	/**
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public User getUserByName(String username) {
		return userJpaDao.findByName(username);
	}
	@PostConstruct
	public void init(){
		
	for(int i=0;i<50;i++){
		User user = new User();
		user.setUsername(randomName());
		user.setName(randomName());
		user.setGender(new Random().nextBoolean() ==true?"1":"0");
		user.setTelephone(randomName());
	//	userJpaDao.save(user);
	}
	
	}
	
	public String randomName(){
		StringBuffer  sb = new StringBuffer();
		char[] lib = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		Random rs = new Random();
		
		for(int a = 0;a<6;a++){
			sb.append(lib[rs.nextInt(26)]);
		}
		
		return sb.toString();
		
	}
	
	@Override
	public Page<User> findAll(User user,String page,String size){
		Page<User> result = userJpaDao.findAll(new Specification<User>() {
	        @Override
	        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	            List<Predicate> list = new ArrayList<Predicate>();

	            if (!StringUtils.isEmpty(user.getName())) {
	                list.add(cb.like(root.get("name").as(String.class), "%" + user.getName() + "%"));
	         /*   	 In<String> in = cb.in(root.get("name").as(String.class));
	                List<String> list1 = new ArrayList<String>();
	                list1.add("qfbkmc");
	                list1.add("wgmcai");
	                in.value("qfbkmc");
	                in.value("wgmcai");
	             //   list.add(root.get("name").as(String.class).in(list1));
	                list.add(root.get("name").in(list1));
	             //   list.add(in);
*/            }

	            if (user.getGender() != null) {
	                list.add(cb.like(root.get("gender").as(String.class),"%" + user.getGender()+ "%"));
	            }

	            if (!StringUtils.isEmpty(user.getUsername())) {
	                list.add(cb.like(root.get("username").as(String.class), "%" + user.getUsername() + "%"));
	            }

	            if (user.getTelephone() != null ) {
	                list.add(cb.like(root.get("telephone").as(String.class),"%" + user.getTelephone()+"%" ));
	            }

	            Predicate[] p = new Predicate[list.size()];
	            query.orderBy(cb.asc(root.get("name")));
	          return query.where(list.toArray(p)).getRestriction();
	           

	         //   return cb.and(list.toArray(p));
	        }

		

	    },new PageRequest(Integer.parseInt(page)-1, Integer.parseInt(size)));
		
		//System.out.println(result.size());
		System.out.println(result.getContent());
		
		System.out.println(result.getTotalElements());
		
		return result;
	}
}
