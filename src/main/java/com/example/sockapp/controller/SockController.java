package com.example.sockapp.controller;

import com.example.sockapp.entity.Comparison;
import com.example.sockapp.service.SockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SockController {

    private SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @Operation(
            summary = "провести в базу приход носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "удалось добавить приход"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка")
            }
    )
    @PostMapping("/income")
    public ResponseEntity socksIncome(@RequestParam String color,
                            @RequestParam int cottonPart,
                            @RequestParam int quantity) {
        if (quantity > 0){
            return ResponseEntity.ok(sockService.saveSock(color,cottonPart,quantity));
        }
       return ResponseEntity.status(400).body("количество не может быть отрицательным");
    }

    @Operation(
            summary = "списать из базы носки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "носки списаны"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка")
            }
    )
    @PostMapping("/outcome")
    public void socksOutcome(@RequestParam String color,
                             @RequestParam int cottonPart,
                             @RequestParam int quantity) {
        sockService.deleteSock(color,cottonPart,quantity);
    }

    @Operation(
            summary = "посмотреть в базе количество носков",
            responses = {
                    @ApiResponse(responseCode = "200", description = "список носков"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка")
            }
    )
    @GetMapping()
    public ResponseEntity socksOGet(@RequestParam String color,
                             @RequestParam Comparison comparison,
                             @RequestParam int cottonPart) {
        return ResponseEntity.ok(sockService.getSock(color,comparison,cottonPart));
    }
}
