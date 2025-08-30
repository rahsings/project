package com.fastfashion.web;

import com.fastfashion.domain.ReturnRequest;
import com.fastfashion.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {
    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<ReturnRequest> request(@PathVariable Long orderId, @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(returnService.requestReturn(orderId, reason));
    }
}
