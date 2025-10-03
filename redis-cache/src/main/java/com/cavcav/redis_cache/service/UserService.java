package com.cavcav.redis_cache.service;


import com.cavcav.redis_cache.dto.CreateUserDto;
import com.cavcav.redis_cache.dto.UpdateUserDto;
import com.cavcav.redis_cache.model.User;
import com.cavcav.redis_cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public User createUser(CreateUserDto user) {
        var entity = userRepository.save(user.toEntity(user));

        return entity;
    }

    @Cacheable(value = "users", key = "#root.methodName", unless = "#result==null")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "user_id", key = "#root.methodName + #id", unless = "#result==null")
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @CachePut(cacheNames = "user_id", key = "'getUserById'+#dto.id", unless = "result==null")

    public User updateUser(UpdateUserDto dto) {

        Optional<User> updateUser = userRepository.findById(dto.getId());
        if (updateUser.isPresent()) {
            User entity = updateUser.get();
            entity.setPassword(dto.getPassword());
            return userRepository.save(entity);
        }

        return null;
    }

    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}
