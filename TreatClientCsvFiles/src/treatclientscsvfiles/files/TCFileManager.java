package treatclientscsvfiles.files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicPrintMsg;
import treatclientscsvfiles.clients.TCClient;
import treatclientscsvfiles.movefiles.TCManager;

public class TCFileManager {

	public TCFileManager(TCManager _sTCManager) {
		pTCManager = _sTCManager;
		/*
		 * 
		 */
		pListTCFileToDelete = new ArrayList<TCFile>();
		pListTCFileToMove = new ArrayList<>();
		pMapDirPlusNameToTCFile = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private TCManager pTCManager;
	private List<TCFile> pListTCFileToMove;
	private List<TCFile> pListTCFileToDelete;
	private Map<String, TCFile> pMapDirPlusNameToTCFile;
	
	/**
	 * 
	 */
	public final void loadFiles() {
		for (String lDir : pTCManager.getpTCCreateDirs().getpListDir()) {
			List<Path> lListPaths = BasicFichiersNioRaw.getListPath(Paths.get(lDir));
			for (Path lPath : lListPaths) {
				/*
				 * Create TCFile
				 */
				String lNameFile = lPath.getFileName().toString();
				TCFile lTCFile = getpOrCreateTCFile(lDir, lNameFile);
				/*
				 * Read the file
				 */
				lTCFile.readFileAndLoadObjects();
				/*
				 * Case the file does not exist --> we should move it and rename it
				 */
				TCClient lTCClient = lTCFile.getpTCClient();
				if (lTCClient.isNewTCFile(lTCFile)) {
					lTCClient.addNewTCFile(lTCFile);
					pListTCFileToMove.add(lTCFile);
					lTCFile.setpTCClient(lTCClient);
					/*
					 * Count quantities to trade for future display of the quantities to hedge
					 */
					pTCManager.getpTCCountManager().addNewFile(lTCFile);
				}
				/*
				 * Case the file already exist --> we delete it
				 */
				else {
					pListTCFileToDelete.add(lTCFile);
					BasicPrintMsg.display(this, "File already there: " + lTCFile.getpReadFile().getmNomCheminPlusFichier(), 50);
				}
			}
		}
		/*
		 * Sort
		 */
		Collections.sort(pListTCFileToMove);
		Collections.sort(pListTCFileToDelete);
		/*
		 * Build the names of the TCFiles to move
		 */
		for (TCFile lTCFile : pListTCFileToMove) {
			TCClient lTCClient = lTCFile.getpTCClient();
			lTCClient.addNewCount();
			String lCountStr = "" + lTCClient.getpCount();
			if (lTCClient.getpCount() < 10) {
				lCountStr = "0" + lCountStr;
			}
			String lNewNameFile = lTCClient.getpEmail() + "_" + lCountStr + ".csv";
			lTCFile.setpNameFileNew(lNewNameFile);
			BasicPrintMsg.display(this, "New file to move: Date= " + lTCFile.getpDate() + "; NewName= " + lTCFile.getpNameFileNew(), 50);
		}
	}

	/**
	 * 
	 * @param _sDir
	 * @param _sNameFile
	 * @return
	 */
	public final TCFile getpOrCreateTCFile(String _sDir, String _sNameFile) {
		String lKeyStr = _sDir + _sNameFile;
		TCFile lTCFile = pMapDirPlusNameToTCFile.get(lKeyStr);
		if (lTCFile == null) {
			lTCFile = new TCFile(_sDir, _sNameFile, pTCManager);
			pMapDirPlusNameToTCFile.put(lKeyStr, lTCFile);
		}
		return lTCFile;
	}
	
	/*
	 * Getters & Setters
	 */
	public final TCManager getpTCManager() {
		return pTCManager;
	}
	public final List<TCFile> getpListTCFileToMove() {
		return pListTCFileToMove;
	}
	public final List<TCFile> getpListTCFileToDelete() {
		return pListTCFileToDelete;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
