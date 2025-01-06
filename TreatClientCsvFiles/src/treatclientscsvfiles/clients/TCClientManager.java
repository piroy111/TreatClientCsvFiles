package treatclientscsvfiles.clients;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicFichiersNioRaw;
import basicmethods.BasicString;
import treatclientscsvfiles.files.TCFile;
import treatclientscsvfiles.movefiles.TCManager;
import treatclientscsvfiles.tcstatic.TCStatic;

public class TCClientManager {

	public TCClientManager(TCManager _sTCManager) {
		pTCManager = _sTCManager;
	}

	/*
	 * Data
	 */
	private TCManager pTCManager;
	private List<TCClient> pListTCClient;
	private Map<String, TCClient> pMapEmailToTCClient;

	/**
	 * 
	 */
	public final void run() {
		pListTCClient = new ArrayList<TCClient>();
		pMapEmailToTCClient = new HashMap<String, TCClient>();
		String[] lArrayDir = new String[]{TCStatic.getDIR_OUTPUT_TRANSACTIONS(), TCStatic.getDIR_OUTPUT_DELIVERY()};
		for (String lDir : lArrayDir) {
			List<Path> lListFiles = BasicFichiersNioRaw.getListPath(Paths.get(lDir));
			for (Path lPath : lListFiles) {
				/*
				 * Read name of the file and the email
				 */
				String lNameFile = lPath.getFileName().toString();
				String[] lArrayStr = lNameFile.split("_", -1);
				String lEmail = lArrayStr[0];
				for (int lIdx = 1; lIdx < lArrayStr.length - 1; lIdx++) {
					lEmail += "_" + lArrayStr[lIdx];
				}
				/*
				 * Create TCClient
				 */
				TCClient lTCClient = getpOrCreateTCClient(lEmail);
				int lCount = BasicString.getInt(lArrayStr[lArrayStr.length - 1]);
				lTCClient.setpCount(Math.max(lTCClient.getpCount(), lCount));
				/*
				 * We create a TCFile from the files which are in the folder target (the folder where there are all the files of the clients)
				 * We store all the files already there in the object TCClient, so we will not create a duplicate
				 */
				TCFile lTCFile = new TCFile(lDir, lNameFile, pTCManager);
				lTCClient.addNewTCFile(lTCFile);
			}
		}
	}

	/**
	 * Classic
	 * @param _sEmail
	 * @return
	 */
	public final TCClient getpOrCreateTCClient(String _sEmail) {
		TCClient lTCClient = pMapEmailToTCClient.get(_sEmail);
		if (lTCClient == null) {
			lTCClient = new TCClient(_sEmail);
			pMapEmailToTCClient.put(_sEmail, lTCClient);
			pListTCClient.add(lTCClient);
		}
		return lTCClient;
	}

	/*
	 * Getters & Setters
	 */
	public final TCManager getpTCManager() {
		return pTCManager;
	}
	public final List<TCClient> getpListTCClient() {
		return pListTCClient;
	}
	public final Map<String, TCClient> getpMapEmailToTCClient() {
		return pMapEmailToTCClient;
	}





}
