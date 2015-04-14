package eu.openanalytics.architect.fontsize.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;

import eu.openanalytics.architect.fontsize.Activator;

public class Preferences extends AbstractPreferenceInitializer {

	public static final String PREF_SCROLL_ENABLED = "SCROLL_ENABLED";
	public static final String PREF_SCROLL_MODIFIER_KEY = "SCROLL_MODIFIER_KEY";
	public static final String PREF_SCROLL_DIRECTION = "SCROLL_DIRECTION";
	
	@Override
	public void initializeDefaultPreferences() {
		getStore().setDefault(PREF_SCROLL_ENABLED, true);
		getStore().setDefault(PREF_SCROLL_MODIFIER_KEY, SWT.CTRL);
		getStore().setDefault(PREF_SCROLL_DIRECTION, SWT.UP);
	}
	
	public static IPreferenceStore getStore() {
		return Activator.getDefault().getPreferenceStore();
	}
}
