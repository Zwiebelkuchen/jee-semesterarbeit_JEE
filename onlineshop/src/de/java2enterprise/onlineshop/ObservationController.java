package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Named
@RequestScoped
public class ObservationController implements Serializable {
    private static final long serialVersionUID = 1L;

    private final static Logger log = Logger
            .getLogger(ObservationController.class.toString());

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    /**
     * Methode um ein Item auf die Ueberwachungsliste zu setzen
     * @param id
     * @return
     */
    public String setObservation(Long id) {
        FacesContext ctx = FacesContext
                .getCurrentInstance();
        ELContext elc = ctx.getELContext();
        ELResolver elr = ctx.getApplication()
                .getELResolver();
        SigninController signinController = (SigninController) elr
                .getValue(
                        elc,
                        null,
                        "signinController");

        Customer customer = signinController.getCustomer();
        try {
            ut.begin();
            Item item = em.find(Item.class, id);
            customer.observe(item);
            ut.commit();
            FacesMessage m = new FacesMessage(
                    "Added article " + item.getTitle() + " to observed list");
            FacesContext
                    .getCurrentInstance()
                    .addMessage("searchForm", m);
            log.info(item + " bought by " + customer);
           
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return "/search.jsf";
    }   
    
    /**
     * Methode fuer das entfernen eines Item von der Beobachtungsliste
     * @param id
     * @return
     */
    public String deleteObservation(Long id) {
        FacesContext ctx = FacesContext
                .getCurrentInstance();
        ELContext elc = ctx.getELContext();
        ELResolver elr = ctx.getApplication()
                .getELResolver();
        SigninController signinController = (SigninController) elr
                .getValue(
                        elc,
                        null,
                        "signinController");
        Customer customer = signinController.getCustomer();
        try {
            ut.begin();
            Item item = em.find(Item.class, id);
            customer.unobserve(item);
            ut.commit();
            log.info(item + " bought by " + customer);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return "/obervationslist.jsf";
    }  
  }