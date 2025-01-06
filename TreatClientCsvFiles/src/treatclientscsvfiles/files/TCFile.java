package treatclientscsvfiles.files;

import java.util.List;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import treatclientscsvfiles.assets.TCAsset;
import treatclientscsvfiles.assets.TCCountManager;
import treatclientscsvfiles.clients.TCClient;
import treatclientscsvfiles.movefiles.TCManager;
import treatclientscsvfiles.tcstatic.TCStatic;
import treatclientscsvfiles.tcstatic.TCStatic.TCFileType;

public class TCFile implements Comparable<TCFile> {

	public TCFile(String _sDir, String _sNameFile, TCManager _sTCManager) {
		pNameFile = _sNameFile;
		pDir = _sDir;
		pTCManager = _sTCManager;
		/*
		 * We read the file
		 */
		pReadFile = new LitUnFichierEnLignes(pDir, pNameFile, true);
	}
	
	/*
	 * Data
	 */
	private TCManager pTCManager;
	private LitUnFichierEnLignes pReadFile;
	private int pDate;
	private String pNameFile;
	private String pNameFileNew;
	private String pDir;
	private TCClient pTCClient;
	private TCAsset pTCAssetMetal;
	private TCAsset	pTCAssetCurrency;
	private TCFileType pTCFileType;
	private double pQuantityMetal;
	private double pQuantityCurrency;
	
	/**
	 * Set TCClient, TCAssets
	 */
	public final void readFileAndLoadObjects() {
		/*
		 * Date of the file is inside the file
		 */
		pDate = BasicString.getInt(pReadFile.getmContenuFichierListe().get(0).get(0));
		/*
		 * Look for the email inside the name of the file
		 */
		if (!pNameFile.endsWith(".csv")) {
			BasicPrintMsg.error("The name of the file should end with '.csv' + \nName of file= '" + pNameFile + "'");
		}
		String lEmail = pNameFile.substring(0, pNameFile.length() - ".csv".length());
		if (!lEmail.endsWith(".com") 
				&& !lEmail.endsWith(".com.sg")
				&& !lEmail.endsWith(".fr")
				&& !lEmail.endsWith(".org.sg")
				&& !lEmail.endsWith("web.de")
				&& !lEmail.endsWith(".net")
				&& !lEmail.endsWith(".de")
				&& !lEmail.endsWith(".be")
				&& !lEmail.endsWith(".co.jp")
				) {
			BasicPrintMsg.error("The name of the file should end with '.com.csv' + \nName of file= '" + pNameFile + "'");
		}
		/*
		 * TCClient
		 */
		pTCClient = pTCManager.getpTCClientManager().getpOrCreateTCClient(lEmail);
		/*
		 * Read file
		 * Determine the TCAssets of the File and if the file is the delivery of a bar (there is no currency) or a transaction (there is a currency)
		 */
		pQuantityMetal = 0.;
		pQuantityCurrency = 0.;
		for (List<String> lLineStr : pReadFile.getmContenuFichierListe()) {
			/*
			 * Load
			 */
			int lIdx = -1;
			++lIdx;
			++lIdx;
			String lNameBunker = lLineStr.get(++lIdx);
			double lQuantity = BasicString.getDouble(lLineStr.get(++lIdx));
			/*
			 * Load the assets related to the file and check there is only one asset of each type (currency & metal)
			 */
			TCCountManager lTCCountManager = pTCManager.getpTCCountManager();
			TCAsset lTCAsset = lTCCountManager.getpOrCreateTCAsset(lNameBunker);
			if (lTCAsset.getpIsCurrency()) {
				if (pTCAssetCurrency != null && !lTCAsset.equals(pTCAssetCurrency)) {
					BasicPrintMsg.error("The file contains 2 different currencies"
							+ "\nCurrency1= " + pTCAssetCurrency
							+ "\nCurrency2= " + lTCAsset
							+ "\nFile= " + this);
				}
			} else {
				if (pTCAssetMetal != null && !lTCAsset.equals(pTCAssetMetal)) {
					BasicPrintMsg.error("The file contains 2 different metals"
							+ "\nMetal1= " + pTCAssetMetal
							+ "\nMetal2= " + lTCAsset
							+ "\nFile= " + this);
				}
			}
			/*
			 * Compute the quantities
			 */
			if (lTCAsset.getpIsCurrency()) {
				pTCAssetCurrency = lTCCountManager.getpAndCheckTCAsset(pTCAssetCurrency, lTCAsset);
				pQuantityCurrency += lQuantity;
			} else {
				pTCAssetMetal = lTCCountManager.getpAndCheckTCAsset(pTCAssetMetal, lTCAsset);
				pQuantityMetal += lQuantity;
			}
		}
		/*
		 * Check the type of the file. If there is no currency then it is a delivery
		 */
		if (pTCAssetCurrency == null) {
			pTCFileType = TCFileType.Delivery;
		} else {
			pTCFileType = TCFileType.Transaction;
		}
	}
	
	/**
	 * 
	 * @param _sTCFile
	 * @return
	 */
	public final boolean getpIsEquals(TCFile _sTCFile) {
		List<String> lListLineStr1 = _sTCFile.getpReadFile().getmContenuFichierLignes();
		List<String> lListLineStr0 = pReadFile.getmContenuFichierLignes();
		if (lListLineStr0.size() != lListLineStr1.size()) {
			return false;
		}
		for (int lIdx = 0; lIdx < lListLineStr0.size(); lIdx++) {
			if (!lListLineStr0.get(lIdx).equals(lListLineStr1.get(lIdx))) {
				return false;
			}
		}
		return true;
	}

	@Override public int compareTo(TCFile _sTCFile) {
		return Integer.compare(pDate,  _sTCFile.getpDate());
	}
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return pDir + pNameFile;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getpDirOutput() {
		if (pTCFileType == TCFileType.Transaction) {
			return TCStatic.getDIR_OUTPUT_TRANSACTIONS();
		} else {
			return TCStatic.getDIR_OUTPUT_DELIVERY();
		}
	}
	
	/**
	 * 
	 * @param pNameFileNew
	 */
	public final void setpNameFileNew(String _sNameFileNew) {
		if (_sNameFileNew.split(".csv", -1).length > 2) {
			BasicPrintMsg.error("The name of the file is incorrect. It contains '.csv' more than once"
					+ "\nFile Name new= " + _sNameFileNew);
		}
		if (!_sNameFileNew.endsWith(".csv")) {
			BasicPrintMsg.error("The name of the file is incorrect. It does not end with '.csv'"
					+ "\nFile Name new= " + _sNameFileNew);
		}
		String[] lWords = _sNameFileNew.split("_", -1);
		if (lWords.length == 0) {
			BasicPrintMsg.error("The file name should be in the form 'email_count.csv'"
					+ "\nFile Name new= " + _sNameFileNew);
		}
		String lCountStr = lWords[lWords.length - 1].replaceAll(".csv", "");
		for (int lIdx = 0; lIdx < lCountStr.length(); lIdx++) {
			char lChar = lCountStr.charAt(lIdx);
			if (lChar < '0' || lChar > '9') {
				BasicPrintMsg.error("The fiel name is incorrect. The count should be a number"
						+ "\nFile Name new= " + _sNameFileNew);
			}
		}
		pNameFileNew = _sNameFileNew;
	}
	
	/*
	 * Getters & Setters
	 */
	public final LitUnFichierEnLignes getpReadFile() {
		return pReadFile;
	}
	public final int getpDate() {
		return pDate;
	}
	public final String getpDir() {
		return pDir;
	}
	public final TCClient getpTCClient() {
		return pTCClient;
	}
	public final void setpTCClient(TCClient pTCClient) {
		this.pTCClient = pTCClient;
	}
	public final TCAsset getpTCAssetMetal() {
		return pTCAssetMetal;
	}
	public final TCAsset getpTCAssetCurrency() {
		return pTCAssetCurrency;
	}
	public final TCManager getpTCManager() {
		return pTCManager;
	}
	public final TCFileType getpTCFileType() {
		return pTCFileType;
	}
	public final double getpQuantityMetal() {
		return pQuantityMetal;
	}
	public final double getpQuantityCurrency() {
		return pQuantityCurrency;
	}
	public final String getpNameFileNew() {
		return pNameFileNew;
	}
	public final String getpNameFile() {
		return pNameFile;
	}
	

	
	
}
