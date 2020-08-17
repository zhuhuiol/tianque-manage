package com.homolo.homolo.entity.system;

import com.homolo.homolo.annotations.TestAutowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author homolo--
 * @DESC
 * @Create 2019-08-23  下午12:18
 */
@Service
public class User implements UserDetails {

	private String userid;

	@TestAutowired
	private String username;

	private String password;

	private String usernick;

	private Date birthday;

	private int age;

	private int sex;

	private String email;

	private int disabled;

	private String mobile;

	private String description;

	private String avatar;

	private String idnunber;

	private String address;

	private int valid;

	private Date create_time;

	private Date update_time;

	private Object details;

	private org.springframework.security.core.userdetails.User user;

	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsernick() {
		return usernick;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
	}

	public Date getBirthday() {
		if (this.birthday != null) {
			return new Date(this.birthday.getTime());
		} else {
			return null;
		}
	}

	public void setBirthday(Date birthday) {
		if (birthday != null) {
			this.birthday = (Date) birthday.clone();
		} else {
			this.birthday = null;
		}
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDisabled() {
		return disabled;
	}
	public boolean getDisabledBool() {
		//0不禁用1禁用
		return disabled == 0;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIdnunber() {
		return idnunber;
	}

	public void setIdnunber(String idnunber) {
		this.idnunber = idnunber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreate_time() {
		if (this.create_time != null) {
			return new Date(this.create_time.getTime());
		} else {
			return null;
		}
	}

	public void setCreate_time(Date create_time) {
		if (create_time != null) {
			this.create_time = (Date) create_time.clone();
		} else {
			this.create_time = null;
		}
	}

	public Date getUpdate_time() {
		if (this.update_time != null) {
			return new Date(this.update_time.getTime());
		} else {
			return null;
		}
	}

	public void setUpdate_time(Date update_time) {
		if (update_time != null) {
			this.update_time = (Date) update_time.clone();
		} else {
			this.update_time = null;
		}
	}

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public org.springframework.security.core.userdetails.User getUser() {
		return user;
	}

	public void setUser(org.springframework.security.core.userdetails.User user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}
}
