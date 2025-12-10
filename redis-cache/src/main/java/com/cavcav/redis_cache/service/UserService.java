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

    // Clears all 'users' and 'user_id' caches to prevent stale data after creating a new user.
    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public User createUser(CreateUserDto user) {
        var entity = userRepository.save(user.toEntity(user));

        return entity;
    }
    // Sonuç null olmadığı sürece getAll() metodunun çıktısını 'users' cache'inde saklar ve tekrarlayan DB sorgularını önler.
    //Caches the result of getAll() under 'users' to avoid repeated database queries unless the result is null.
    @Cacheable(value = "users", key = "#root.methodName", unless = "#result==null")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "user_id", key = "#root.methodName + #id", unless = "#result==null")
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // After updating the user, stores the new User object in the 'user_id' cache using the user's ID as the key;
    // skips caching if the result is null.

    @CachePut(cacheNames = "user_id", key = "'getUserById' + #dto.id", unless = "#result == null")
    public User updateUser(UpdateUserDto dto) {
        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setPassword(dto.getPassword());
            return userRepository.save(user1);
        } else {
            return null;
        }
    }

    // Clears all entries in 'users' and 'user_id' caches after a user is deleted to prevent serving stale data.
    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User deleted";
    }
}
