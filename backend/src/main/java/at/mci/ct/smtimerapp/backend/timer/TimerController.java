package at.mci.ct.smtimerapp.backend.timer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
public class TimerController {

    private final TimerEntryRepository repository;

    public TimerController(TimerEntryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TimerEntry> getAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimerEntry create(@RequestBody CreateTimerRequest request) {
        return repository.save(
                new TimerEntry(request.durationMs())
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        repository.deleteAll();
    }
}