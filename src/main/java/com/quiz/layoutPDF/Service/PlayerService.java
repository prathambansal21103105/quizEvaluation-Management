package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.models.Player;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Long createPlayer(Player player) {
        if (playerRepository.existsById(player.getId())) {
            throw new RuntimeException("Player ID already exists: " + player.getId());
        }
        Player savedPlayer = playerRepository.save(player);
        return savedPlayer.getId();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public Boolean deletePlayer(Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean updatePlayer(Long id, Player updatedPlayer) {
        Optional<Player> existingPlayerOpt = playerRepository.findById(id);
        if (existingPlayerOpt.isPresent()) {
            Player existingPlayer = existingPlayerOpt.get();
            existingPlayer.setName(updatedPlayer.getName());
            existingPlayer.setBranch(updatedPlayer.getBranch());
            playerRepository.save(existingPlayer);
            return true;
        }
        return false;
    }
}
