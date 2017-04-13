/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.SlidesFacadeLocal;
import entity.Slides;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author Duyet
 */
@ManagedBean
@SessionScoped
public class SlideMB {
    @EJB
    private SlidesFacadeLocal slidesFacade;
    private Slides selectedSlide;
    private String slideName;
    private Part imgFile;
    private int sortOrder;
    private String info;

    public String getSlideName() {
        return slideName;
    }

    public void setSlideName(String slideName) {
        this.slideName = slideName;
    }

    public Part getImgFile() {
        return imgFile;
    }

    public void setImgFile(Part imgFile) {
        this.imgFile = imgFile;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    

    public Slides getSelectedSlide() {
        return selectedSlide;
    }

    public void setSelectedSlide(Slides selectedSlide) {
        this.selectedSlide = selectedSlide;
    }
    

    /**
     * Creates a new instance of SlideMB
     */
    public SlideMB() {
    }
    
    public List<Slides> getAllSlides(){
        return slidesFacade.findAll();
    }
    
    public String gotoEdit(int id){
        selectedSlide = slidesFacade.find(id);
        return "/admin/slide/slide-edit?faces-redirect=true";
    }
    
    public void delete(int id){}
    
    public void save(){}
    
    public void addSlide(){}
    
    public void updateSlide(){}
    
}
