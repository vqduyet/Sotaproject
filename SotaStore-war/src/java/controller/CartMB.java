/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.OrderDetailFacadeLocal;
import beans.OrdersFacadeLocal;
import beans.UsersFacadeLocal;
import entity.OrderDetail;
import entity.OrderDetailPK;
import entity.Orders;
import entity.Products;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Item;
import model.ShippingInfo;

/**
 *
 * @author Duyet
 */
@ManagedBean
@SessionScoped
public class CartMB {
    @EJB
    private UsersFacadeLocal usersFacade;
    @EJB
    private OrderDetailFacadeLocal orderDetailFacade;
    @EJB
    private OrdersFacadeLocal ordersFacade;
    
    //inject product managed bean
    @ManagedProperty(value="#{productMB}")
    private productMB productsMB;

    public productMB getProductsMB() {
        return productsMB;
    }

    public void setProductsMB(productMB productsMB) {
        this.productsMB = productsMB;    }
    
    
    //inject member managed bean
    @ManagedProperty(value="#{usersMB}")
    private usersMB memberMB;

    public usersMB getMemberMB() {
        return memberMB;
    }

    public void setMemberMB(usersMB memberMB) {
        this.memberMB = memberMB;
    }
    
    private List<Item> cart = new ArrayList<Item>();
    private BigDecimal total = BigDecimal.ZERO;
    private Orders order = null;
    private int qtyItemDetail = 1;
    private ShippingInfo shippingInfo;
    private int scoreApplied = 0;
    private boolean shippingCheck = false;
    private int scoreAdded = 0;

    public int getScoreAdded() {
        return scoreAdded;
    }

    public void setScoreAdded(int scoreAdded) {
        this.scoreAdded = scoreAdded;
    }    

    public boolean isShippingCheck() {
        return shippingCheck;
    }

    public void setShippingCheck(boolean shippingCheck) {
        this.shippingCheck = shippingCheck;
    }
    

    public int getScoreApplied() {
        return scoreApplied;
    }

    public void setScoreApplied(int scoreApplied) {
        this.scoreApplied = scoreApplied;
    }
    

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
    

    public int getQtyItemDetail() {
        return qtyItemDetail;
    }

    public void setQtyItemDetail(int qtyItemDetail) {
        this.qtyItemDetail = qtyItemDetail;
    }
    

    public List<Item> getCart() {
        return cart;
    }

    public void setCart(List<Item> cart) {
        this.cart = cart;
    }

    public BigDecimal getTotal() {
        total = BigDecimal.ZERO;
        for(Item item: cart){
            total = total.add(item.getP().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
    
    
    /**
     * Creates a new instance of CartMB
     */
    public CartMB() {
    }
    
    /* add to cart */
    public void addToCart(Products p){ 
        Boolean inCart = false;
        //current item in cart
        for(Item item: cart){
            if(item.getP().getId() == p.getId()){
                item.setQuantity(item.getQuantity()+1);
                inCart = true;
            }
        }
        //if item not in cart
        if(!inCart){
            Item i = new Item(p, 1);
            cart.add(i);
        }       
    }
    
    /* add to cart from product detail */
    public void addQtyItem(){
        Boolean inCart = false;
        //current item in cart
        for(Item item: cart){
            if(item.getP().getId() == productsMB.getSelectedProduct().getId()){
                item.setQuantity(qtyItemDetail);
                inCart = true;
            }
        }
        //if item not in cart
        if(!inCart){
            Item i = new Item(productsMB.getSelectedProduct(), qtyItemDetail);
            cart.add(i);
        }
        qtyItemDetail = 1;
    }
    
    /* count items in cart */
    public int countItems(){
        int itemCount = 0;
        for(Item item : cart){
            itemCount = itemCount + item.getQuantity();
        }
        return itemCount;
    }
    
    /* update quantity of cart item in page shopping cart */
    public void update(){
        
    }
    
    /* remove item in cart */
    public void remove(Item i){
        for(Item item:cart){
            if(item.equals(i)){
                cart.remove(item);
                break;
            }
        }
    }
    
    /* clear cart */
    public void clearCart(){
        cart = new ArrayList<Item>();
    }
    
    /* go to checkout page */
    public String gotoCheckout(){
        if(cart.size()>0){
            scoreAdded = total.intValue()/100000;
            return "/cart/checkout-shipping?faces-redirect=true";
        }
        else{
            return null;
        }        
    }
    
    /* check score applied */
    public void applyScore(){
        if(total.compareTo(new BigDecimal(scoreApplied * 10000)) < 0){
           scoreApplied = 0;
           
        }
    }
    
    /* check shipping info */
    public String checkShippingInfo(){
        return "/cart/checkout-confirm?faces-redirect=true";
    }
    
    /* check out */
    public String checkout(){
        //do 3 tasks: create order, create order-detail, empty cart, go to success/fail page
        if(!cart.isEmpty()){
            try {
            /* create order */
            order = new Orders();
            // total quantity
            order.setQuantity(this.countItems());
            // total amount = pretax * 1.1
            order.setAmount(this.total.multiply(new BigDecimal(1.1)));
            // set user id and user name            
            if(memberMB.getLoginUsers() != null){
               int clientId = memberMB.getLoginUsers().getId();
               order.setUsersId(usersFacade.find(clientId));
               order.setUsersName(memberMB.getLoginUsers().getUserName());
               //shipping check
               if(shippingCheck){ 
                   if(memberMB.getLoginUsers().getDistrict() != null){
                       order.setShippingAddress(memberMB.getLoginUsers().getAddress());
                       order.setDistrict(memberMB.getLoginUsers().getDistrict());
                       order.setShippingFee(memberMB.getLoginUsers().getDistrict().getFee());
                       order.setScore(scoreApplied);
                   }
                   else{
                       return null; //thong bao
                   }
                   //shipping info                  
                   
               }
            } else{
               order.setUsersName(shippingInfo.getCustomerName());
            }
            //shipping info
            
                order.setShippingAddress(null);
            
            // add order 
            ordersFacade.create(order);            
            
            /* create order detail */
            for(Item item: cart){
                OrderDetail orderDetail = new OrderDetail();
                OrderDetailPK pk = new OrderDetailPK(order.getId(), item.getP().getId());
                orderDetail.setOrderDetailPK(pk);
                orderDetail.setPrice(item.getP().getPrice());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setAmount(item.getP().getPrice().multiply(new BigDecimal(item.getQuantity())));
                orderDetailFacade.create(orderDetail);
            }
            } catch (Exception e) {
                e.printStackTrace();
                return "/cart/order-error?faces-redirect=true";
            }
            //empty cart
            cart = new ArrayList<Item>();          
        }  
        return "/cart/order-success?faces-redirect=true";
    }
    
}
