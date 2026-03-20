package com.YashIngale.JournalApp.Service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServices  {

   @Autowired
    UserRepository userRepository;

//If the Implements here then
//   @Override
//   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       UserEntity user = userRepository.findByuserName(username);
//       if (user == null) {
//           throw new UsernameNotFoundException("User not found: " + username);
//       }
//       return org.springframework.security.core.userdetails.User.builder()
//               .username(user.getUserName())
//               .password(user.getPassword())
////               .roles(user.getRoles().toArray(new String[0])) // assumes getRoles() exists
//               .build();
//   }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
   public void saveNewUser(UserEntity userEntity){
       try{
           userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

       userEntity.setRoles(Arrays.asList("USER"));
       userRepository.save(userEntity);
       }
       catch (Exception e){
           log.error("Error occured for {}",userEntity.getUserName(),e);
       }
   }

    public void SaveUser(UserEntity userEntity) {

        userRepository.save(userEntity);
    }
   public List<UserEntity> getAllUser(){
       return userRepository.findAll();
   }
   public Optional<UserEntity> getById(ObjectId id){
       return userRepository.findById(id);
   }
   public void deleteUser(ObjectId id){
       userRepository.deleteById(id);
   }
    public void deleteUserByUserName(String userName){
        userRepository.deleteByUserName(userName);
    }
   public UserEntity findByuserName(String userName){
       return userRepository.findByUserName(userName);

   }


    public void saveNewAdmin(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(userEntity);
    }
}
