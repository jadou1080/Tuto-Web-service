package com.imt.framework.web.tuto.resources;

import com.imt.framework.web.tuto.entities.Livre;
import com.imt.framework.web.tuto.repositories.LivreRepository;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivreResourceTest {

    @Mock
    private LivreRepository livreRepository; // On "mocke" le repository pour isoler le code

    @InjectMocks
    private LivreResource livreResource;

    @Test
    void getBooksNominalTest() {
        when(livreRepository.findAll()).thenReturn(Collections.emptyList());
        Response response = livreResource.getBooks(null);
        assertEquals(200, response.getStatus());
        verify(livreRepository).findAll();
    }

    @Test
    void createBookNominalTest() {
        Livre livre = new Livre();
        livreResource.createBook(livre);
        verify(livreRepository).save(livre);
    }


    @Test
    void getBooksWithMaxPriceLimitTest() {
        Double maxPrice = 0.0;
        when(livreRepository.getBooksWithMaxPrice(maxPrice)).thenReturn(Collections.emptyList());

        Response response = livreResource.getBooks(maxPrice);

        assertEquals(200, response.getStatus());
        verify(livreRepository).getBooksWithMaxPrice(maxPrice);
    }

    @Test
    void updateBookExceptionTest() {
        Integer id = 99;
        Livre input = new Livre();
        when(livreRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            livreResource.updateBook(id, input);
        });

        assertEquals("Livre inconnu", exception.getMessage());
    }
}