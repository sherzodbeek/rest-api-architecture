package com.epam.dtos;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {

    private Long id;

    private Long userId;

    private String startDate;
}
