package com.example.demo.controller;

import com.example.demo.model.ReturnT;
import com.example.demo.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {


    @Resource
    private LoginService loginService;

    @RequestMapping("/")
    public String index(Model model) {

//        Map<String, Object> dashboardMap = xxlJobService.dashboardInfo();
//        model.addAllAttributes(dashboardMap);
        System.err.println("===================");
        return "index";
    }

    @RequestMapping("/toLogin")
//    @PermissionLimit(limit = false)
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        if (loginService.ifLogin(request, response) != null) {
            modelAndView.setView(new RedirectView("/", true, false));
            return modelAndView;
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
//    @PermissionLimit(limit = false)
    public ReturnT<String> loginDo(HttpServletRequest request, HttpServletResponse response, String userName, String password, String ifRemember) {
        System.err.println("----------------------------");
        boolean ifRem = (ifRemember != null && ifRemember.trim().length() > 0 && "on".equals(ifRemember)) ? true : false;
        return loginService.login(request, response, userName, password, ifRem);
    }

}
