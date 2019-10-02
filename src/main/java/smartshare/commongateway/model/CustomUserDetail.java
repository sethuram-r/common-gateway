package smartshare.commongateway.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smartshare.commongateway.model.redis.UserSessionDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private UserSessionDetail userSessionDetail;

    public CustomUserDetail(UserSessionDetail userSessionDetail) {
        this.userSessionDetail = userSessionDetail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userSessionDetail.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public String getSessionId() {
        return userSessionDetail.getSessionId();
    }

    @Override
    public String getUsername() {
        return userSessionDetail.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
