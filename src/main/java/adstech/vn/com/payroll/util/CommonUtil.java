package adstech.vn.com.payroll.util;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {
	public static String getUserName() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			return currentPrincipalName;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Boolean isNull(List<?> objects) {
		return objects == null || objects.size() == 0;
	}
}
