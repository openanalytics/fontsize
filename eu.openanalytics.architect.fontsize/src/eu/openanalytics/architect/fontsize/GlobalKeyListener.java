package eu.openanalytics.architect.fontsize;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IStartup;

import eu.openanalytics.architect.fontsize.preferences.Preferences;

public class GlobalKeyListener implements Listener, IStartup {

	private AtomicBoolean fontIncreaseTriggered = new AtomicBoolean();
	private AtomicBoolean fontDecreaseTriggered = new AtomicBoolean();
	private int triggerDelay = 100;
	
	private boolean enabled = Preferences.getStore().getBoolean(Preferences.PREF_SCROLL_ENABLED);
	private int modifierKey = Preferences.getStore().getInt(Preferences.PREF_SCROLL_MODIFIER_KEY);
	private int scrollDirection = Preferences.getStore().getInt(Preferences.PREF_SCROLL_DIRECTION);
	
	@Override
	public void earlyStartup() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Display.getCurrent().addFilter(SWT.MouseWheel, GlobalKeyListener.this);
			}
		});
		Preferences.getStore().addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(Preferences.PREF_SCROLL_ENABLED)) {
					enabled = Preferences.getStore().getBoolean(Preferences.PREF_SCROLL_ENABLED);
				} else if (event.getProperty().equals(Preferences.PREF_SCROLL_MODIFIER_KEY)) {
					modifierKey = Preferences.getStore().getInt(Preferences.PREF_SCROLL_MODIFIER_KEY);
				} else if (event.getProperty().equals(Preferences.PREF_SCROLL_DIRECTION)) {
					scrollDirection = Preferences.getStore().getInt(Preferences.PREF_SCROLL_DIRECTION);
				}
			}
		});
	}
	
	@Override
	public void handleEvent(Event event) {
		if (!enabled) return;
		if ((event.stateMask & modifierKey) != modifierKey) return;
		int eventCount = event.count;
		if (scrollDirection == SWT.DOWN) eventCount = -eventCount;
		if (eventCount > 0) triggerFontIncrease();
		else triggerFontDecrease();
		// Abort further event handling (i.e. PgUp/PgDown in a text editor) 
		event.type = SWT.None;
	}
	
	private void triggerFontIncrease() {
		if (fontIncreaseTriggered.get()) return;
		fontIncreaseTriggered.set(true);
		Display.getDefault().timerExec(triggerDelay, new Runnable() {
			@Override
			public void run() {
				FontSizeManager.increaseFontSize();
				fontIncreaseTriggered.set(false);
			}
		});
	}
	
	private void triggerFontDecrease() {
		if (fontDecreaseTriggered.get()) return;
		fontDecreaseTriggered.set(true);
		Display.getDefault().timerExec(triggerDelay, new Runnable() {
			@Override
			public void run() {
				FontSizeManager.decreaseFontSize();
				fontDecreaseTriggered.set(false);
			}
		});
	}
}
