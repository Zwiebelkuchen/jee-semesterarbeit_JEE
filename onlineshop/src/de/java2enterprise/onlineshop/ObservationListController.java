package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.java2enterprise.onlineshop.model.Customer;
import de.java2enterprise.onlineshop.model.Item;

@Named
@RequestScoped
public class ObservationListController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(SearchController.class.toString());

	@PersistenceContext
	private EntityManager em;

	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Methode welche anhand der Customer ID die Beobachtungsliste zusammenstellt
	 * 
	 * @return
	 */
	public List<Item> findItemList() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ELContext elc = ctx.getELContext();
		ELResolver elr = ctx.getApplication().getELResolver();
		SigninController signinController = (SigninController) elr.getValue(elc, null, "signinController");
		Customer customer = signinController.getCustomer();
		Set<Item> cuslist = customer.getObservers();
		ArrayList<Item> list = new ArrayList<Item>();
		for (Item i : cuslist) {
			list.addAll(finditem((long) i.getId()));
		}

		return list;
	}

	/**
	 * Methode welche anhand einer Item ID diese in der DB suchen geht
	 * 
	 * @param id
	 * @return
	 */
	private List<Item> finditem(Long id) {
		try {
			TypedQuery<Item> query = em.createNamedQuery("Item.findItem", Item.class);
			query = query.setParameter("id", id);
			return query.getResultList();
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return new ArrayList<Item>();
	}
}