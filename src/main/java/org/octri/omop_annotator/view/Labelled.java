package org.octri.omop_annotator.view;

/**
 * Interface used for labelling an object in the UI.
 * 
 * @author lawhead
 */
public interface Labelled {

	/**
	 * @return Label presented to the user in the UI.
	 */
	public abstract String getLabel();
}
