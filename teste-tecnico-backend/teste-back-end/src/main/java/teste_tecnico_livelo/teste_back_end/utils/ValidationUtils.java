package teste_tecnico_livelo.teste_back_end.utils;

import java.util.List;

public class ValidationUtils {

	public Boolean isNullOrEmpty(String str) {
		if (str == null || str.equals("")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean isNull(Integer i) {
		if (i == null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean isNull(Object obj) {
		if (obj == null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean isNullOrEmpty(List<?> listObj) {
		if (listObj == null || listObj.size() == 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
