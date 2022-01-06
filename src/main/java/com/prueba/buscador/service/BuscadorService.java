package com.prueba.buscador.service;

import com.prueba.buscador.config.Constants;
import com.prueba.buscador.domain.Authority;
import com.prueba.buscador.domain.User;
import com.prueba.buscador.repository.AuthorityRepository;
import com.prueba.buscador.repository.UserRepository;
import com.prueba.buscador.security.AuthoritiesConstants;
import com.prueba.buscador.security.SecurityUtils;
import com.prueba.buscador.service.dto.AdminUserDTO;
import com.prueba.buscador.service.dto.UserDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class BuscadorService {

    private final Logger log = LoggerFactory.getLogger(BuscadorService.class);

    public BuscadorService() {}

    public String calcularEdad(String nombre, String fechaNacimiento) {
    	
    	
    	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formato);
    	LocalDate diaHoy = LocalDate.now();

    	Period periodo = Period.between(fechaNac, diaHoy);
    	System.out.printf("Tu edad es: %s años, %s meses y %s días",
    	                    periodo.getYears(), periodo.getMonths(), periodo.getDays());
    	if(periodo.getYears() > 18) {
    		return "Eres mayor de edad " + nombre + " Bienvenido";
    	}else {
    		int resta = 18 - periodo.getYears();
    		return "No cumples con la edad " + nombre + "Intenta en: " + resta + " años";
    	}
    	
    }
    
    public List<String> buscarFiltro(String filtro) {
    	List<String>listFiltro = new ArrayList<>();
    	List<String>listSabritas = new ArrayList<>();
    	List<String>listBarcel = new ArrayList<>();
    	Map<String,List<String>>mapFiltros = new HashMap<>();
    	
    	listSabritas.add("Rufles");
    	listSabritas.add("Rancheritos");
    	listSabritas.add("Chetos");
    	
    	listBarcel.add("Runner");
    	listBarcel.add("Chips");
    	listBarcel.add("Toreadas");
    	
    	mapFiltros.put("Sabritas", listSabritas );
    	mapFiltros.put("Barcel", listBarcel );
    	log.debug("lista de!!!! : {}",mapFiltros);
    	if(mapFiltros.containsKey(filtro)) {
    		log.debug("lista {}",mapFiltros.get(filtro));
    		listFiltro = mapFiltros.get(filtro);
    		return listFiltro;
    	}else {
    		listFiltro.add("No se encontro el filtro");
    		return listFiltro ;
    	}
    	
    }
}
