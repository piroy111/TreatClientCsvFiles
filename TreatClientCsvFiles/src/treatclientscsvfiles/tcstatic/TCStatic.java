package treatclientscsvfiles.tcstatic;

import staticdata.StaticDir;

public class TCStatic {

	/*
	 * Drive
	 */
	private static String DRIVE = StaticDir.getDRIVE();
	private static String DIR = DRIVE + "Bunker_Clients_Orders_To_Hedge/";
	private static String DIR_OUTPUT_TRANSACTIONS = DRIVE + "BUNKER_V2/11_COMPTA/01_Import_csv/02_Clients_purchases_and_sell/";
	private static boolean IS_TOUCH_FILES = true;
	private static String DIR_OUTPUT_DELIVERY = DRIVE + "BUNKER_V2/11_COMPTA/01_Import_csv/12_Bars_delivery/";
	/*
	 * Data
	 */
	public static double XAU;
	public static double XAG;
	public static double XPT;
	public static double USDSGD;
	public static double EURUSD;
	public static double GBPUSD;
	/*
	 * Margin
	 */
	private static double MARGIN_GOLD = 0.01;
	private static double MARGIN_SILVER = 0.06;
	private static double MARGIN_PLATINUM = 0.03;
	/*
	 * ENUM
	 */
	public enum TCFileType {Delivery, Transaction}
	
	/*
	 * Getters & Setters
	 */
	public static final String getDIR() {
		return DIR;
	}
	public static final boolean getIS_TOUCH_FILES() {
		return IS_TOUCH_FILES;
	}
	public static final String getDRIVE() {
		return DRIVE;
	}
	public static final double getXAU() {
		return XAU;
	}
	public static final double getXAG() {
		return XAG;
	}
	public static final double getXPT() {
		return XPT;
	}
	public static final double getUSDSGD() {
		return USDSGD;
	}
	public static final double getEURUSD() {
		return EURUSD;
	}
	public static final double getMARGIN_GOLD() {
		return MARGIN_GOLD;
	}
	public static final double getMARGIN_SILVER() {
		return MARGIN_SILVER;
	}
	public static final double getMARGIN_PLATINUM() {
		return MARGIN_PLATINUM;
	}
	public static final double getGBPUSD() {
		return GBPUSD;
	}
	public static final String getDIR_OUTPUT_DELIVERY() {
		return DIR_OUTPUT_DELIVERY;
	}
	public static final String getDIR_OUTPUT_TRANSACTIONS() {
		return DIR_OUTPUT_TRANSACTIONS;
	} 
	
	
	
}
