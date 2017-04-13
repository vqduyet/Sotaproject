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
import entity.Contact;
import beans.ContactFacadeLocal;

/**
 *
 * @author Lenovo
 */
@ManagedBean
@SessionScoped
public class contactMB {
    @EJB
    private ContactFacadeLocal contactFacade;

    private Contact addContact = new Contact();
    private Contact selectedContact = new Contact();

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }
    
    public Contact getAddContact() {
        return addContact;
    }

    public void setAddContact(Contact addContact) {
        this.addContact = addContact;
    }

    public String insertContact(){
        //addContact.setUsersId(null);
        addContact.setStatus("Waiting");
        contactFacade.create(addContact);
        return "index";
    }
    public String displayContact(){
        addContact = new Contact();
        return "contact";
    }
    public String displayViewContacts(){
        return "/admin/user/viewContacts";
    }
    public String displayDetailContact(int id){
        selectedContact = contactFacade.find(id);
        return "/admin/user/detailContact";
    }
    
    public String updateStatusContact(){
        contactFacade.edit(selectedContact);
        return this.displayViewContacts();
    }
    
    public List<Contact> showWaiting(){
        return contactFacade.findWaiting();
    }
    public List<Contact> showInProgress(){
        return contactFacade.findInProgress();
    }
    public List<Contact> showCompleted(){
        return contactFacade.findCompleted();
    }
    public contactMB() {
    }
    
}
