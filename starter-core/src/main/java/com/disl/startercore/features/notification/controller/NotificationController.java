package com.disl.startercore.features.notification.controller;

import com.disl.commons.enums.AscOrDescType;
import com.disl.commons.exceptions.ResponseException;
import com.disl.commons.payloads.PaginationArgs;
import com.disl.commons.payloads.Response;
import com.disl.startercore.entities.User;
import com.disl.startercore.features.notification.entity.Notification;
import com.disl.startercore.features.notification.model.SendNotificationRequest;
import com.disl.startercore.features.notification.service.NotificationService;
import com.disl.startercore.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.disl.commons.constants.CommonConstants.*;

@RestController
@RequestMapping("/api/notification")
class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "get unread notification counter", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Long.class)), responseCode = "200")
    @GetMapping("/unread-counter")
    public ResponseEntity<Response> getUnreadNotificationCounter() {
        return Response.getResponseEntity(
                true, "Unread notification counter loaded",
                notificationService.getUnreadCounter()
        );
    }

    @Operation(summary = "unread notification mark as read", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Boolean.class)), responseCode = "200")
    @PostMapping("/mark-as-read")
    public ResponseEntity<Response> markNotificationAsRead() {
        boolean isMarkedAsRead = notificationService.markNotificationAsRead();
        String responseMsg = isMarkedAsRead ? "Notification mark as read updated" : "Mark as read already updated";
        return Response.getResponseEntity(isMarkedAsRead, responseMsg, isMarkedAsRead);
    }

    @Operation(summary = "get all notification", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Notification.class))), responseCode = "200")
    @GetMapping("/all")
    public ResponseEntity<Response> getAllNotificationByUserType(
            @RequestParam(name = PAGE_NO, defaultValue = DEFAULT_PAGE_NO) int pageNo,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = ASC_OR_DESC, defaultValue = DEFAULT_ASC_OR_DESC) AscOrDescType ascOrDesc
    ) {
        PaginationArgs paginationArgs = new PaginationArgs(
                pageNo, pageSize, sortBy, ascOrDesc
        );

        return Response.getResponseEntity(
                true, "All notification loaded",
                notificationService.getAllNotificationPaginated(paginationArgs)
        );
    }

    @Operation(summary = "create and send push notification", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)), responseCode = "200")
    @PreAuthorize("hasAuthority('NOTIFICATION_SEND')")
    @PostMapping("/create-and-push")
    public ResponseEntity<Response> Create(@Valid @RequestBody SendNotificationRequest notificationRequest) {
        User sender = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(sender == null) {
            throw new ResponseException("No sender found");
        }

        notificationService.createAndSendNotification(notificationRequest, sender);
        return Response.getResponseEntity(true, "Notification created and sent successfully!");
    }
}
