package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.java2enterprise.onlineshop.model.Item;

@Named
@SessionScoped
public class ViewItemController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static Logger log = Logger.getLogger(SearchController.class.toString());

	@PersistenceContext
	private EntityManager em;

	private List<Item> items;

	private Long id;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String setId(Long id) {
		this.id=id;
		return "/viewItem.jsf";
	}

	public Long getId() {
		return id;
	}

	/**
	 * Methode welche anhand der Item ID welche hier zwischengespeichert wird, diese in der DB suchen geht
	 * 
	 * @param id
	 * @return
	 */
	public List<Item> finditem() {
		items = null;
		items = new ArrayList<Item>();
		try {
			TypedQuery<Item> query = em.createNamedQuery("Item.findItem", Item.class);
			query = query.setParameter("id", id);
			return query.getResultList();
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return new ArrayList<Item>();
	}

	public String returned() {
		return "observationlist.jsf";
	}

}