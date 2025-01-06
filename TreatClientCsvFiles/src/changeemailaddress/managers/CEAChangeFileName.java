package changeemailaddress.managers;

import java.nio.file.Path;
import java.nio.file.Paths;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicTime;

class CEAChangeFileName {

	protected CEAChangeFileName(CEAManager _sCEAManager) {
		pCEAManager = _sCEAManager;
	}
	
	
	/*
	 * Data
	 */
	private CEAManager pCEAManager;


	/**
	 * Change the name of the files which contain the email address (clients files)
	 */
	protected final Path run(Path _sPath) {
		String lNameOld = _sPath.getFileName().toString();
		if (lNameOld.contains(pCEAManager.getpEmailAddressOld())) {
			/*
			 * Initiate
			 */
			String lNewNameFull = _sPath.toString().replaceAll(pCEAManager.getpEmailAddressOld(), pCEAManager.getpEmailAddressNew());
			Path lPathTarget = Paths.get(lNewNameFull);
			/*
			 * Change name of the file
			 */
			BasicFichiersNio.moveFile(_sPath, lPathTarget, true);
			/*
			 * Communication
			 */
			String lNameNew = lPathTarget.getFileName().toString();
			BasicPrintMsg.display(this, "Change of file name " + lNameOld + " --> " + lNameNew, 50);
			/*
			 * 
			 */
			BasicTime.sleep(500);
			return lPathTarget;
		}
		return _sPath;
	}
	
	/*
	 * Getters & Setters
	 */
	public final CEAManager getpCEAManager() {
		return pCEAManager;
	}
	
	
}
