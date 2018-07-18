package com.ratiose.testtask.service.tmdb.impl;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ratiose.testtask.dto.DiscoverMoviesResponse;
import com.ratiose.testtask.dto.MovieData;
import com.ratiose.testtask.service.UserService;
import com.ratiose.testtask.service.tmdb.TmdbApi;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TmdbApiImpl implements TmdbApi {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbApiImpl.class);

    private static final String DISCOVER_URL = "/discover/movie";
    private static final String PARAMETER_ACTORS = "with_cast";
    private static final String PARAMETER_RELEASE_DATE_FROM = "release_date.gte";
    private static final String PARAMETER_RELEASE_DATE_TO = "release_date.lte";
    private static final String PARAMETER_PAGE = "page";
    private static final String DEFAULT_PARAMETER_SEPARATOR = ",";

    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    @Autowired
    private UserService userService;

    @Override
    public DiscoverMoviesResponse discoverMovies(int page, int year, int month) {
        try {
            String request = buildDiscoverUrl(page, year, month);
            HttpResponse<JsonNode> jsonResponse = Unirest.get(request).asJson();

            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return null;
            }

            String responseJSONString = jsonResponse.getBody().toString();

            DiscoverMoviesResponse response = convertToResponse(responseJSONString);

            Set<String> watchedMovies = userService.getCurrentUser().getWatchedMovies();
            List<MovieData> filteredMovies =
                    response.getResults().stream().filter(movie->!watchedMovies.contains(movie.getId())).collect(Collectors.toList());

            response.setResults(filteredMovies);

            return response;
        } catch (UnirestException e) {
            LOG.error("UnirestException has occured due to:", e);
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException has occured due to:", e);
        }
        return null;
    }

    private String buildDiscoverUrl(int page, int year, int month) throws URISyntaxException {
        return getTmdbUrl(DISCOVER_URL, prepareParametersList(page, year, month));
    }

    private String getTmdbUrl(String tmdbItem, List<NameValuePair> parameters) throws URISyntaxException {
        StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
        builder.append(tmdbItem);
        URIBuilder uriBuilder = new URIBuilder(builder.toString());
        uriBuilder.addParameter("language", tmdbLanguage);
        uriBuilder.addParameter("api_key", tmdbApiKey);
        uriBuilder.addParameters(parameters);
        return uriBuilder.build().toString();
    }

    private List<NameValuePair> prepareParametersList(int page, int year, int month) {
        List<NameValuePair> parametersList = new ArrayList<>();
        LocalDate fromDate = LocalDate.of(year, month, 1);
        LocalDate toDate = fromDate.plusMonths(1).minusDays(1);
        parametersList.add(pair(PARAMETER_PAGE, Integer.toString(page)));
        parametersList.add(pair(PARAMETER_RELEASE_DATE_FROM, fromDate.format(DateTimeFormatter.ISO_DATE)));
        parametersList.add(pair(PARAMETER_RELEASE_DATE_TO, toDate.format(DateTimeFormatter.ISO_DATE)));

        Set<String> favouriteActors = userService.getCurrentUser().getFavouriteActors();
        if (!favouriteActors.isEmpty()) {
            parametersList.add(pair(PARAMETER_ACTORS, favouriteActors.stream().collect(Collectors.joining(DEFAULT_PARAMETER_SEPARATOR))));
        }
        return parametersList;
    }

    private NameValuePair pair(String param, String value) {
        return new BasicNameValuePair(param, value);
    }

    private DiscoverMoviesResponse convertToResponse(String json) {
        return new Gson().fromJson(json, DiscoverMoviesResponse.class);
    }
}
