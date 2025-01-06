package treatclientscsvfiles.movefiles;

import treatclientscsvfiles.assets.TCCountManager;
import treatclientscsvfiles.clients.TCClientManager;
import treatclientscsvfiles.files.TCFileManager;

public class TCManager {

	public TCManager() {
		initiate();
	}
	
	/*
	 * Data
	 */
	private TCCreateDirs pTCCreateDirs;
	private TCClientManager pTCClientManager;
	private TCFileManager pTCFileManager;
	private TCFileActionManager pTCFileActionManager;
	private TCCountManager pTCCountManager;
	
	/**
	 * 
	 */
	private void initiate() {
		pTCCreateDirs = new TCCreateDirs();
		pTCClientManager = new TCClientManager(this);
		pTCFileManager = new TCFileManager(this);
		pTCFileActionManager = new TCFileActionManager(this);
		pTCCountManager = new TCCountManager(this);
	}
	
	/**
	 * 
	 */
	public final void run() {
		pTCCreateDirs.createDir();
		pTCClientManager.run();
		pTCFileManager.loadFiles();
		pTCFileActionManager.run();
		pTCCountManager.display();
	}

	/*
	 * Getters & Setters
	 */
	public final TCCreateDirs getpTCCreateDirs() {
		return pTCCreateDirs;
	}
	public final TCClientManager getpTCClientManager() {
		return pTCClientManager;
	}
	public final TCFileManager getpTCFileManager() {
		return pTCFileManager;
	}
	public final TCFileActionManager getpTCFileActionManager() {
		return pTCFileActionManager;
	}
	public final TCCountManager getpTCCountManager() {
		return pTCCountManager;
	}
	
}
