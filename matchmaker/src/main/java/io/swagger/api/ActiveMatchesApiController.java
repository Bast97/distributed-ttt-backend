package io.swagger.api;

import io.swagger.model.ActiveMatches;
import io.swagger.model.NewMatch;
import io.swagger.matchmaker.Matchmaker;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-01T12:16:14.800Z[GMT]")
@CrossOrigin(origins = "*")
@RestController
public class ActiveMatchesApiController implements ActiveMatchesApi {

    private static final Logger log = LoggerFactory.getLogger(NewmatchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    private Matchmaker matchmaker;

    @org.springframework.beans.factory.annotation.Autowired
    public ActiveMatchesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.matchmaker = new Matchmaker();
    }

    public ResponseEntity<ActiveMatches> getActiveMatchesUsingGET() {
            try {
                ActiveMatches activeMatches = new ActiveMatches();
                activeMatches.setNumActiveMatches(this.matchmaker.getNumberOfMatches());
                
                return new ResponseEntity<ActiveMatches>(activeMatches, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ActiveMatches>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

}
