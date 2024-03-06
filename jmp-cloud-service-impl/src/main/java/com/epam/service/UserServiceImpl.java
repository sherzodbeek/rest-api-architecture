package com.epam.service;

import com.epam.dtos.UserRequestDto;
import com.epam.dtos.UserResponseDto;
import com.epam.entity.User;
import com.epam.exception.NotFoundException;
import com.epam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = User.builder().
                name(requestDto.name())
                .surname(requestDto.surname())
                .birthday(LocalDate.parse(requestDto.birthday()))
                .build();

        User savedUser = userRepository.save(user);

        return mapUserResponseToUser(savedUser);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.id())
                .orElseThrow(() -> new NotFoundException("User not found with given id: " + requestDto.id()));
        user.setName(requestDto.name());
        user.setSurname(requestDto.surname());
        user.setBirthday(LocalDate.parse(requestDto.birthday()));

        User savedUser = userRepository.save(user);

        return mapUserResponseToUser(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with given id: " + id));

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with given id: " + id));

        return mapUserResponseToUser(user);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.parallelStream()
                .map(UserServiceImpl::mapUserResponseToUser)
                .toList();
    }

    private static UserResponseDto mapUserResponseToUser(User user) {
        return new UserResponseDto(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getBirthday().toString());
    }
}
