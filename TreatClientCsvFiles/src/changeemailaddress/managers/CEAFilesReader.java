package changeemailaddress.managers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import staticdata.StaticDir;

class CEAFilesReader {

	protected CEAFilesReader(CEAManager _sCEAManager) {
		pCEAManager = _sCEAManager;
	}
	
	
	/*
	 * Data
	 */
	private CEAManager pCEAManager;
	private List<Path> pListPathToCheck;

	/**
	 * 
	 */
	protected final void run() {
		BasicPrintMsg.display(this, "Load the names of all the files to be checked");
		/*
		 * Initiate
		 */
		List<String> lListDirRoot = getpAndComputeListDirRoot();
		pListPathToCheck = new ArrayList<>();
		/*
		 * Add all the files in the folders and sub folders
		 */
		for (String lDir : lListDirRoot) {
			Path lPathRoot = Paths.get(lDir);
			List<Path> lListPath = BasicFichiersNio.getListFilesAndSubFiles(lPathRoot);
			pListPathToCheck.addAll(lListPath);
		}
		BasicPrintMsg.display(this, pListPathToCheck.size() + " files found");
	}
	
	/**
	 * Manual input
	 * @return
	 */
	private List<String> getpAndComputeListDirRoot() {
		List<String> lListDirRoot = new ArrayList<>();
		/*
		 * Written manually
		 */
		lListDirRoot.add(StaticDir.getCONF_COMPTA());
		lListDirRoot.add(StaticDir.getIMPORT_CSV());
		lListDirRoot.add(StaticDir.getIMPORT_CSV_TREATED());
		lListDirRoot.add(StaticDir.getOUTPUT());
		/*
		 * Return
		 */
		return lListDirRoot;
	}
	
	/*
	 * Getters & Setters
	 */
	public final CEAManager getpCEAManager() {
		return pCEAManager;
	}
	public final List<Path> getpListPathToCheck() {
		return pListPathToCheck;
	}
	
	
}
