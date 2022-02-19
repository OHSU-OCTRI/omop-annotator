package org.octri.omop_annotator.view;

/**
 * Interface used for labeling an object in the UI.
 * 
 *
 */
public interface Labelled {

	/**
	 * @return Label presented to the user in the UI.
	 */
	public abstract String getLabel();
}
