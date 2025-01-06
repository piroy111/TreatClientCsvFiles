package changeemailaddress;

import changeemailaddress.managers.CEAManager;

public class STL_ChangeEmailAddress_LaunchMe {

	public static void main(String[] _sArgs) {
		/*
		 * Data
		 */
		String lEmailOld = "Dimitri.Mongeot@natwestmarkets.com";
		String lEmailNew = "dimitri.mongeot@gmail.com";
		/*
		 * Launch
		 */
		new CEAManager().run(lEmailOld, lEmailNew);
	}

	
}
