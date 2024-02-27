package com.epam.service;

import com.epam.dtos.SubscriptionRequestDto;
import com.epam.dtos.SubscriptionResponseDto;
import com.epam.entity.Subscription;
import com.epam.entity.User;
import com.epam.exception.NotFoundException;
import com.epam.repository.SubscriptionRepository;
import com.epam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto requestDto) {
        User user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new NotFoundException("User not found with given id: " + requestDto.userId()));

        Subscription subscription = Subscription.builder()
                .user(user)
                .startDate(LocalDate.now())
                .build();

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return mapSubscriptionResponseToSubscription(savedSubscription);
    }

    @Override
    public SubscriptionResponseDto updateSubscription(SubscriptionRequestDto requestDto) {
        User user = userRepository.findById(requestDto.id())
                .orElseThrow(() -> new NotFoundException("User not found with given id: " + requestDto.userId()));

        Subscription subscription = subscriptionRepository.findById(requestDto.id())
                .orElseThrow(() -> new NotFoundException("Subscription not found with given id: " + requestDto.userId()));

        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return mapSubscriptionResponseToSubscription(savedSubscription);
    }

    @Override
    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found with given id: " + id));

        subscriptionRepository.delete(subscription);
    }

    @Override
    public SubscriptionResponseDto getSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found with given id: " + id));

        return mapSubscriptionResponseToSubscription(subscription);
    }

    @Override
    public List<SubscriptionResponseDto> getAllSubscription() {
        return subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionServiceImpl::mapSubscriptionResponseToSubscription)
                .toList();
    }

    private static SubscriptionResponseDto mapSubscriptionResponseToSubscription(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getUser().getId(),
                subscription.getStartDate().toString()
        );
    }
}
