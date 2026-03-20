package com.YashIngale.JournalApp.Controller;

import com.YashIngale.JournalApp.Entity.UserEntity;
import com.YashIngale.JournalApp.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServices userServices;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUser(){
        List<UserEntity> users = userServices.getAllUser();
        if(users!= null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-new-admin")
    public ResponseEntity<?> createNewAdmin(@RequestBody UserEntity userEntity){
        try{
            if(userEntity.getUserName()!=null || userEntity.getUserName().isEmpty()) {
                userServices.saveNewAdmin(userEntity);
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
