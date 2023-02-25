package sod.eastonone.music.es.service;

import java.io.IOException;
import java.util.List;

import sod.eastonone.music.es.model.Song;

public interface ESService {

//    public Employee fetchEmployeeById(String id) throws RecordNotFoundException, IOException;
//
//    public String insertEmployee(Employee employee) throws IOException;
//
//    public boolean bulkInsertEmployees(List<Employee> employees) throws IOException;
//
//    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException;
//    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException;
//
//    public String deleteEmployeeById(Long id) throws IOException;
//
//    public String updateEmployee(Employee employee) throws IOException;

    public List<Song> fetchSongsWithShouldQuery(Song song) throws IOException;
    
    public String insertSong(Song song) throws IOException;

    public String updateSong(Song song) throws IOException;
}
