package com.altostratus.core.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.core.dao.GenericDaoJpa;
import com.altostratus.core.model.Role;
import com.altostratus.core.model.User;
import com.altostratus.core.util.QueryUtil;

@Service("userManagementService")
@Transactional
public class UserManagementServiceImpl implements UserManagementService, UserDetailsService
{
	private Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	GenericDaoJpa<User, Long> userDao;

	@Autowired
	GenericDaoJpa<Role, Long> roleDao;

	@Override
	public User getUserByUsername(String username) {
		List<User> users = userDao.findByQuery("SELECT u FROM User u WHERE u.username=:username",
				QueryUtil.toMap("username", username));
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
        	User u = users.get(0);
        	for(Role r : u.getRoles()) {
        		logger.info("ROLE: " + r.getAuthority());
        	}
            return users.get(0);
        }
	}

	@Override
	public User getUser(Long id) {
		return (User) userDao.get(id);
	}

	@Override
	public User saveUser(User user) {

		// Get and prepare password management-related artifacts
		boolean passwordChanged = false;
		if (passwordEncoder != null) {
			user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
		}

		try {
			return (User) userDao.save(user);
		} catch (DataIntegrityViolationException e) {
			//e.printStackTrace();
			return null;
		} catch (JpaSystemException e) { // needed for JPA
			//e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean removeUser(Long id) {
		try {
			userDao.remove(id);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<User> getUsersWithRole(Role role) {
		List<User> users = userDao.findByQuery("SELECT u FROM User u WHERE u.role=:role",
				QueryUtil.toMap("role", role));
        if (users == null || users.isEmpty()) {
        	// TODO: Error handling here
        	return null;
        } else {
            return users;
        }
	}
	//Varian added
	@Override
	public List<User> getUsersWithOneRole(Role role) {
        List<User> users2 = userDao.getAll();
        List<User> users = new ArrayList<User>();
        for(User u : users2){
        	if(u.getRoles().get(0).equals(role)){
        		users.add(u);
        	}
        }
        return users;
	}

	@Override
	public Role getRoleByName(String name) {
		logger.info("Getting role by name " + name);
		List<Role> roles = roleDao.findByQuery("SELECT r FROM Role r WHERE r.name=:name",
				QueryUtil.toMap("name", name));
		if (roles == null || roles.isEmpty()) {
        	// TODO: Error handling here
        	return null;
        } else {
            return roles.get(0);
        }
	}

	@Override
	public Role getRole(Long id) {
		return (Role) roleDao.get(id);
	}

	@Override
	public Role saveRole(Role role) {
		return (Role) roleDao.save(role);
	}

	@Override
	public boolean removeRole(Long id) {
		try {
			roleDao.remove(id);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Role> getUserRoles(User user) {
		return user.getRoles();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails user = getUserByUsername(username);
		logger.info("USER LOGGING IN: " + user.getUsername());
		logger.info("PASSWORD: " + user.getPassword());
		for(GrantedAuthority g : user.getAuthorities()) {
			logger.info("ROLE: " + g.getAuthority());
		}
		return user;
	}

	@Override
	public String getUserPassword(String username) {
		List<User> users = userDao.findByQuery("SELECT u FROM User u WHERE u.username=:username",
				QueryUtil.toMap("username", username));
		if(users == null || users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + username + "' not found...");
		} else {
			return users.get(0).getPassword();
		}
	}

	@Override
	public boolean exists(Long id) {
		return userDao.exists(id);
	}

	@Override
	public List<User> getBasicUsers() {
		List<User> users = userDao.findByQuery("SELECT u FROM User u WHERE u.userType=:userType",
				QueryUtil.toMap("userType", "USER"));
		logger.info("Size of basic users: " + users.size());
		List<User> returnedUsers = new ArrayList<User>();
		if(users.size() > 0) {
			for(User user : users) {
				user.setRole(user.getRoles().get(0));
				returnedUsers.add(user);
			}

			logger.info("Returning users");
			return returnedUsers;
		} else {

			logger.info("Returning null");
			return null;
		}
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAll();
	}
}
