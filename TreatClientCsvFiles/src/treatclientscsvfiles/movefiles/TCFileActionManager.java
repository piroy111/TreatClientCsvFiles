package treatclientscsvfiles.movefiles;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import treatclientscsvfiles.files.TCFile;
import treatclientscsvfiles.tcstatic.TCStatic;

class TCFileActionManager {

	protected TCFileActionManager(TCManager _sTCManager) {
		pTCManager = _sTCManager;
	}
	
	/*
	 * Data
	 */
	private TCManager pTCManager;

	/**
	 * 
	 */
	public final void run()  {
		/*
		 * Move Files
		 */
		for (TCFile lTCFile : pTCManager.getpTCFileManager().getpListTCFileToMove()) {
			if (TCStatic.getIS_TOUCH_FILES()) {
				/*
				 * Load data
				 */
				String lNameOld = lTCFile.getpReadFile().getmNomFichier();
				String lNameNew = lTCFile.getpNameFileNew();
				/*
				 * Rename file
				 */
				BasicPrintMsg.display(this,  "Rename file '" + lNameOld	+ "' -> '" + lNameNew + "'");
				BasicFichiersNio.renameFile(Paths.get(lTCFile.getpDir() + lNameOld), lNameNew, true);
				/*
				 * Copy file
				 */
				BasicPrintMsg.display(this,  "Copy file '" + lNameNew + "' into " + lTCFile.getpDir());
				BasicFichiersNio.copyFiles(Paths.get(lTCFile.getpDir() + lNameNew), lTCFile.getpDirOutput(), false, true);
				/*
				 * Delete old file
				 */
				BasicPrintMsg.display(this,  "Delete file old file '" + lNameOld + "'");
				BasicFichiersNio.deleteFile(lTCFile.getpDir() + lNameNew);
			}
		}
		/*
		 * Delete Files
		 */
		List<TCFile> lListTCFile = new ArrayList<>();
		lListTCFile.addAll(pTCManager.getpTCFileManager().getpListTCFileToDelete());
		for (TCFile lTCFile : lListTCFile) {
			if (TCStatic.getIS_TOUCH_FILES()) {
				BasicPrintMsg.display(this,  "Delete file '" + lTCFile.getpNameFile() + "'");
				BasicFichiersNio.deleteFile(lTCFile.getpDir() + lTCFile.getpNameFile());
			}
		}
		/*
		 * Communication
		 */
		BasicPrintMsg.display(this,  "All Ok");
	}
	
}
