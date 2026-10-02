package com.Gadiza.backend.controller;

import com.Gadiza.backend.model.Score;
import com.Gadiza.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO: add an annotation that will mark this class as REST API Controller
@RestController
// TODO: add an annotation to map the API to "api/scores"
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {

    // TODO: Add an annotation to do Dependency Injection from the existing instance (ScoreService)
    // TODO: Add a private field for ScoreService
    @Autowired
    private ScoreService scoreService;

    // TODO: add an annotation to map HTTP GET to this method with "/{scoreId}" as the path
    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(/* TODO: add @PathVariable for scoreId here */ @PathVariable UUID scoreId) {
        // TODO: create a score variable to store the score given by scoreService
        Optional<Score> score = scoreService.getScoreByID(scoreId);

        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Score not found with ID: " + scoreId));
        }
    }

    // TODO: add an annotation to map HTTP POST to this method
    @PostMapping
    public ResponseEntity<?> createScore(/* TODO: add @RequestBody to bind JSON body from request to score object here */ @RequestBody Score score) {
        try {
            // TODO: Create a new score instance using scoreService with the data available from the parameter
            Score newScore = scoreService.createScore(score);
            // TODO: return the new score response data with CREATED status
            return ResponseEntity.status(HttpStatus.CREATED;
        } catch (RuntimeException e) {
            // TODO: return error response with BAD_REQUEST status and a matching error body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage() != null ? e.getMessage() : "Invalid request data"));
        }
    }

    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        // 2. Use scoreService to call getAllScores() and store those scores in a variable using List
        scoreService.getAllScores();
        // 3. Return the variable containing those scores
        return ResponseEntity.status();
    }

    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetExchange
    public  ResponseEntity<List<Score>> getLeaderboardByPoint(
	/* 2. add the '@RequestParam' parameter with defaultValue 10
	   3. as well as Integer limit */ @RequestParam int limit){
        // 4. Use scoreService to call getLeaderboard() with the appropriate parameter
        scoreService.getLeaderboard(limit);
        //    and store those scores in a variable using List
        // 5. Return the variable containing those scores
        return ResponseEntity.status(HttpStatusCode.valueOf(limit));
    }

    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetExchange
    public ResponseEntity<List<Score>> getScoresAboveValue(
            /* 2. add '@PathVariable' for Integer minValue*/ @PathVariable int minValue){
        // 3. Use scoreService to call getScoreAboveValue() with the appropriate parameter
        scoreService.getScoreAboveValue(minValue);
        //    and store those scores in a variable using List
        // 4. Return the variable containing those scores
        return ResponseEntity.of(ProblemDetail.forStatus(minValue));
    }

    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    public ResponseEntity<List<Score>> getRecentScores(){
        // 2. Use scoreService to call getRecentScores() with the appropriate parameter
        //    and store those scores in a variable using List
        scoreService.getRecentScores();
        // 3. Return the variable containing those scores
        return ResponseEntity.status();
    }

    // TODO:
// 1. Add the appropriate annotation for a DELETE endpoint along with the appropriate endpoint
    public  ResponseEntity.BodyBuilder deleteScore(
            /* 2. add '@PathVariable' for scoreId*/ @PathVariable Score scoreId){
        // 3. create a try-catch block
        // in the try block:
        //  use scoreService to call deleteScore() with the appropriate parameter
        scoreService.deleteScore(scoreId.getScoreId());
        //  return a response indicating the score was successfully deleted
        return scoreService.
        // in the catch block:
        //  return an error response with status NOT_FOUND along with an appropriate error body
        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

}