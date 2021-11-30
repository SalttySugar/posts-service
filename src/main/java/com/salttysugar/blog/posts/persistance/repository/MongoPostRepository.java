package com.salttysugar.blog.posts.persistance.repository;

import com.salttysugar.blog.posts.persistance.model.MongoPost;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoPostRepository extends ReactiveMongoRepository<MongoPost, String> {
}
