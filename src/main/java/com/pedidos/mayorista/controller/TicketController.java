package com.pedidos.mayorista.controller;

import com.pedidos.mayorista.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@CrossOrigin("*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/{pedidoId}")
    public ResponseEntity<byte[]> descargarTicket(
            @PathVariable Long pedidoId
    ) {

        byte[] pdf =
                ticketService.generarTicket(pedidoId);

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=ticket-" + pedidoId + ".pdf"
                )

                .contentType(MediaType.APPLICATION_PDF)

                .body(pdf);
    }
}