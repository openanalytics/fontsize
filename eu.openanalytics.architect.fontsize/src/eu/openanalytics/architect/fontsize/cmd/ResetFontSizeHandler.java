package eu.openanalytics.architect.fontsize.cmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import eu.openanalytics.architect.fontsize.FontSizeManager;

public class ResetFontSizeHandler extends AbstractHandler  {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		FontSizeManager.resetFontSize();
		return null;
	}
}