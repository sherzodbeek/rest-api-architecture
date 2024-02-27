package com.epam.controller;

import com.epam.dtos.SubscriptionRequestDto;
import com.epam.dtos.SubscriptionResponseDto;
import com.epam.dtos.UserResponseDto;
import com.epam.service.SubscriptionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    public SubscriptionController(SubscriptionServiceImpl subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Create new Subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "New Subscription has been created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with given ID",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@RequestBody SubscriptionRequestDto requestDto) {
        SubscriptionResponseDto subscription = subscriptionService.createSubscription(requestDto);
        applyHateoas(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    @Operation(summary = "Update Subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Subscription or User not found with give ID",
                    content = @Content)})
    @PutMapping
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@RequestBody SubscriptionRequestDto requestDto) {
        SubscriptionResponseDto subscription = subscriptionService.updateSubscription(requestDto);
        applyHateoas(subscription);
        return ResponseEntity.ok(subscription);
    }

    @Operation(summary = "Delete Subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Subscription not found with give ID",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteSubscription(@PathVariable("id") Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Subscription with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription Data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Subscription not found with give ID",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable("id") Long id) {
        SubscriptionResponseDto subscription = subscriptionService.getSubscription(id);
        applyHateoas(subscription);
        return ResponseEntity.ok(subscription);
    }

    @Operation(summary = "Get All Subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Subscriptions",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SubscriptionResponseDto.class)))})
    })
    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription() {
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getAllSubscription();
        subscriptions.forEach(this::applyHateoas);
        return ResponseEntity.ok(subscriptions);
    }

    private void applyHateoas(SubscriptionResponseDto subscription) {
        subscription.add(linkTo(methodOn(SubscriptionController.class)
                .getSubscription(subscription.getId())).withSelfRel());
        subscription.add(linkTo(methodOn(SubscriptionController.class)
                .getAllSubscription()).withRel(IanaLinkRelations.COLLECTION));
        subscription.add(linkTo(methodOn(UserController.class)
                .getUser(subscription.getUserId())).withRel("user"));
    }
}
