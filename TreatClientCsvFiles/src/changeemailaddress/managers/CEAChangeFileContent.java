package changeemailaddress.managers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import basicmethods.LitUnFichierEnLignes;

class CEAChangeFileContent {

	protected CEAChangeFileContent(CEAManager _sCEAManager) {
		pCEAManager = _sCEAManager;
	}
	
	
	/*
	 * Data
	 */
	private CEAManager pCEAManager;


	/**
	 * Change the content of the files
	 */
	protected final void run(Path _sPath) {
		/*
		 * Initiate
		 */
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(_sPath, true);
		boolean lIsAtLeastOneChange = false;
		List<String> lListLineToWrite = new ArrayList<>();
		/*
		 * Read lines
		 */
		for (int lIdx = 0; lIdx < lReadFile.getmContenuFichierLignes().size(); lIdx++) {
			String lLineStr = lReadFile.getmContenuFichierLignes().get(lIdx);
			if (lLineStr.contains(pCEAManager.getpEmailAddressOld())) {
				/*
				 * Change the line and store it
				 */
				lListLineToWrite.add(lLineStr.replaceAll(pCEAManager.getpEmailAddressOld(), pCEAManager.getpEmailAddressNew()));
				lIsAtLeastOneChange = true;
				/*
				 * Communication
				 */
				BasicPrintMsg.display(this, "Changed line in file '" + _sPath.getFileName().toString() + "'; Line Number " + (lIdx + 2), 50);
			} else {
				lListLineToWrite.add(lLineStr);
			}
		}
		/*
		 * Write new file
		 */
		if (lIsAtLeastOneChange) {
			String lDir = _sPath.getParent().toString();
			String lName = _sPath.getFileName().toString();
			String lHeader = null;
			if (lReadFile.getmHeadersAndComments().size() > 0) {
				lHeader = lReadFile.getmHeadersAndComments().get(0);
			}
			BasicFichiers.writeFile(lDir, lName, lHeader, lListLineToWrite);
			/*
			 * Communication
			 */
			BasicPrintMsg.display(this,  "Overwitten file '" + lName + "'", 50);
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final CEAManager getpCEAManager() {
		return pCEAManager;
	}
	
	
	
	
}
