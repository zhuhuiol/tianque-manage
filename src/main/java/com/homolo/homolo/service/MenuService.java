package com.homolo.homolo.service;

import com.homolo.homolo.entity.system.Menu;

import java.util.HashMap;
import java.util.List;

public interface MenuService {

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
     * 查询根据父id.
     * @param parentId
     * @return
     */
    List<Menu> searchByParentId(Integer parentId);

    /**
     * 查询根据id集合.
     * @param ids ids
     * @return menus
     */
    List<Menu> searchByIds(List<Integer> ids);

    /**
     * 创建一个菜单.
     * @param menu
     * @return
     */
    void createMenu(Menu menu);

    /**
     * 更新菜单.
     * @param params
     */
    void updateMenu(HashMap params);

    /**
     * 删除菜单根据id.
     * @param id
     */
    void removeMenu(Integer id);
}
