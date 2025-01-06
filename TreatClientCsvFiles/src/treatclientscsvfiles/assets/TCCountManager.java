package treatclientscsvfiles.assets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.AMNumberTools;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicTime;
import staticdata.StaticAsset;
import treatclientscsvfiles.files.TCFile;
import treatclientscsvfiles.movefiles.TCManager;
import treatclientscsvfiles.tcstatic.TCStatic;
import treatclientscsvfiles.tcstatic.TCStatic.TCFileType;

public class TCCountManager {

	public TCCountManager(TCManager _sTCManager) {
		initiate();
		/*
		 * 
		 */
		pTCManager = _sTCManager;
	}


	/*
	 * Data
	 */
	private Map<String, TCAsset> pMapNameBunkerToTCAsset;
	private List<TCAsset> pListTCAsset;
	private TCManager pTCManager;

	/**
	 * 
	 */
	private void initiate() {
		pMapNameBunkerToTCAsset = new HashMap<>();
		pListTCAsset = new ArrayList<TCAsset>();
	}

	/**
	 * 
	 */
	public final void display() {
		BasicPrintMsg.displayTitle(this, "Quantity to trade per asset");
		Collections.sort(pListTCAsset);
		for (TCAsset lTCAsset : pListTCAsset) {
			int lQuantity = AMNumberTools.ceil(lTCAsset.getpQuantityToTrade());
			String lWayStr = lQuantity > 0 ? "BUY" : "SELL";
			System.out.print(BasicPrintMsg.getJustifiedText(lWayStr, 8) 
					+ BasicPrintMsg.getJustifiedText(lTCAsset.getpName(), 23 - (lTCAsset.getpIsCurrency() ? 13 : 0))
					+ " " 
					+ BasicPrintMsg.afficheIntegerWithComma(lQuantity));
			if (!lTCAsset.getpIsCurrency() && lTCAsset.getpQuantityToTrade() > 0) {
				System.out.print(" @ " + BasicPrintMsg.afficheDouble(lTCAsset.getpPriceUSDExecClient(), 2));
				double lPerfExec = lTCAsset.getpPriceUSDExecClient() / lTCAsset.getpPriceUSD() - 1;
				System.out.print("  ->  Exec= " + BasicPrintMsg.affichePourcentage(lPerfExec, 2));
			}
			System.out.println();
			System.out.println();
		}
		/*
		 * Communicate if there is no file to delete
		 */
		if (pTCManager.getpTCFileManager().getpListTCFileToDelete().size() == 0) {
			BasicTime.sleep(50);
			System.err.println("Careful !");
			System.err.println("There was no file which was already done. Hence, may be we may have missed a file exec");
			System.err.println("Careful !");
		}
	}

	/**
	 * Pass the quantity and the price
	 * @param _sReadFile
	 */
	public final void addNewFile(TCFile _sTCFile) {
		if (_sTCFile.getpTCFileType() == TCFileType.Transaction) {
			/*
			 * Load
			 */
			TCAsset lTCAssetCurrency = _sTCFile.getpTCAssetCurrency();
			TCAsset lTCAssetMetal = _sTCFile.getpTCAssetMetal();
			double lQuantityCurrency = _sTCFile.getpQuantityCurrency();
			double lQuantityMetal = _sTCFile.getpQuantityMetal();
			/*
			 * Compute addition for currency
			 */
			lTCAssetCurrency.addQuantity(lQuantityCurrency, Double.NaN);
			/*
			 * Compute addition for metal
			 */
			double lPriceUSD = -lQuantityCurrency * _sTCFile.getpTCAssetCurrency().getpPriceUSD() / lQuantityMetal;
			lTCAssetMetal.addQuantity(_sTCFile.getpQuantityMetal(), lPriceUSD);
		}
	}

	/**
	 * 
	 * @param _sTCAssetRef
	 * @param _sTCAssetNew
	 * @return
	 */
	public TCAsset getpAndCheckTCAsset(TCAsset _sTCAssetRef, TCAsset _sTCAssetNew) {
		if (_sTCAssetRef != null && !_sTCAssetRef.equals(_sTCAssetNew)) {
			BasicPrintMsg.error("Error");
		}
		return _sTCAssetNew;
	}

	/**
	 * Classic get or create + go take the price in the static data + the name
	 * @param _sNameBunker
	 * @return
	 */
	public final TCAsset getpOrCreateTCAsset(String _sNameBunker) {
		TCAsset lTCAsset = pMapNameBunkerToTCAsset.get(_sNameBunker);
		if (lTCAsset == null) {
			/*
			 * Initiate
			 */
			String lName = null;
			double lPrice = Double.NaN;
			boolean lIsQuantityInverted = false;
			double lMargin = Double.NaN;
			/*
			 * Fill static data
			 */
			if (_sNameBunker.equals("GOLD BAR OZ")) {
				lName = "XAU";
				lPrice = TCStatic.XAU;
				lMargin = TCStatic.getMARGIN_GOLD();
			} else if (_sNameBunker.equals("SILVER BAR OZ")) {
				lName = "XAG";
				lPrice = TCStatic.XAG;
				lMargin = TCStatic.getMARGIN_SILVER();
			} else if (_sNameBunker.equals("PLATINUM BAR OZ")) {
				lName = "XPT";
				lPrice = TCStatic.XPT;
				lMargin = TCStatic.getMARGIN_PLATINUM();
			} else if (_sNameBunker.equals("EUR")) {
				lName = "EURUSD";
				lPrice = TCStatic.EURUSD;
			} else if (_sNameBunker.equals("SGD")) {
				lName = "USDSGD";
				lPrice = TCStatic.USDSGD;
				lIsQuantityInverted = true;
			} else if (_sNameBunker.equals("GBP")) {
				lName = "GBPUSD";
				lPrice = TCStatic.GBPUSD;
			} else if (_sNameBunker.equals(StaticAsset.getOIL())) {
				BasicPrintMsg.error("Clients should not trade BCO");
			} else if (_sNameBunker.equals("USD")) {
				lName = "USD";
				lPrice = 1;
			} else {
				BasicPrintMsg.error("Asset unknown; _sNameBunker= " + _sNameBunker);
			}
			/*
			 * Create
			 */
			lTCAsset = new TCAsset(_sNameBunker, lName, lIsQuantityInverted, lPrice, lMargin);
			pMapNameBunkerToTCAsset.put(_sNameBunker, lTCAsset);
			pListTCAsset.add(lTCAsset);
		}
		return lTCAsset;
	}

	/*
	 * Getters & Setters
	 */
	public final Map<String, TCAsset> getpMapNameBunkerToTCAsset() {
		return pMapNameBunkerToTCAsset;
	}
	public final List<TCAsset> getpListTCAsset() {
		return pListTCAsset;
	}






}
