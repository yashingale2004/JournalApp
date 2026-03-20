package com.YashIngale.JournalApp.Repository;

import com.YashIngale.JournalApp.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
UserEntity findByUserName(String userName);

    void deleteByUserName(String userName);
}
