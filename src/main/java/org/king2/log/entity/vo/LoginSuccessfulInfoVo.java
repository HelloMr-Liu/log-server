package org.king2.log.entity.vo;

import java.util.List;

/**
 * 描述: 封装用户登录成功信息
 * @author 刘梓江
 * @Date 2021/4/2 14:45
 */
public class LoginSuccessfulInfoVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public LoginSuccessfulInfoVo(Long userId, String userName, String userAccount) {
        this.userId = userId;
        this.userName = userName;
        this.userAccount = userAccount;
    }
}
