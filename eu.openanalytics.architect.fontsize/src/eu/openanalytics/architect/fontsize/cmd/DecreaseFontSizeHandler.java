package eu.openanalytics.architect.fontsize.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import eu.openanalytics.architect.fontsize.FontSizeManager;

public class DecreaseFontSizeHandler extends AbstractHandler  {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		FontSizeManager.decreaseFontSize();
		return null;
	}
}