package se.ifmo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.ifmo.model.ErrorResponse;
import se.ifmo.model.SuccessResponse;
import se.ifmo.service.StarshipService;

@RestController
@RequestMapping("api/v1/starship")
public class StarshipController {
    @Autowired
    private StarshipService starshipService;

    // 卸载特定 SpaceMarine
    @GetMapping(value = "/{starship-id}/unload/space-marine-id", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<?> unloadSpaceMarineById(
            @PathVariable("starship-id") long starshipId,
            @RequestParam("spaceMarineId") long spaceMarineId) {
        return starshipService.unloadSpaceMarineById(starshipId, spaceMarineId)?
                ResponseEntity.ok(new SuccessResponse(200, "Selected SpaceMarine unloaded from given spaceship")) :
                ResponseEntity.status(500).body(new ErrorResponse(500, "Something wrong"));
    }

    // 卸载所有 SpaceMarine
    @GetMapping(value = "/{starship-id}/unload-all", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<?> unloadAllSpaceMarines(@PathVariable("starship-id") long starshipId) {
        return starshipService.unloadAllSpaceMarines(starshipId)?
                ResponseEntity.ok(new SuccessResponse(200, "All SpaceMarines unloaded")) :
                ResponseEntity.status(500).body(new ErrorResponse(500, "Something wrong"));
    }
}
