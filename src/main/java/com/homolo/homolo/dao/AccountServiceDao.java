package com.homolo.homolo.dao;


import java.util.HashMap;

/**
 * 账号.
 */
public interface AccountServiceDao {

    HashMap loadUserByUsername(String username);
}
