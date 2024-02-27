package com.epam.service;

import com.epam.dtos.SubscriptionRequestDto;
import com.epam.dtos.SubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponseDto createSubscription(SubscriptionRequestDto requestDto);

    SubscriptionResponseDto updateSubscription(SubscriptionRequestDto requestDto);

    void deleteSubscription(Long id);

    SubscriptionResponseDto getSubscription(Long id);

    List<SubscriptionResponseDto> getAllSubscription();
}
