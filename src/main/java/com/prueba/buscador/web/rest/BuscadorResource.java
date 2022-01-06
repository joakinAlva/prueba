package com.prueba.buscador.web.rest;

import com.prueba.buscador.config.Constants;
import com.prueba.buscador.domain.User;
import com.prueba.buscador.repository.UserRepository;
import com.prueba.buscador.security.AuthoritiesConstants;
import com.prueba.buscador.service.BuscadorService;
import com.prueba.buscador.service.MailService;
import com.prueba.buscador.service.UserService;
import com.prueba.buscador.service.dto.AdminUserDTO;
import com.prueba.buscador.service.dto.EdadDTO;
import com.prueba.buscador.service.dto.FiltroDto;
import com.prueba.buscador.web.rest.errors.BadRequestAlertException;
import com.prueba.buscador.web.rest.errors.EmailAlreadyUsedException;
import com.prueba.buscador.web.rest.errors.LoginAlreadyUsedException;

import io.micrometer.core.instrument.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Collections;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class BuscadorResource {


    private final Logger log = LoggerFactory.getLogger(BuscadorResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    private BuscadorService buscadorService;

    
    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/calcularEdad")
    public ResponseEntity<EdadDTO> calcularEdad(String name, String fechaNacimiento) {
    	EdadDTO edad = new EdadDTO();
    	if(StringUtils.isEmpty(name) || StringUtils.isEmpty(name)){
    		return new ResponseEntity<>(edad, HttpStatus.OK);
    	}
    	String response = buscadorService.calcularEdad(name, fechaNacimiento);
    	
    	edad.setRespuesta(response);
    	
        return new ResponseEntity<>(edad, HttpStatus.OK);
    }
    
    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/buscarFiltro")
    public ResponseEntity<FiltroDto> buscarFiltro(String filtro) {
    	FiltroDto filtroDto = new FiltroDto(); 
    	List<String> response = new ArrayList<>();
    	response = buscadorService.buscarFiltro(filtro);
    	filtroDto.setListaProductos(response);
    	
        return new ResponseEntity<>(filtroDto, HttpStatus.OK);
    }

}
