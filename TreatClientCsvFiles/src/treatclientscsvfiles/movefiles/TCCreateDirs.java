package treatclientscsvfiles.movefiles;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiers;
import treatclientscsvfiles.tcstatic.TCStatic;

public class TCCreateDirs {

	/*
	 * Data
	 */
	private List<String> pListDir;
	
	/**
	 * 
	 */
	public final void createDir() {
		String lDir = TCStatic.getDIR();
		pListDir = new ArrayList<String>();
		for (int lIdx = 0; lIdx < 21; lIdx++) {
			String lNewDir;
			if (lIdx < 10) {
				lNewDir = lDir + "ClientsOrders_0" + lIdx + "/";
			} else {
				lNewDir = lDir + "ClientsOrders_" + lIdx + "/";
			}
			pListDir.add(lNewDir);
			BasicFichiers.getOrCreateDirectory(lNewDir);
		}
	}

	/*
	 * Getters & Setters
	 */
	public final List<String> getpListDir() {
		return pListDir;
	}
	
}
