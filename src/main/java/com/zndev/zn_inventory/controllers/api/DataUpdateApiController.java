package com.zndev.zn_inventory.controllers.api;

import com.zndev.zn_inventory.helpers.Helper;
import com.zndev.zn_inventory.helpers.ResourceHelper;
import com.zndev.zn_inventory.models.other.Response;
import com.zndev.zn_inventory.models.other.TableUpdate;
import com.zndev.zn_inventory.repository.UpdatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class DataUpdateApiController {

    @Autowired
    private UpdatesRepo updatesRepo;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "api/updates/view")
    private Flux<List<TableUpdate>> getUpdates() {
        Flux<List<TableUpdate>> updatesFlux = Flux.fromStream(Stream.generate(() -> {
            List<TableUpdate> updates = updatesRepo.findAll();
            return updates;
        }));

        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(5));

        return Flux.zip(updatesFlux, durationFlux).map(Tuple2::getT1);
    }


    @PutMapping("api/updates/update/{tag}")
    private Response updateTableByTag(@PathVariable("tag") String tag) {
        TableUpdate update = updatesRepo.findByCode(tag);
        update.setDateUpdated(new Date());
        updatesRepo.save(update);

        return Helper.createResponse("Request Successful", true);
    }
}
