package com.example.demo.service;

import com.example.demo.dao.LoginUserDao;
import com.example.demo.model.LoginUser;
import com.example.demo.model.ReturnT;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.JacksonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Service
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "DTIMER_DAY_LOGIN_IDENTITY";

    @Resource
    private LoginUserDao loginUserDao;

    private String createToken(LoginUser loginUser) {
        String tokenJson = JacksonUtil.writeValueAsString(loginUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    private LoginUser parseToken(String tokenHex) {
        LoginUser loginUser = null;
        if (tokenHex != null) {
            // username_password(md5)
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());
            loginUser = JacksonUtil.readValue(tokenJson, LoginUser.class);
        }
        return loginUser;
    }


    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember) {

        ReturnT<String> success = ReturnT.SUCCESS;
        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new ReturnT<String>(500, "账号或密码为空账号或密码为空");
        }

        // valid passowrd
        LoginUser loginUser = loginUserDao.loadByUserName(username);
        System.err.println(loginUser.getPassword());
        if (loginUser == null) {
            return new ReturnT<String>(500, "账号或密码为空");
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        System.err.println(passwordMd5);
        if (!passwordMd5.equals(loginUser.getPassword())) {
            return new ReturnT<String>(500, "账号或密码错误");
        }

        String loginToken = createToken(loginUser);
        System.err.println(loginToken);
        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public LoginUser ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            LoginUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                LoginUser dbUser = loginUserDao.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }


}
