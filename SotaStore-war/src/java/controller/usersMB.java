/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.ShippingFeeFacadeLocal;
import beans.UsersFacadeLocal;
import entity.ShippingFee;
import entity.Users;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tqkha
 */
@ManagedBean
@SessionScoped
public class usersMB {

    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private ShippingFeeFacadeLocal shippingFeeFacade;
    //Register
    private int districtId;
    private Users registeringGuest = new Users();
    private String rdDistrict = "hochiminh";
    //User
    private Users loginUsers = null;

    //bien tam de lay gia tri cua district cua login member
    private Integer loginDistrictId;
    
    private String path = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLoginDistrictId() {
        return loginDistrictId;
    }

    public void setLoginDistrictId(Integer loginDistrictId) {
        this.loginDistrictId = loginDistrictId;
    }    
    
    public Users getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(Users loginUsers) {
        this.loginUsers = loginUsers;
    }
    //Password
    private String oldPassword, newPassword, confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    //Login
    private String username, password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //getter setter Register  
    public String getRdDistrict() {
        return rdDistrict;
    }

    public void setRdDistrict(String rdDistrict) {
        this.rdDistrict = rdDistrict;
    }

    public Users getRegisteringGuest() {
        return registeringGuest;
    }

    public void setRegisteringGuest(Users registeringGuest) {
        this.registeringGuest = registeringGuest;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /**
     * Creates a new instance of usersMB
     */
    public usersMB() {
    }

    public String gotoLogin() {
        //return to current page
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        path = viewId + "?faces-redirect=true";        
        return "/login?faces-redirect=true";
    }

    public String checkLogin() {
        if (usersFacade.verifyUser(username, password)) {
            //get member
            loginUsers = usersFacade.getUserByUsername(username);
            if(loginUsers.getDistrict() == null){
                loginDistrictId = null;
            }
            else{
                loginDistrictId = loginUsers.getDistrict().getId();
            }            
            //return to current page
            return path;
        } else {
            //remain at login page
            return null;
        }
    }

    public List<ShippingFee> districtList() {
        return shippingFeeFacade.findAll();
    }

    public String addUser() {
        registeringGuest.setStatus("Normal");
        if (rdDistrict.equals("hochiminh")) {
            registeringGuest.setDistrict(shippingFeeFacade.find(districtId));
        } else {
            registeringGuest.setDistrict(null);
        }
        usersFacade.create(registeringGuest);
        System.out.println(rdDistrict);
        System.out.println(districtId);
        return "/login?faces-redirect=true";
    }
    
    public String changeProfile() {
        if (rdDistrict.equals("hochiminh")) {
            System.out.println("This is: " + loginDistrictId.toString());
            loginUsers.setDistrict(shippingFeeFacade.find(loginDistrictId));
        } else {
            loginUsers.setDistrict(null);
        }        
        usersFacade.edit(loginUsers);
        return "/member/profile?faces-redirect=true";
    }
    
    public String changePassword() {
        if (oldPassword.equals(loginUsers.getUserPass()) && confirmPassword.equals(newPassword) && !oldPassword.equals(newPassword)) {
            loginUsers.setUserPass(newPassword);
        } else {
            return "/member/changePassword?faces-redirect=true";
        }
        usersFacade.edit(loginUsers);
        oldPassword = "";
        newPassword = "";
        confirmPassword = "";
        return "/member/profile?faces-redirect=true";
    }
    
    public String gotoProfile() {
        return "/member/profile?faces-redirect=true";
    }

    public String logout() {
        loginUsers = null;
        return "/index?faces-redirect=true";
    }
}
