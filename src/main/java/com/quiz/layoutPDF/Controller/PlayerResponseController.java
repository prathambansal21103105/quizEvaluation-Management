package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Service.PlayerResponseService;
import com.quiz.layoutPDF.models.PlayerResponseDTO;
import com.quiz.layoutPDF.models.PlayerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/playerResponse")
public class PlayerResponseController {

    private final PlayerResponseService playerResponseService;

    public PlayerResponseController(PlayerResponseService playerResponseService) {
        this.playerResponseService = playerResponseService;
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerResponse>> getAllPlayerResponses() {
        List<PlayerResponse> playerResponses = playerResponseService.getAllPlayerResponses();
        return new ResponseEntity<>(playerResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PlayerResponse>> getAllPlayerResponsesForQuiz(@PathVariable Long id) {
        List<PlayerResponse> playerResponses = playerResponseService.getAllPlayerResponsesForQuiz(id);
        return new ResponseEntity<>(playerResponses, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createPlayerResponse(@RequestBody PlayerResponseDTO playerResponseDTO) {
        try {
            Long playerResponseId = playerResponseService.createPlayerResponse(playerResponseDTO);
            return new ResponseEntity<>("PlayerResponse successfully added with ID: " + playerResponseId, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred while creating the player response: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/response/{id}")
    public ResponseEntity<PlayerResponse> getPlayerResponseById(@PathVariable Long id) {
        try {
            PlayerResponse playerResponse = playerResponseService.getPlayerResponseById(id);
            if (playerResponse == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(playerResponse, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayerResponse(@PathVariable Long id) {
        try {
            Boolean deleted = playerResponseService.deletePlayerResponse(id);
            if (deleted) {
                return new ResponseEntity<>("PlayerResponse successfully deleted", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("PlayerResponse couldn't be deleted", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayerResponse(@PathVariable Long id, @RequestBody PlayerResponseDTO playerResponseDTO) {
        try {
            Boolean updated = playerResponseService.updatePlayerResponse(id, playerResponseDTO);
            if (updated) {
                return new ResponseEntity<>("PlayerResponse successfully updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("PlayerResponse not updated", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<String> deleteAllPlayerResponsesForAQuiz(@PathVariable Long id) {
        try {
            Boolean deleted = playerResponseService.deletePlayerResponseForAQuiz(id);
            if (deleted) {
                return new ResponseEntity<>("PlayerResponses successfully deleted for quiz with id " + id, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("PlayerResponses couldn't be deleted", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/response/quiz/{quizId}/player/{playerId}")
    public ResponseEntity<String> getPlayerResponseByQuizAndPlayer(@PathVariable Long quizId, @PathVariable Long playerId) {
        try {
            PlayerResponse playerResponse = playerResponseService.getPlayerResponseByQuizAndPlayer(quizId, playerId);
            if (playerResponse != null) {
                return new ResponseEntity<>(playerResponse.toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No response found for player with id " + playerId + " and quiz with id " + quizId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred while fetching the player response: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
