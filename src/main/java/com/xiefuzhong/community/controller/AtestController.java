package com.xiefuzhong.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 10:46
 */

@Controller
@RequestMapping("/test")
public class AtestController {

    @RequestMapping("/a")
    @ResponseBody
    public  String say(){
        return  "hello oo";
    }

    @RequestMapping("/http")
    public  void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getParameter("code"));
        System.out.println(request.getServletPath());
        //响应头
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter writer = response.getWriter()){
            writer.write("<h1>测试http</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  /students?current=1 & limit =20
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public  String getStudents(@RequestParam(value = "current",required = false,defaultValue = "1") int current,int limit){
        return  "some students";
    }

    //  /student/26
    @RequestMapping(path = "/students/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  String getStudent(@PathVariable("id") int id){
        return  "a student";
    }

    //测试提交表单
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return  "success";
    }

    //响应html数据
    @RequestMapping(path = "/teach",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",20);
        mav.setViewName("/demo/view");
        return  mav;

    }
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","背景大学");
        model.addAttribute("age",25);
        return  "/demo/view";
    }

    //响应json数据（异步请求）
    // java对象 --》json字符串 --》 js对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","lisi");
        emp.put("age",23);
        emp.put("salary",8000.2);
        return  emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> emp = new HashMap<>();
        emp.put("name","lisi");
        emp.put("age",23);
        emp.put("salary",8000.2);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","lisi");
        emp.put("age",24);
        emp.put("salary",8000.2);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","lisi");
        emp.put("age",25);
        emp.put("salary",8000.2);
        list.add(emp);


        return  list;
    }
}
