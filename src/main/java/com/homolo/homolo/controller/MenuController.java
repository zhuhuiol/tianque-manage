package com.homolo.homolo.controller;

import com.homolo.homolo.constants.ReturnCode;
import com.homolo.homolo.entity.system.Menu;
import com.homolo.homolo.result.ServiceResult;
import com.homolo.homolo.service.MenuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @RequestMapping(value = "/searchMenu")
    public Object searchMenu() {
        List<Menu> menus = this.menuService.searchAll();
        //得到所有菜单
        JSONArray child = getChild(menus, 0);
        return ServiceResult.serviceResult(ReturnCode.SUCCESS, child.toList());
    }


    @RequestMapping(value = "/createMenu")
    public Object createMenu(){

        return "ok";
    }

    @RequestMapping(value = "/removeMenu")
    public Object removeMenu() {

        return "ok";
    }

    /**
     * 获取所有菜单，并且按照层级装填.
     * @param menus 所有菜单集合。
     * @param parentId 父id
     * @return 返回构造好层级的集合.
     */
    public JSONArray getChild(List<Menu> menus, Integer parentId) {
        JSONArray jsonArray = new JSONArray();
        menus.forEach( m -> {
            if (m.getParentId() == parentId) {
                JSONObject jsonObject = new JSONObject();
                JSONArray child = getChild(menus, m.getMenuId());
                jsonObject.put("menu", m);
                jsonObject.put("child", child);
                jsonArray.put(jsonObject);
            }
        });
        return jsonArray;
    }

}
