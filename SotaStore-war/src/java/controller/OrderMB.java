/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.ShippingFeeFacadeLocal;
import entity.ShippingFee;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Duyet
 */
@ManagedBean
@SessionScoped
public class OrderMB {
    @EJB
    private ShippingFeeFacadeLocal shippingFeeFacade;
    private ShippingFee selectedShippingFee;
    
    public ShippingFee getSelectedShippingFee() {
        return selectedShippingFee;
    }

    public void setSelectedShippingFee(ShippingFee selectedShippingFee) {
        this.selectedShippingFee = selectedShippingFee;
    }
    
    
    /**
     * Creates a new instance of OrderMB
     */
    public OrderMB() {
    }
    
    public List<ShippingFee> getAllShippingList(){
        return shippingFeeFacade.findAll();
    }
    
    public String gotoEditFee(int id){
        selectedShippingFee = shippingFeeFacade.find(id);
        return "/admin/order/shipping-fee-edit?faces-redirect=true";
    }
    
    public void updateFee(){}
    
}
