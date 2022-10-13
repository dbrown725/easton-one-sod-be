package sod.eastonone.music.es.connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.es.utils.QueryBuilderUtils;

@Service
public class ESClientConnector {

    @Value("${elastic.index}")
    private String index;

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    public List<Song> fetchSongsWithShouldQuery(Song song) throws IOException {
    	
    	String searchField = "youtube_title";

    	HighlightField field = new HighlightField.Builder().build();

    	//Docs not great, not sure what type does
    	//HighlightField field = (new HighlightField.Builder()).type(HighlighterType.Plain).build();
    	
    	    	
        SearchResponse<Song> songSearchResponse = elasticsearchClient.search(req->
                        req.index(index)
                                .size(100)
                                .highlight(h -> h
                                		.fields(searchField, field) 
                                		)
                                .query(q -> q      
                                        .match(t -> t   
                                            .field(searchField) 
                                            .query(song.getTitle())
                                        )
                                    ),
                                
                Song.class);
        
        List<Song> foundSongs = new ArrayList<Song>();
        
        List<Hit<Song>> hits = songSearchResponse.hits().hits();
        for (Hit<Song> hit: hits) {
        	Song foundSong = hit.source();
        	foundSong.setTitleHighlighted(hit.highlight().get(searchField).get(0));
        	foundSong.setScore(hit.score());
        	foundSongs.add(foundSong);
        }
        return foundSongs;


//        return songSearchResponse.hits().hits().stream()
//                .map(Hit::source).collect(Collectors.toList());
    }
    
    public String insertSong(Song song) throws IOException {
        IndexRequest<Song> request = IndexRequest.of(i->
                i.index(index)
                        .id(String.valueOf(song.getId()))
                        .document(song));
        IndexResponse response = elasticsearchClient.index(request);
        return response.result().toString();
    }
    
    private List<Query> prepareSongQueryList(Song song) {
        Map<String, String> conditionMap = new HashMap<>();
        conditionMap.put("youtube_title.text", song.getTitle());

        return conditionMap.entrySet().stream()
                .filter(entry->!ObjectUtils.isEmpty(entry.getValue()))
                .map(entry->QueryBuilderUtils.termQuery(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

//    public String insertEmployee(Employee employee) throws IOException {
//        IndexRequest<Employee> request = IndexRequest.of(i->
//                i.index(index)
//                        .id(String.valueOf(employee.getId()))
//                        .document(employee));
//        IndexResponse response = elasticsearchClient.index(request);
//        return response.result().toString();
//    }
//
//    public boolean bulkInsertEmployees(List<Employee> employeeList) throws IOException {
//        BulkRequest.Builder builder = new BulkRequest.Builder();
//        employeeList.stream().forEach(employee ->
//            builder.operations(op->
//                    op.index(i->
//                            i.index(index)
//                                    .id(String.valueOf(employee.getId()))
//                                    .document(employee)))
//        );
//        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
//        return !bulkResponse.errors();
//    }
//
//    public Employee fetchEmployeeById(String id) throws RecordNotFoundException, IOException {
//        GetResponse<Employee> response = elasticsearchClient.get(req->
//                req.index(index)
//                        .id(id),Employee.class);
//        if(!response.found())
//            throw new RecordNotFoundException("Employee with ID" + id + " not found!");
//
//        return response.source();
//    }
//
//    public List<Employee> fetchEmployeesWithMustQuery(Employee employee) throws IOException {
//        List<Query> queries = prepareQueryList(employee);
//        SearchResponse<Employee> employeeSearchResponse = elasticsearchClient.search(req->
//                req.index(index)
//                        .size(employee.getSize())
//                        .query(query->
//                                query.bool(bool->
//                                        bool.must(queries))),
//                Employee.class);
//
//        return employeeSearchResponse.hits().hits().stream()
//                .map(Hit::source).collect(Collectors.toList());
//    }
//
//    public List<Employee> fetchEmployeesWithShouldQuery(Employee employee) throws IOException {
//        List<Query> queries = prepareQueryList(employee);
//        SearchResponse<Employee> employeeSearchResponse = elasticsearchClient.search(req->
//                        req.index(index)
//                                .size(employee.getSize())
//                                .query(query->
//                                        query.bool(bool->
//                                                bool.should(queries))),
//                Employee.class);
//
//        return employeeSearchResponse.hits().hits().stream()
//                .map(Hit::source).collect(Collectors.toList());
//    }
//
//    public String deleteEmployeeById(Long id) throws IOException {
//        DeleteRequest request = DeleteRequest.of(req->
//                req.index(index).id(String.valueOf(id)));
//        DeleteResponse response = elasticsearchClient.delete(request);
//        return response.result().toString();
//    }
//
//    public String updateEmployee(Employee employee) throws IOException {
//        UpdateRequest<Employee, Employee> updateRequest = UpdateRequest.of(req->
//                req.index(index)
//                        .id(String.valueOf(employee.getId()))
//                        .doc(employee));
//        UpdateResponse<Employee> response = elasticsearchClient.update(updateRequest, Employee.class);
//        return response.result().toString();
//    }
    


//    private List<Query> prepareQueryList(Employee employee) {
//        Map<String, String> conditionMap = new HashMap<>();
//        conditionMap.put("firstName.keyword", employee.getFirstName());
//        conditionMap.put("lastName.keyword", employee.getLastName());
//        conditionMap.put("gender.keyword", employee.getGender());
//        conditionMap.put("jobTitle.keyword", employee.getJobTitle());
//        conditionMap.put("phone.keyword", employee.getPhone());
//        conditionMap.put("email.keyword", employee.getEmail());
//
//        return conditionMap.entrySet().stream()
//                .filter(entry->!ObjectUtils.isEmpty(entry.getValue()))
//                .map(entry->QueryBuilderUtils.termQuery(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//    }
    



}
