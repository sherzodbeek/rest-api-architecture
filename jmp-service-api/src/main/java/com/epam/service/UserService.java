package com.epam.service;

import com.epam.dtos.UserRequestDto;
import com.epam.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto requestDto);

    UserResponseDto updateUser(UserRequestDto requestDto);

    void deleteUser(Long id);

    UserResponseDto getUser(Long id);

    List<UserResponseDto> getAllUser();
}
