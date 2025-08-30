package com.fastfashion.web;

import com.fastfashion.domain.ReturnRequest;
import com.fastfashion.service.ReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/returns")
public class ReturnAdminController {
    private final ReturnService returnService;

    public ReturnAdminController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ReturnRequest> approve(@PathVariable Long id) {
        return ResponseEntity.ok(returnService.approve(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<ReturnRequest> reject(@PathVariable Long id, @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(returnService.reject(id, reason));
    }

    @PostMapping("/{id}/pickup")
    public ResponseEntity<ReturnRequest> pickup(@PathVariable Long id) {
        return ResponseEntity.ok(returnService.markPickedUp(id));
    }
}
