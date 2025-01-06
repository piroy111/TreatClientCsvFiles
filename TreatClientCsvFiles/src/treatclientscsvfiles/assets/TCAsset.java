package treatclientscsvfiles.assets;

import basicmethods.AMNumberTools;

public class TCAsset implements Comparable<TCAsset> {

	protected TCAsset(String _sNameBunker, String _sName, boolean _sIsQuantityInverted, double _sPriceStatic, double _sMargin) {
		pNameBunker = _sNameBunker;
		pName = _sName;
		pIsQuantityInverted = _sIsQuantityInverted;
		pPriceStatic = _sPriceStatic;
		pMargin = _sMargin;
		/*
		 * 
		 */
		pIsCurrency = !pName.startsWith("X");
		pQuantityToTrade = 0;
		pPriceUSD = pIsQuantityInverted ? 1 / _sPriceStatic : _sPriceStatic;
		pPriceUSDExec = 0;		
	}
	
	/*
	 * Data
	 */
	private String pNameBunker;
	private String pName;
	private double pQuantityToTrade;
	private boolean pIsQuantityInverted;
	private double pPriceStatic;
	private double pPriceUSDExec;
	private boolean pIsCurrency;
	private double pPriceUSD;
	private double  pMargin;
	
	/**
	 * 
	 * @param _sQuantity
	 */
	protected final void addQuantity(double _sQuantity, double _sPriceUSD) {
		if (pIsQuantityInverted) {
			pQuantityToTrade += -_sQuantity / pPriceStatic;
		} else {
			pQuantityToTrade += _sQuantity;
		}
		if (!pIsCurrency) {
			pPriceUSDExec = ((pQuantityToTrade - _sQuantity) * pPriceUSDExec + _sQuantity * _sPriceUSD);
			if (AMNumberTools.isNaNOrZero(pQuantityToTrade)) {
				pPriceUSDExec = -pPriceUSDExec; 
			} else {
				pPriceUSDExec = pPriceUSDExec / pQuantityToTrade;
			}
		}
	}
	
	@Override public int compareTo(TCAsset _sTCAsset) {
		if (!pIsCurrency && _sTCAsset.getpIsCurrency()) {
			return -1;
		} else if (pIsCurrency && !_sTCAsset.getpIsCurrency()) {
			return 1;
		} else {
			return pName.compareTo(_sTCAsset.getpName());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public final double getpPriceUSDExecClient() {
		return pPriceUSDExec / (1 + pMargin);
	}
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return pName;
	}

	/*
	 * Getters & Setters
	 */
	public final String getpName() {
		return pName;
	}
	public final String getpNameBunker() {
		return pNameBunker;
	}
	public final double getpQuantityToTrade() {
		return pQuantityToTrade;
	}
	public final boolean getpIsQuantityInverted() {
		return pIsQuantityInverted;
	}
	public final boolean getpIsCurrency() {
		return pIsCurrency;
	}
	public final double getpPriceUSD() {
		return pPriceUSD;
	}
	public final double getpPriceUSDExec() {
		return pPriceUSDExec;
	}

	public final double getpMargin() {
		return pMargin;
	}

	
	
	
	
	
}
