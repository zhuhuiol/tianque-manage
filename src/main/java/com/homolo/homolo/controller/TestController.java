package com.homolo.homolo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/testHttp", method = RequestMethod.GET)
    @ResponseBody
    public Object testGet(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "get");
        map.put("h", request.getHeader("h"));
        map.put("data", request.getParameter("data"));
        System.out.println("request api : " + request.getParameter("data"));
        return map;
    }

    @RequestMapping(value = "/testHttp", method = RequestMethod.POST)
    @ResponseBody
    public Object testPost(HttpServletRequest request, @RequestBody String data) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "post");
        map.put("h", request.getHeader("h"));
        map.put("data", request.getParameter("data"));
        map.put("data2", data);
        System.out.println("request api : " + request.getParameter("data"));
        return map;
    }

    @RequestMapping(value = "/testHttp", method = RequestMethod.PUT)
    @ResponseBody
    public Object testPut(HttpServletRequest request, @RequestBody String data) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "put");
        map.put("h", request.getHeader("h"));
        map.put("data", data);
        System.out.println("request api : " + request.getParameter("data"));
        return map;
    }

    @RequestMapping(value = "/testHttp", method = RequestMethod.DELETE)
    @ResponseBody
    public Object testDelete(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "delete");
        map.put("h", request.getHeader("h"));
        map.put("data", request.getParameter("data"));
        System.out.println("request api : " + request.getParameter("data"));
        return map;
    }
}
