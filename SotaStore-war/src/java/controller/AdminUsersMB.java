/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entity.Users;
import beans.UsersFacadeLocal;

/**
 *
 * @author Duyet
 */
@ManagedBean
@SessionScoped
public class AdminUsersMB {
    
    @EJB
    private UsersFacadeLocal usersFacade;
    private Users selectedCus = new Users();
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public Users getSelectedCus() {
        return selectedCus;
    }

    public void setSelectedCus(Users selectedCus) {
        this.selectedCus = selectedCus;
    }
    
    public String displayDetail(int id){
        selectedCus = usersFacade.find(id);
        return "/admin/user/detailCustomer";
    }
    public String displayUpdateStatusCustomer(int id){
        selectedCus = usersFacade.find(id);
        return "/admin/user/updateStatusCustomer";
    }
    public String displayShow(){
        return "/admin/user/viewCustomers";
    }
    public String displayResetPasswordCustomer(int id){
        selectedCus = usersFacade.find(id);
        return "/admin/user/resetPasswordCustomer";
    }
    
    public String resetPasswordCutomer(){
        usersFacade.edit(selectedCus);
        return this.displayShow();
    }
    public String updateStatusCustomer(){
        usersFacade.edit(selectedCus);
        return this.displayShow();
    }
    public List<Users> showAll(){
        return usersFacade.findAll();
    }

    /**
     * Creates a new instance of AdminUsersMB
     */
    public AdminUsersMB() {
    }
    
}
