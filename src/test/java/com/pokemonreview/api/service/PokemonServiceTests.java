package com.pokemonreview.api.service;

import com.pokemonreview.api.controllers.PokemonController;
import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PokemonController.class)
@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_CreatePokemon_ReturnPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        //dto --> refers to expected response based on the requests of certian type.
        PokemonDto pokemondto = PokemonDto.builder().name("pikachu").type("electric").build();
        // Whenever we save any mock pokemon in the pokemonRepository then return that pokemon.
        // when --> it will mock the save method of the repository method.
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto savedPokemon = pokemonService.createPokemon(pokemondto);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnResponse() {
        // Inserting fake pokemons inside the mock repo.
        // Create mocks of Page<Pokemon>.
        Page<Pokemon> pokemons = Mockito.mock(Page.class);
        // Mocking the repository method.
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savedPokemon = pokemonService.getAllPokemon(1,10);
        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();

        PokemonDto pokemonDto = PokemonDto.builder().name("pikachu").type("electric").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.updatePokemon(pokemonDto,1);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetPokemonById_ReturnResponse() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electric").build();
        PokemonDto pokemonDto = PokemonDto.builder().name(pokemon.getName()).type(pokemon.getType()).build();

        when(pokemonRepository.findById((1))).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto savedPokemon = pokemonService.getPokemonById(1);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_DeletePokemonById_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("pikachu").type("electric").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        assertAll(() -> pokemonService.deletePokemonId(1));
    }
}
