package bb.mom.tete.components.http.controllers;

import bb.mom.tete.components.db.XTDB;
import bb.mom.tete.entities.Event;
import bb.mom.tete.entities.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xtdb.api.IXtdb;
import xtdb.api.TransactionInstant;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    IXtdb xtdb;

    @PostMapping
    public TransactionInstant registerEvent(@RequestBody Event event) {
        return XTDB.ingest(xtdb, List.of(event.toDoc()));
    }

    @PostMapping("/query")
    public ResponseEntity<Collection<List<?>>> getEvents(@RequestBody final QueryRequest q) throws IOException {
        var res =  XTDB.query(TransactionInstant.factory(q.txInstant()), xtdb, q.query());
        return ResponseEntity.ok(res);
    }
}
