/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import entity.Admin;
import beans.AdminFacadeLocal;
import entity.AdminGroup;
import beans.AdminGroupFacadeLocal;

/**
 *
 * @author Lenovo
 */
@ManagedBean
@SessionScoped
public class adminMB {
    @EJB
    private AdminGroupFacadeLocal adminGroupFacade;

    @EJB
    private AdminFacadeLocal adminFacade;

    /**
     * Creates a new instance of adminMB
     */
    private Admin selectedAd = new Admin(), editedAd = new Admin();
    private int adGroupId; 
    private List<AdminGroup> listAdGr;
    private Admin ad = new Admin();
    private String username, password;
    private List<Admin> list = new ArrayList<Admin>();
    private AdminGroup selectedGr = new AdminGroup();

    public AdminGroup getSelectedGr() {
        return selectedGr;
    }

    public void setSelectedGr(AdminGroup selectedGr) {
        this.selectedGr = selectedGr;
    }
    
    public Admin getEditedAd() {
        return editedAd;
    }

    public void setEditedAd(Admin editedAd) {
        this.editedAd = editedAd;
    }
    
    public int getAdGroupId() {
        return adGroupId;
    }

    public void setAdGroupId(int adGroupId) {
        this.adGroupId = adGroupId;
    }

    public List<AdminGroup> getListAdGr() {
        return listAdGr;
    }

    public Admin getSelectedAd() {
        return selectedAd;
    }

    public void setSelectedAd(Admin selectedAd) {
        this.selectedAd = selectedAd;
    }
    
    public void setListAdGr(List<AdminGroup> listAdGr) {
        this.listAdGr = listAdGr;
    }
    private String confirmPassword;


    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Admin getAd() {
        return ad;
    }

    public void setAd(Admin ad) {
        this.ad = ad;
    }

    public List<Admin> getList() {
        return list;
    }

    public void setList(List<Admin> list) {
        this.list = list;
    }

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

    public String show() {
        //list = adminFacade.findAll();
        return "/admin/user/viewEmployees";
    }

    public String login() {
        if (adminFacade.login(username, password)) {
            return "/admin/index";
        }
        return "/admin/login";
    }
    public String displayAddEmployee(){
        ad = new Admin();
        //listAdGr = adminGroupFacade.findNormal();
        return "/admin/user/addEmployee";
    }
    public String addEmployee(){
        String uname = ad.getUserName().trim();
        if (!(uname.length() >= 1 && uname.length()<=20)) {
            FacesContext.getCurrentInstance().addMessage("formAdd:usernameMsg", new FacesMessage("Username must between 1 to 20 characters!"));
            return "/admin/user/addEmployee";
        }
        String upass = ad.getUserPass();
        if (!(upass.length() >= 6 && upass.length()<=20)) {
            FacesContext.getCurrentInstance().addMessage("passwordMsg", new FacesMessage("Password must be between 6 and 20 characters!"));
            return "/admin/user/addEmployee";
        }
        if (!(upass.equals(confirmPassword))){
            FacesContext.getCurrentInstance().addMessage("formAdd:confirmPasswordMsg", new FacesMessage("confirm password must be same to password!"));
            return "/admin/user/addEmployee";
        }
        ad.setStatus("Normal");
        ad.setAdmingroupid(adminGroupFacade.find(adGroupId));
        adminFacade.create(ad);
        ad = new Admin();
        return this.show();
    }
    
    public String updateStatusGroup(){
        adminGroupFacade.edit(selectedGr);
        return this.displayViewGroup();
    }
    
    public String editEmployee(){
        ad.setAdmingroupid(adminGroupFacade.find(adGroupId));
        adminFacade.edit(editedAd);
        return this.show();
    }
    
   public String updateStatusEmployee(){
        adminFacade.edit(editedAd);
        return this.show();
    }
   
    public String resetPasswordEmployee(){
        adminFacade.edit(editedAd);
        return this.show();
    }
    
    public String displayEditEmployee(int id){
        editedAd = adminFacade.find(id);
        adGroupId = editedAd.getAdmingroupid().getId();
        return "/admin/user/updateEmployee";
    }
    
    public String displayUpdateStatus(int id){
        editedAd = adminFacade.find(id);
        return "/admin/user/updateStatusEmployee";
    }
    
    public String displayResetPassword(int id){
        editedAd = adminFacade.find(id);
        confirmPassword = "";
        return "/admin/user/resetPasswordEmployee";
    }
    
    public String displayUpdateStatusGroup(int id){
        selectedGr = adminGroupFacade.find(id);
        return "/admin/user/updateStatusGroup";
    }
    
    public String displayDetail(int id){
        selectedAd = adminFacade.find(id);        
        return "/admin/user/detailEmployee";
    }
    
    public String displayDetailGroup(int id){
        selectedGr = adminGroupFacade.find(id);  
        return "/admin/user/detailGroup";
    }
    
    public String displayViewGroup(){
        return "/admin/user/viewGroups";
    }
 
    public List<AdminGroup> showAdminGroup(){
        return adminGroupFacade.findNormal();
    }
    public List<AdminGroup> showAllAdGr(){
        return adminGroupFacade.findAll();
    }
    public List<Admin> showAll(){
        return adminFacade.findAll();
    }
    public List<Admin> showAdminByGroup(){     
        return adminFacade.findByGroup(selectedGr);
    }
    
    public adminMB() {
    }
   
}
