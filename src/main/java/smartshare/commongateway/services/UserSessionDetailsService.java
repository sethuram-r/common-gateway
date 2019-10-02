package smartshare.commongateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smartshare.commongateway.model.CustomUserDetail;
import smartshare.commongateway.model.redis.UserSessionDetail;
import smartshare.commongateway.repository.UserSessionRepository;


@Service
public class UserSessionDetailsService implements UserDetailsService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public CustomUserDetail loadUserByUsername(String userName) {
        System.out.println(userSessionRepository.findByUserName(userName));

        UserSessionDetail user = userSessionRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new CustomUserDetail(user);
    }

    // I can add method to save details here

    public UserSessionDetail saveUserSessionDetail(UserSessionDetail userSessionDetail) {
        return userSessionRepository.save(userSessionDetail);
    }

}
