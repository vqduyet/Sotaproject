/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.CatalogsFacadeLocal;
import entity.Catalogs;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author phuon
 */
@ManagedBean
@SessionScoped
public class CatalogMB {
    @EJB
    private CatalogsFacadeLocal catalogsFacade;
private Catalogs catalogNew = new Catalogs();
private Catalogs selectedCatalog;
private String defaultStatus = "Normal";

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Catalogs getSelectedCatalog() {
        return selectedCatalog;
    }

    public void setSelectedCatalog(Catalogs selectedCatalog) {
        this.selectedCatalog = selectedCatalog;
    }

    public Catalogs getCatalogNew() {
        return catalogNew;
    }

    public void setCatalogNew(Catalogs catalogNew) {
        this.catalogNew = catalogNew;
    }
    
    /**
     * Creates a new instance of CatalogMB
     */
    public CatalogMB() {
    }
    
    public void addNewCatalog(){
        catalogsFacade.create(catalogNew);
        catalogNew = new Catalogs();
    }
    
    public List<Catalogs> fetchAllCatalogList(){
        return catalogsFacade.findAll();        
    }
    
    public List<Catalogs> fetchActiveCatalogList(){
       return catalogsFacade.getActiveCatalogsList(); 
    }
    
     public List<Catalogs> fetchInactiveCatalogList(){
       return catalogsFacade.getInactiveCatalogsList(); 
    }
    
    public List<Catalogs> fetchParentList(){
        return catalogsFacade.getParentCatalogList();
    }
    
    public List<Catalogs> fetchSubCatalogList(){
        return catalogsFacade.getSubCatalogList();
    }
    
   public String gotoEdit(int id){
       selectedCatalog = catalogsFacade.find(id);
       return "/admin/catalog/catalog-edit?faces-redirect=true";
   }
   
   public void updateCatalog(){
    catalogsFacade.edit(selectedCatalog);
   }
   
   //parent catalog list    
    public List<Catalogs> fetchMenuCatalog(){
        return catalogsFacade.getMenuCatalogs();
    }
    
    //subcatlog by parent id
    public List<Catalogs> fetchSubCatalogByParentId(int id){
        return catalogsFacade.getCatalogByParentId(id);
    }
    
}