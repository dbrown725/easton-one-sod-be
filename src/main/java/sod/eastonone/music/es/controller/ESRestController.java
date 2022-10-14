package sod.eastonone.music.es.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.es.service.ESService;
import sod.eastonone.music.service.SodSongService;

@RestController
public class ESRestController {


    @Autowired
    private ESService esService;
    
    @Autowired
    private SodSongService sodSongService;
    
    @PostMapping("/index/fetchSongsWithShould")
    public ResponseEntity<List<Song>> fetchSongsWithShouldQuery(@RequestBody Song songSearchRequest) throws IOException {
        List<Song> songs = esService.fetchSongsWithShouldQuery(songSearchRequest);
        return ResponseEntity.ok(songs);
    }
    
	@PostMapping("/index")
	public ResponseEntity<Song> createSong(@RequestBody Song song) throws IOException {
		Song newSong = sodSongService.createSodSong(song.getTitle(), song.getPlaylist(), song.getLink(),
				song.getBandName(), song.getSongName(), song.getMessage(), song.getUserId());
		return ResponseEntity.ok(newSong);
	}

//    @GetMapping("/index/{id}")
//    public ResponseEntity<Employee> fetchEmployeeById(@PathVariable("id") String id) throws RecordNotFoundException, IOException {
//        Employee employee = esService.fetchEmployeeById(id);
//        return ResponseEntity.ok(employee);
//    }
//
//    @PostMapping("/index/fetchWithMust")
//    public ResponseEntity<List<Employee>> fetchEmployeesWithMustQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
//        List<Employee> employees = esService.fetchEmployeesWithMustQuery(employeeSearchRequest);
//        return ResponseEntity.ok(employees);
//    }
//
//    @PostMapping("/index/fetchWithShould")
//    public ResponseEntity<List<Employee>> fetchEmployeesWithShouldQuery(@RequestBody Employee employeeSearchRequest) throws IOException {
//        List<Employee> employees = esService.fetchEmployeesWithShouldQuery(employeeSearchRequest);
//        return ResponseEntity.ok(employees);
//    }

//    @PostMapping("/index")
//    public ResponseEntity<String> insertRecords(@RequestBody Employee employee) throws IOException {
//        String status = esService.insertEmployee(employee);
//        return ResponseEntity.ok(status);
//    }

//    @PostMapping("/index/bulk")
//    public ResponseEntity<String> bulkInsertEmployees(@RequestBody List<Employee> employees) throws IOException {
//        boolean isSuccess = esService.bulkInsertEmployees(employees);
//        if(isSuccess) {
//            return ResponseEntity.ok("Records successfully ingested!");
//        } else {
//            return ResponseEntity.internalServerError().body("Oops! unable to ingest data");
//        }
//    }
//
//    @DeleteMapping("/index/{id}")
//    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) throws IOException {
//        String status = esService.deleteEmployeeById(id);
//        return ResponseEntity.ok(status);
//    }
//
//    @PutMapping("/index")
//    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) throws IOException {
//        String status = esService.updateEmployee(employee);
//        return ResponseEntity.ok(status);
//    }
    
}