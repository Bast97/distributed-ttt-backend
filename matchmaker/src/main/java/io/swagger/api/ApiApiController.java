package io.swagger.api;

import io.swagger.model.NewMatch;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-17T12:52:16.300Z[GMT]")
@RestController
public class ApiApiController implements ApiApi {

    private static final Logger log = LoggerFactory.getLogger(ApiApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ApiApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<NewMatch> getNewMatchUsingGET() {
        String accept = request.getHeader("Accept");
            System.out.println(accept);
        try {
            // return new ResponseEntity<NewMatch>(objectMapper.readValue("{\n  \"color\" : \"color\",\n  \"player_num\" : 6,\n  \"id\" : 0\n}", NewMatch.class), HttpStatus.OK);
            // HttpHeaders headers = new HttpHeaders();
            // headers.add("Custom-Header", "foo");

            Random rand = new Random();
            int maxNumber = 999999999;
            Long id = new Long(rand.nextInt(maxNumber) + 1);

            NewMatch match = new NewMatch();
            match.setId(id);
            match.setColor("X");
            match.setPlayerNum(1);
            return new ResponseEntity<NewMatch>(match, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Couldn't serialize response for content type application/json");
            System.out.println(e.toString());
            return new ResponseEntity<NewMatch>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // if (accept != null && accept.contains("application/json")) {
        // if (accept != null) {
        //     try {
        //         return new ResponseEntity<NewMatch>(objectMapper.readValue("{\n  \"color\" : \"color\",\n  \"player_num\" : 6,\n  \"id\" : 0\n}", NewMatch.class), HttpStatus.NOT_IMPLEMENTED);
        //     } catch (IOException e) {
        //         log.error("Couldn't serialize response for content type application/json", e);
        //         return new ResponseEntity<NewMatch>(HttpStatus.INTERNAL_SERVER_ERROR);
        //     }
        // } else {
        //     System.out.println("not working lol");
        //     System.out.println(accept);
        // }

        // return new ResponseEntity<NewMatch>(HttpStatus.NOT_IMPLEMENTED);
    }

}
