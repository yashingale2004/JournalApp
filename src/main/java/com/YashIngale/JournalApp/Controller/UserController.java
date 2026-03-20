package com.YashIngale.JournalApp.Controller;

import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServices userServices;

//    @GetMapping
//    public ResponseEntity<?> getAllUser(){
//        try{
//            List<UserEntity> userEntities =userServices.getAllUser();
//            if(userEntities.isEmpty()==true){
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            else {
//                return new ResponseEntity<>(userEntities,HttpStatus.OK);
//            }
//        }
//        catch (Exception e){
//            System.out.println(e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //in Public controller

//    @PostMapping
//    public ResponseEntity<?> postUser(@RequestBody UserEntity userEntity){
//        try{
//            if(userEntity.getUserName()!=null || userEntity.getUserName().isEmpty()) {
//                userServices.saveUser(userEntity);
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            else{
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//        catch (Exception e){
//            System.out.println(e.toString());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

//    @GetMapping("getById/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){
//        try {
//             Optional<UserEntity> user =userServices.getById(id);
//             return new ResponseEntity<>(user,HttpStatus.OK);
//
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PutMapping
    public ResponseEntity<?> putUserById(@RequestBody UserEntity userEntity) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();

            UserEntity oldUserEntity = userServices.findByuserName(userName);

                oldUserEntity.setUserName(userEntity.getUserName());
                oldUserEntity.setPassword(userEntity.getPassword());
                userServices.saveNewUser(oldUserEntity);

            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {

            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            userServices.deleteUserByUserName(userName);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
