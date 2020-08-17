package com.homolo.homolo.dao;

import com.homolo.homolo.entity.system.Menu;

import java.util.List;

/**
 * 菜单.
 */
public interface MenuServiceDao {

    /**
     * 查询所有.
     * @return all
     */
    List<Menu> searchAll();

    /**
     * 查询根据id.
     * @param id id.
     * @return menu.
     */
    Menu searchById(Integer id);

    /**
     * 查询根据id集合.
     * @param ids ids
     * @return menus
     */
    List<Menu> searchByIds(List<Integer> ids);
}
