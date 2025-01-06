package treatclientscsvfiles;

import treatclientscsvfiles.movefiles.TCManager;
import treatclientscsvfiles.tcstatic.TCStatic;

class STL_Treat_clients_executions_LaunchMe {

	public static void main(String[] _sArgs) {
		/*
		 * PUT THE CURRENT PRICES MANUALLY ******************************************************************
		 */
		TCStatic.XAU = 1844.21;
		TCStatic.XAG = 22.34;
		TCStatic.XPT = 893;
		TCStatic.EURUSD = 1.1217;
		TCStatic.GBPUSD = 1.3939;
		TCStatic.USDSGD = 1.2402;
		
		/*
		 * **************************************************************************************************
		 */
		/*
		 * Launch program
		 */
		new TCManager().run();
	}
	
	
	
}
