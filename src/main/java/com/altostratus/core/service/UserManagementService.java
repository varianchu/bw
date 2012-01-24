package com.altostratus.core.service;

import java.util.List;

import com.altostratus.core.model.Role;
import com.altostratus.core.model.User;

public interface UserManagementService
{
	public User getUserByUsername(String username);
	public User getUser(Long id);
	public User saveUser(User user);
	public boolean removeUser(Long id);
	public List<User> getUsersWithRole(Role role);
	public String getUserPassword(String username);
	public boolean exists(Long id);
	public List<User> getUsersWithOneRole(Role role);
	public List<User> getBasicUsers();

	public Role getRoleByName(String name);
	public Role getRole(Long id);
	public Role saveRole(Role role);
	public boolean removeRole(Long id);
	public List<Role> getUserRoles(User user);
	public List<Role> getAllRoles();
}
