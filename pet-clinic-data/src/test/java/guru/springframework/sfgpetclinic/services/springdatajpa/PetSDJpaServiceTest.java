package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class PetSDJpaServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSDJpaService petSDJpaService;

    Pet returnPet;


    @BeforeEach
    void setUp() {
        returnPet = Pet.builder().id(1l).build();
    }


    @Test
    void findAll() {
        Pet pet = Pet.builder().id(2l).build();
        Set <Pet> petSet = new HashSet<>();
        petSet.add(pet);
        petSet.add(returnPet);
        when(petRepository.findAll()).thenReturn(petSet);
        petSDJpaService.findAll();
        Set<Pet> pets = petSDJpaService.findAll();
        assertEquals(pets.size(), petSet.size());
    }

    @Test
    void findById() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(returnPet));
        Pet newPet =  petSDJpaService.findById(1L);
        assertEquals(newPet, returnPet);
    }

    @Test
    void findByIdNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());
        Pet newPet = petSDJpaService.findById(3L);
        assertNull(newPet);
    }

    @Test
    void save() {
        when(petRepository.save(any())).thenReturn(returnPet);
        Pet pet = petSDJpaService.save(returnPet);
        assertNotNull(pet);

        verify(petRepository).save(any());    }

    @Test
    void delete() {
        petSDJpaService.delete(returnPet);
        verify(petRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petSDJpaService.deleteById(1L);
        verify(petRepository, times(1)).deleteById(anyLong());
    }
}