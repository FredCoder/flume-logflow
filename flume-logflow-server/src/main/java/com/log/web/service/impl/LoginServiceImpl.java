package com.log.web.service.impl;

import java.util.Enumeration;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.log.web.service.ILoginService;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements ILoginService {

	@SuppressWarnings("finally")
	@Override
	public boolean userLogin(String username, String password) {
		boolean result = false;
		try {
			Resource resource = new ClassPathResource("/user.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);

			Enumeration<Object> userEnum = props.elements();
			while (userEnum.hasMoreElements()) {
				String strKey = (String) userEnum.nextElement();
				String strValue = props.getProperty(strKey);
				if (strKey.equals(username) && strValue.equals(password)) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

}
