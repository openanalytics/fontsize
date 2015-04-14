package eu.openanalytics.architect.fontsize;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class FontSizeManager {

	private static final ScopedPreferenceStore WB_PREF_STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.ui.workbench" );
	
	public static void increaseFontSize() {
		modifyFontSize(1.0f);
	}
	
	public static void decreaseFontSize() {
		modifyFontSize(-1.0f);
	}
	
	public static void resetFontSize() {
		modifyFontSize(Float.NaN);
	}
	
	private static void modifyFontSize(float modifier) {
		IWorkbenchPart activePart = getActivePart();
		String fontPreference = getMappedFontPreference(activePart);
		if (fontPreference == null || fontPreference.isEmpty()) return;
		
		String prefValue = WB_PREF_STORE.getString(fontPreference);
		if (prefValue == null || prefValue.isEmpty()) return;
		
		if (Float.isNaN(modifier)) {
			WB_PREF_STORE.setToDefault(fontPreference);
		} else {
			String[] font = prefValue.split("\\|");
			float fontSize = Math.max(1, Float.parseFloat(font[2]) + modifier);
			font[2] = Float.toString(fontSize);
			
			StringBuilder newFont = new StringBuilder(font[0]);
			for (int i=1; i<font.length; i++) newFont.append('|').append(font[i]);
			
			WB_PREF_STORE.setValue(fontPreference, newFont.toString());
		}
	}
	
	private static IWorkbenchPart getActivePart() {
		IWorkbenchPart activePart = null;
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null) activePart = activeWorkbenchWindow.getActivePage().getActivePart();
		return activePart;
	}
	
	private static String getMappedFontPreference(IWorkbenchPart part) {
		if (part == null) return null;
		String partId = part.getSite().getId();
		if (partId == null) return null;
		
		//TODO For testing purposes.
		Map<String, String> mappings = new HashMap<String, String>();
		mappings.put("org.eclipse.mylyn.wikitext.ui.editor.markupEditor", "org.eclipse.mylyn.wikitext.ui.presentation.textFont");
		mappings.put("org.eclipse.ui.console.ConsoleView", "de.walware.workbench.themes.ConsoleFont");
		mappings.put("org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor", "org.eclipse.jdt.ui.editors.textfont");

		if (!mappings.containsKey(partId)) return JFaceResources.TEXT_FONT;
		return mappings.get(partId);
	}
}
