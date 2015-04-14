package eu.openanalytics.architect.fontsize.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static final String[][] MODIFIER_KEYS = {
		{ "Ctrl", String.valueOf(SWT.CTRL) },
		{ "Alt", String.valueOf(SWT.ALT) },
		{ "Shift", String.valueOf(SWT.SHIFT) },
	};
	
	private static final String[][] SCROLL_DIRECTIONS = {
		{ "Scroll up to zoom in", String.valueOf(SWT.UP) },
		{ "Scroll down to zoom in", String.valueOf(SWT.DOWN) }
	};
	
	public PreferencePage() {
		setPreferenceStore(Preferences.getStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		// Nothing to do.
	}
	
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(Preferences.PREF_SCROLL_ENABLED, "Zoom in/out using the mouse scroll wheel", getFieldEditorParent()));
		addField(new ComboFieldEditor(Preferences.PREF_SCROLL_MODIFIER_KEY, "Scroll modifier key:", MODIFIER_KEYS, getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(Preferences.PREF_SCROLL_DIRECTION, "Scroll wheel behaviour:", 1, SCROLL_DIRECTIONS, getFieldEditorParent()));
	}

}
