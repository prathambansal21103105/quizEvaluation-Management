package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.models.Player;
import com.quiz.layoutPDF.models.PlayerResponse;
import com.quiz.layoutPDF.models.PlayerResponseDTO;
import com.quiz.layoutPDF.Repository.PlayerResponseRepository;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.Repository.QuizRepository;
import com.quiz.layoutPDF.models.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerResponseService {

    private final PlayerResponseRepository playerResponseRepository;
    private final PlayerRepository playerRepository;
    private final QuizRepository quizRepository;

    public PlayerResponseService(PlayerResponseRepository playerResponseRepository, PlayerRepository playerRepository, QuizRepository quizRepository) {
        this.playerResponseRepository = playerResponseRepository;
        this.playerRepository = playerRepository;
        this.quizRepository = quizRepository;
    }

    public List<PlayerResponse> getAllPlayerResponses() {
        return playerResponseRepository.findAll();
    }

    public List<PlayerResponse> getAllPlayerResponsesForQuiz(Long quizId) {
        return playerResponseRepository.findByQuizId(quizId);
    }

    public PlayerResponse getPlayerResponseById(Long id) {
        Optional<PlayerResponse> playerResponse = playerResponseRepository.findById(id);
        return playerResponse.orElse(null);
    }

    public PlayerResponse getPlayerResponseByQuizAndPlayer(Long quizId, Long playerId) {
        Optional<PlayerResponse> playerResponse = playerResponseRepository.findByQuizIdAndPlayerId(quizId, playerId);
        return playerResponse.orElse(null);
    }

    public Long createPlayerResponse(PlayerResponseDTO playerResponseDTO) throws IllegalArgumentException {
        Optional<Quiz> quiz = quizRepository.findById(playerResponseDTO.getQuizId());
        if (quiz.isEmpty()) {
            throw new IllegalArgumentException("Quiz not found with ID: " + playerResponseDTO.getQuizId());
        }
        Player player = playerRepository.findById(playerResponseDTO.getPlayerId())
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setId(playerResponseDTO.getPlayerId());
                    newPlayer.setName("Unknown");
                    newPlayer.setBranch("Unknown");
                    return playerRepository.save(newPlayer);
                });

        Optional<PlayerResponse> existingResponse = playerResponseRepository
                .findByQuizIdAndPlayerId(quiz.get().getId(),player.getId());

        if (existingResponse.isPresent()) {
            PlayerResponse playerResponse = existingResponse.get();
            List<String> updatedResponses = new ArrayList<>(playerResponse.getMarkedResponses());
            updatedResponses.addAll(playerResponseDTO.getMarkedResponses());
            playerResponse.setMarkedResponses(updatedResponses);
            playerResponse.setScore(playerResponseDTO.getScore());
            playerResponseRepository.save(playerResponse);
            return playerResponse.getId();
        } else {
            PlayerResponse playerResponse = new PlayerResponse();
            playerResponse.setQuiz(quiz.get());
            playerResponse.setPlayer(player);
            playerResponse.setMarkedResponses(playerResponseDTO.getMarkedResponses());
            playerResponse.setScore(playerResponseDTO.getScore());

            playerResponse = playerResponseRepository.save(playerResponse);
            return playerResponse.getId();
        }
    }

    public Boolean updatePlayerResponse(Long id, PlayerResponseDTO playerResponseDTO) throws IllegalArgumentException {
        Optional<PlayerResponse> existingPlayerResponseOpt = playerResponseRepository.findById(id);
        if (!existingPlayerResponseOpt.isPresent()) {
            throw new IllegalArgumentException("PlayerResponse with ID " + id + " not found");
        }

        PlayerResponse existingPlayerResponse = existingPlayerResponseOpt.get();

        Optional<Quiz> quiz = quizRepository.findById(playerResponseDTO.getQuizId());
        if (!quiz.isPresent()) {
            throw new IllegalArgumentException("Quiz with ID " + playerResponseDTO.getQuizId() + " does not exist");
        }

        Player player = playerRepository.findById(playerResponseDTO.getPlayerId())
                .orElseGet(() -> playerRepository.save(new Player(playerResponseDTO.getPlayerId(), "Unknown", "Unknown")));

        existingPlayerResponse.setQuiz(quiz.get());
        existingPlayerResponse.setPlayer(player);
        existingPlayerResponse.setMarkedResponses(playerResponseDTO.getMarkedResponses());
        existingPlayerResponse.setScore(playerResponseDTO.getScore());

        playerResponseRepository.save(existingPlayerResponse);
        return true;
    }

    public Boolean deletePlayerResponse(Long id) throws IllegalArgumentException {
        if (!playerResponseRepository.existsById(id)) {
            throw new IllegalArgumentException("PlayerResponse with ID " + id + " not found");
        }

        playerResponseRepository.deleteById(id);
        return true;
    }

    public Boolean deletePlayerResponseForAQuiz(Long quizId) throws IllegalArgumentException {
        if (!quizRepository.existsById(quizId)) {
            throw new IllegalArgumentException("Quiz with ID " + quizId + " does not exist");
        }

        playerResponseRepository.deleteByQuizId(quizId);
        return true;
    }

    public boolean updateScore(Long id, Long playerScore, List<Long> scores) throws Exception {
        try {
            Optional<PlayerResponse> playerResponseOpt = playerResponseRepository.findById(id);
            if (playerResponseOpt.isPresent()) {
                PlayerResponse playerResponse = playerResponseOpt.get();
                playerResponse.setScore(playerScore);
                playerResponse.setScores(scores);
                playerResponseRepository.save(playerResponse);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return true;
    }
}
