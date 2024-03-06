package com.epam.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private Long id;

    private String name;

    private String surname;

    private String birthday;
}
