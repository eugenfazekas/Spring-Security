package com.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

import com.entitys.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities().stream()
                   .map(a -> new SimpleGrantedAuthority(a.getName()))
                   .collect(Collectors.toList());
    }

	@Override
	public String getPassword() {
	
		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public final User getUser() {
        return user;
    }
}
