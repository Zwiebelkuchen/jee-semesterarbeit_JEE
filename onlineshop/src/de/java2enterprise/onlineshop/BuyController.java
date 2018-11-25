package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Named
@RequestScoped
public class BuyController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;

	public void update(Long id) {
		try {
			ut.begin();
			Item item = em.find(Item.class, id);
			item.setBuyer(getCustomer());
			item.setSold(new Date());
			em.merge(item);
			ut.commit();
		} catch(Exception e) {
			Logger.getLogger(BuyController.class.getCanonicalName())
				.log(Level.WARNING, "Fehler: " + e.getMessage());
		}
		
	}
	
	private Customer getCustomer() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = 
				(SigninController) elr.getValue(elc, null, "signinController");
		return signinController.getCustomer();
	}
}
