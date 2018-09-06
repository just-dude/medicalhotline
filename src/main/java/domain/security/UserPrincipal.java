/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.security;

import domain.userAccounts.UserGroup;

/**
 * @author SuslovAI
 */
public class UserPrincipal {

    private String login;
    private Long userId;
    private UserGroup userGroup;

    public UserPrincipal() {
    }

    public UserPrincipal(String login, Long userId, UserGroup userGroup) {
        this.login = login;
        this.userId = userId;
        this.userGroup = userGroup;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "login='" + login + '\'' +
                ", userId=" + userId +
                ", userGroup=" + userGroup +
                '}';
    }
}
