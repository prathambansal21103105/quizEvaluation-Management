package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.PlayerService;
import com.quiz.layoutPDF.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createPlayer(@RequestBody Player player) {
        Long playerId = playerService.createPlayer(player);
        return new ResponseEntity<>("Player successfully added with ID: " + playerId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        if(player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Long id) {
        Boolean deleted = playerService.deletePlayer(id);
        if(deleted) {
            return new ResponseEntity<>("Player successfully removed", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Player couldn't be removed", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        Boolean updated = playerService.updatePlayer(id,player);
        if(updated) {
            return new ResponseEntity<>("Player successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Player not updated", HttpStatus.NOT_FOUND);
    }

}
