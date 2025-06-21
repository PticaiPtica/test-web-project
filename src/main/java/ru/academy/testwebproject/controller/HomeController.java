package ru.academy.testwebproject.controller;


import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.academy.testwebproject.model.MovieApiResponse;
import ru.academy.testwebproject.model.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/home")
public class HomeController {

    private final String apiKey = "266b43cd";
    private final String baseUrl = "https://www.omdbapi.com";

    @GetMapping("/{title}")
    public ResponseEntity<?> getAll(@PathVariable String title) {
        String url = baseUrl + "/?s=" + title + "&apiKey=" + apiKey + "&page=";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Send request #" + 1);
        ResponseEntity<MovieApiResponse> response = restTemplate.getForEntity(url + 1, MovieApiResponse.class);
        MovieApiResponse result = null;
        if (response.getStatusCode().is2xxSuccessful()) {
            result = response.getBody();
            List<Search> arr = new ArrayList<>(result.Search);


            int totalResult = Integer.parseInt(result.totalResults);//184=>18.4=>19.0=>19
            int pages = (int) Math.ceil(totalResult / 10.0);
            System.out.println("Total result => " + totalResult);
            System.out.println("Total pages  => " + pages);

            for (int i = 2; i <= pages; i++) {
                System.out.println("Send request #" + i);
                response = restTemplate.getForEntity(url + i, MovieApiResponse.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    result = response.getBody();
                    arr.addAll(result.Search);
                }
            }

            result.Search = new ArrayList<>(arr);
//            result.currentPage = 1;
            result.totalPages = pages;
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @GetMapping("/page/{title}&{ViewSize}&{page}")
    public ResponseEntity<?> getTitleAndPage(@PathVariable String title, @PathVariable int ViewSize, @PathVariable int page) {
        String url = baseUrl + "/?s=" + title + "&apiKey=" + apiKey + "&page=";


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MovieApiResponse> response = restTemplate.getForEntity(url + page, MovieApiResponse.class);

        MovieApiResponse result;

        if (response.getStatusCode().is2xxSuccessful()) {
            result = response.getBody();
            assert result != null;

            ArrayList<Search> arr = new ArrayList<>(result.Search);

            int totalResult = Integer.parseInt(result.totalResults);
            int pages = (int) Math.ceil((double) totalResult / ViewSize);

            System.out.println("Total result => " + totalResult);
            System.out.println("Total pages  => " + pages);

            for (int i = page; i <= pages; i++) {
                System.out.println("Send request #" + i);
                response = restTemplate.getForEntity(url + i, MovieApiResponse.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    result = response.getBody();
                    assert result != null;
                    arr.addAll(result.Search);
                }

            }
            System.out.println(arr + "");

            result.Search = new ArrayList<>(arr);

            result.currentPage = page;
            result.owner = "Sokol";
            result.group = "411";
            return ResponseEntity.ok(result);

        }

        return ResponseEntity.notFound().build();

    }
}
