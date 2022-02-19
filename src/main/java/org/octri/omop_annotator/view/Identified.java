package org.octri.omop_annotator.view;

/**
 * Interface used for an object with an id that can be used distinctly.
 * 
 */
public interface Identified {

	/**
	 * 
	 * @return the identifier for the object
	 */
	public abstract Long getId();
}
