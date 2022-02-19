package org.octri.omop_annotator.view;


import java.util.Collection;

import org.octri.authentication.server.security.entity.User;
import org.springframework.util.Assert;

/**
 * Select option specialized for Users. Provides a custom user label.
 * 
 * @author lawhead
 *
 */
public class UserSelectOption {

	protected Boolean selected;
	protected User choice;

	/**
	 * Constructor for the option
	 *
	 * @param choice
	 *            - lookup list item
	 * @param selected
	 *            - the selected item; may be null
	 */
	public UserSelectOption(User choice, User selected) {
		Assert.notNull(choice, "Entity is required");
		Assert.notNull(choice, "User is required");
		this.choice = choice;

		this.selected = choice.equals(selected);
	}

	/**
	 * Constructor for an option in a multi-select.
	 *
	 * @param choice
	 *            - lookup list item
	 * @param selected
	 *            - selection list
	 */
	public UserSelectOption(User choice, Collection<User> selected) {
		Assert.notNull(choice, "Entity is required");
		Assert.notNull(choice, "User is required");
		this.choice = choice;
		this.selected = selected != null && selected.contains(choice);
	}

	public User getChoice() {
		return choice;
	}

	public String getValue() {
		return choice.getId().toString();
	}

	/**
	 * @return label with the user name, company, and cohort if available.
	 */
	public String getLabel() {
		return choice.getLabel();
	}

	public Boolean getSelected() {
		return selected;
	}

	public Long getId() {
		return choice.getId();
	}
}
