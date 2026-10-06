package com.Gadiza.backend.service;

import com.Gadiza.backend.model.Score;
import com.Gadiza.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }

    public Optional<Score> getScoreByID(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores(){
        // TODO: Use scoreRepository to find all scores in the database, then return the result
        return scoreRepository.findAll();
        // hint: Call the same method as the code you wrote in TP number 4
    }

    public List<Score> getRecentScores(){
        // TODO: Use scoreRepository to find all scores in the database ordered by newest creation, then return the result
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Score> getScoreAboveValue(Integer minValue){
        // TODO: Use scoreRepository to find all scores in the database whose points are above a certain value
        return scoreRepository.findPointGreaterThan(minValue);
        // use minValue as the lower bound of the point value
    }

    public List<Score> getLeaderboard(Integer limit) {
        // TODO: Use scoreRepository to find the Top Scores and provide the appropriate parameter
        return scoreRepository.findTopScores(org.springframework.data.domain.PageRequest.of(0, limit));
    }

    public void deleteScore(UUID scoreId) {
        Score scoreDelete = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score with ID " + scoreId + " was not found"));
        scoreRepository.delete(scoreDelete);
    }


}