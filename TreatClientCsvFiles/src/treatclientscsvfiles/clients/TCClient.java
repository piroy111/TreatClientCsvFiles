package treatclientscsvfiles.clients;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;
import treatclientscsvfiles.files.TCFile;

public class TCClient {

	protected TCClient(String _sEmail) {
		pEmail = _sEmail;
		/*
		 * 
		 */
		if (pEmail.contains(".csv")) {
			BasicPrintMsg.error("The email should not contain '.csv'; Email= " + _sEmail);
		}
		pCount = -1;
		pListTCFile = new ArrayList<TCFile>();
	}
	
	/*
	 * Data
	 */
	private int pCount;
	private String pEmail;
	private List<TCFile> pListTCFile;
	
	/**
	 * 
	 * @param _sTCFile
	 */
	public final void addNewTCFile(TCFile _sTCFile) {
		pListTCFile.add(_sTCFile);
	}
	
	/**
	 * Says if the TCFile is already in the list
	 * @param _sTCFile
	 * @return
	 */
	public final boolean isNewTCFile(TCFile _sTCFile) {
		for (TCFile lTCFile : pListTCFile) {
			if (lTCFile.getpIsEquals(_sTCFile)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	public final void addNewCount() {
		pCount++;
	}
	
	/*
	 * Getters & Setters
	 */
	public final int getpCount() {
		return pCount;
	}
	public final String getpEmail() {
		return pEmail;
	}
	public final void setpCount(int pCount) {
		this.pCount = pCount;
	}
	
}
