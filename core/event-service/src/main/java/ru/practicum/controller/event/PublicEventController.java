package ru.practicum.controller.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.annotation.LogAllMethods;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventPublicParam;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.service.event.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
@Slf4j
@RequiredArgsConstructor
@Validated
@LogAllMethods
public class PublicEventController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findPublicEvents(
            @Valid @ModelAttribute EventPublicParam params) {
        return eventService.findPublicEvents(params);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findPublicEventById(
            @Positive(message = "eventId должен быть больше 0")
            @PathVariable
            Long id,
            @RequestHeader("X-EWM-USER-ID")
            Long userId
    ) {
        return eventService.findPublicEventById(id, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findUserRecommendations(
            @RequestHeader("X-EWM-USER-ID") Long userId,
            @RequestParam(defaultValue = "10")
            @Positive(message = "size должен быть больше 0")
            Integer size
    ) {
        return eventService.findUserRecommendations(userId, size);
    }

    @PutMapping("/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public void addLikeToEvent(
            @Positive(message = "eventId должен быть больше 0")
            @PathVariable
            Long id,
            @RequestHeader("X-EWM-USER-ID")
            Long userId
    ) {
        eventService.addLikeToEvent(id, userId);
    }

}
