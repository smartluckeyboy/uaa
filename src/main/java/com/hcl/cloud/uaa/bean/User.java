package com.hcl.cloud.uaa.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name = "userId")
	private String userName;
	@Column(name = "email" , unique=true)
	private String email;
	private String first_name;
	private String last_name;
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Address> user_address =  new HashSet<>();
	private String password;
	private long phone_number;
	//private boolean active_user = true;
	private String role;
	private Integer active=1; 
	private boolean isLoacked=false;   
	private boolean isExpired=false;  
	private boolean isEnabled=true;

	/**
	 * Default constructor
	 */
	public User() {
	}

	
	/**
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}


	/**
	 *
	 * @param id the id to set
	 */
	
	public void setId(long id) {
		this.id = id;
	}

	
	/**
	 *
	 * @return the user_address
	 */
	public Set<Address> getUser_address() {
		return user_address;
	}


	/**
	 *
	 * @param user_address the user_address to set
	 */
	public void setUser_address(Set<Address> user_address) {
		this.user_address = user_address;
	}

	/**
	 * 
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 *
	 * @param role the role to set
	 */
	@Column(name = "role")
	public void setRole(String role) {
		this.role = role;
	}

	
	/**
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 *
	 * @param userName the userName to set
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 *
	 * @param email
	 *            the email to set
	 */
	@Column(name = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 *
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 *
	 * @param first_name
	 *            the first_name to set
	 */
	@Column(name = "first_name")
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 *
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 *
	 * @param last_name
	 *            the last_name to set
	 */
	@Column(name = "lat_name")
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @param password
	 *            the password to set
	 */
	@Column(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 *
	 * @return the phone_number
	 */
	public long getPhone_number() {
		return phone_number;
	}

	/**
	 *
	 * @param phone_number
	 *            the phone_number to set
	 */
	@Column(name = "phone")
	public void setPhone_number(long phone_number) {
		this.phone_number = phone_number;
	}


	/**
	 *
	 * @return the active
	 */
	public Integer getActive() {
		return active;
	}

	/**
	 *
	 * @param id
	 * @param userName
	 * @param email
	 * @param first_name
	 * @param last_name
	 * @param user_address
	 * @param password
	 * @param phone_number
	 * @param role
	 * @param active
	 * @param isLoacked
	 * @param isExpired
	 * @param isEnabled
	 */
	public User(long id, String userName, String email, String first_name, String last_name, Set<Address> user_address,
			String password, long phone_number, String role, Integer active, boolean isLoacked, boolean isExpired,
			boolean isEnabled) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_address = user_address;
		this.password = password;
		this.phone_number = phone_number;
		this.role = role;
		this.active = active;
		this.isLoacked = isLoacked;
		this.isExpired = isExpired;
		this.isEnabled = isEnabled;
	}

	
	/**
	 *
	 * @param active the active to set
	 */
	public void setActive(Integer active) {
		this.active = active;
	}

	/**
	 *
	 * @return the isLoacked
	 */
	public boolean isLoacked() {
		return isLoacked;
	}

	/**
	 *
	 * @param isLoacked the isLoacked to set
	 */
	public void setLoacked(boolean isLoacked) {
		this.isLoacked = isLoacked;
	}

	/**
	 *
	 * @return the isExpired
	 */
	public boolean isExpired() {
		return isExpired;
	}

	/**
	 *
	 * @param isExpired the isExpired to set
	 */
	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	/**
	 *
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}


	/**
	 *
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}