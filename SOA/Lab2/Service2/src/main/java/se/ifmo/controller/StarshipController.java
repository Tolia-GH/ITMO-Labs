package se.ifmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.ifmo.model.SuccessResponse;
import se.ifmo.service.StarshipService;

@RestController
@RequestMapping("v1/starship")
public class StarshipController {
    @Autowired
    private StarshipService starshipService;

    // 卸载特定 SpaceMarine
    @GetMapping(value = "/{starship-id}/unload/space-marine-id", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public SuccessResponse unloadSpaceMarineById(
            @PathVariable("starship-id") long starshipId,
            @RequestParam("space-marine-id") long spaceMarineId) {
        return starshipService.unloadSpaceMarineById(starshipId, spaceMarineId);
    }

    // 卸载所有 SpaceMarine
    @GetMapping(value = "/{starship-id}/unload-all", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public SuccessResponse unloadAllSpaceMarines(@PathVariable("starship-id") long starshipId) {
        return starshipService.unloadAllSpaceMarines(starshipId);
    }
}
