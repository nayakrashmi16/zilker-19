package io.ztech.expensesapp.services;

import java.util.regex.Pattern;

//=========================================================================================================================================
//VALIDATOR CLASS - VALIDATES CORRECTNESS OF INPUT
//=========================================================================================================================================

public class Validator {
	// =====================================================
	// VALIDATES GIVEN INPUT WITH RESPECTIVE REGEX
	// =====================================================
	public boolean isValidated(String input, String regex) {
		Pattern pat = Pattern.compile(regex);
		if (input != null) {
			return pat.matcher(input).matches();
		}
		return false;

	}
}
