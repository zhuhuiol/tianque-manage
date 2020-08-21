package com.homolo.homolo.service.impl;

import com.homolo.homolo.dao.MenuDao;
import com.homolo.homolo.entity.system.Menu;
import com.homolo.homolo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Component
public class MenuServiceImpl implements MenuService
{

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> searchAll() {
        return this.menuDao.searchAll();
    }

    @Override
    public Menu searchById(Integer id) {
        return this.menuDao.searchById(id);
    }

    @Override
    public List<Menu> searchByParentId(Integer parentId) {
        return this.menuDao.searchByParentId(parentId);
    }

    @Override
    public List<Menu> searchByIds(List<Integer> ids) {
        return this.menuDao.searchByIds(ids);
    }

    @Override
    @Transactional
    public void createMenu(Menu menu) {
        this.menuDao.createMenu(menu);
    }

    @Transactional
    @Override
    public void updateMenu(HashMap params) {
        this.menuDao.updateMenu(params);
    }

    @Transactional
    @Override
    public void removeMenu(Integer id) {
        this.menuDao.removeMenu(id);
    }
}
