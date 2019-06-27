package br.com.davsantos.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.davsantos.security.User;

public class UserS {

	public static User authenticate() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
