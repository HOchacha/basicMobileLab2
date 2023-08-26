package org.mse.basicmobilelab2.security.service;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.repository.UserCollectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    UserCollectionRepo userCollectionRepo;
    @Autowired
    public UserDetailsServiceImpl(UserCollectionRepo userCollectionRepo) {
        this.userCollectionRepo = userCollectionRepo;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info(username);
        User user = userCollectionRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        log.info(user);
        return UserDetailsImpl.build(user);
    }
}
