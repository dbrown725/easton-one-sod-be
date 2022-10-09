package sod.eastonone.music.es.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sod.eastonone.music.es.connector.ESClientConnector;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.es.service.ESService;

@Service
public class ESServiceImpl implements ESService {

    @Autowired
    private ESClientConnector esClientConnector;
    
    @Override
    public List<Song> fetchSongsWithShouldQuery(Song song) throws IOException {
        return esClientConnector.fetchSongsWithShouldQuery(song);
    }
    
    @Override
    public String insertSong(Song song) throws IOException {
        return esClientConnector.insertSong(song);
    }

//    @Override
//    public Employee fetchEmployeeById(String id) throws RecordNotFoundException, IOException {
//        return esClientConnector.fetchEmployeeById(id);
//    }
//
//    @Override
//    public String insertEmployee(Employee employee) throws IOException {
//        return esClientConnector.insertEmployee(employee);
//    }
//
//    @Override
//    public boolean bulkInsertEmployees(List<Employee> employees) throws IOException {
//       return esClientConnector.bulkInsertEmployees(employees);
//    }
//
//    @Override
//    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException {
//        return esClientConnector.fetchEmployeesWithMustQuery(employee);
//    }
//
//    @Override
//    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException {
//        return esClientConnector.fetchEmployeesWithShouldQuery(employee);
//    }
//
//    @Override
//    public String deleteEmployeeById(Long id) throws IOException {
//        return esClientConnector.deleteEmployeeById(id);
//    }
//
//    @Override
//    public String updateEmployee(Employee employee) throws IOException {
//        return esClientConnector.updateEmployee(employee);
//    }
    
}
