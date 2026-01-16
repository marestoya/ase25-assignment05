package de.unibayreuth.se.taskboard.business.impl;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserPersistenceService;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserPersistenceService userPersistenceService;

    @Override
   public List<User> getAllUsers() {
        return userPersistenceService.getAll();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userPersistenceService.getById(id);
    }

    @Override
    public User createUser(User user) {
        return userPersistenceService.upsert(user);
    }

    @Override
    public void clear() {
        userPersistenceService.clear();
    }

  
}