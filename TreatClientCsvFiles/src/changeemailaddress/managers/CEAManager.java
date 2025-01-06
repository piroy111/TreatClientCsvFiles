package changeemailaddress.managers;

import java.nio.file.Path;
import java.util.List;

import basicmethods.BasicPrintMsg;

public class CEAManager {

	public CEAManager() {
		pCEAChangeFileContent = new CEAChangeFileContent(this);
		pCEAFilesReader = new CEAFilesReader(this);
		pCEAChangeFileName = new CEAChangeFileName(this);
	}
	
	
	/*
	 * Data
	 */
	private CEAChangeFileContent pCEAChangeFileContent;
	private CEAFilesReader pCEAFilesReader;
	private CEAChangeFileName pCEAChangeFileName;
	private String pEmailAddressOld;
	private String pEmailAddressNew;
	
	/**
	 * 
	 */
	public final void run(String _sOldEmailAddress, String _sNewEmailAddress) {
		pEmailAddressOld = _sOldEmailAddress;
		pEmailAddressNew = _sNewEmailAddress;
		/*
		 * Communication
		 */
		BasicPrintMsg.displayTitle(this, "Change email address '" + pEmailAddressOld
				+ "' -> '" + pEmailAddressNew + "'");
		/*
		 * Load files
		 */
		pCEAFilesReader.run();
		List<Path> lListPathToCheck = pCEAFilesReader.getpListPathToCheck();
		/*
		 * Loop over all the files
		 */
		int lNbTotal = lListPathToCheck.size();
		int lPercent = 0;
		for (int lIdx = 0; lIdx < lListPathToCheck.size(); lIdx++) {
			if (lIdx >= lNbTotal * lPercent / 100) {
				BasicPrintMsg.display(this, "Number of files treated: " + lIdx + "/" + lNbTotal + "= " + lPercent + "%", 100);
				lPercent += 10;
			}
			Path lPath = lListPathToCheck.get(lIdx);
			/*
			 * Change the name of the files which contain the email address (clients files)
			 */
			lPath = pCEAChangeFileName.run(lPath);
			/*
			 * Check file content
			 */
			pCEAChangeFileContent.run(lPath);			
		}
	}

	/*
	 * Getters & Setters
	 */
	public final String getpEmailAddressOld() {
		return pEmailAddressOld;
	}
	public final String getpEmailAddressNew() {
		return pEmailAddressNew;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
