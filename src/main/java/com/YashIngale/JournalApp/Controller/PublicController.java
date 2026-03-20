package com.YashIngale.JournalApp.Controller;

import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserServices userServices;
    @PostMapping("/add-user")
    public ResponseEntity<?> postUser(@RequestBody UserEntity userEntity){
        try{
            if(userEntity.getUserName()!=null || userEntity.getUserName().isEmpty()) {
                userServices.saveNewUser(userEntity);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
