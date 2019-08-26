package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    private Owner returnOwner;

    private final String LAST_NAME = "smith";

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).build();
    }

    @Test
    void findByLastName() {
       Owner returnOwner = Owner.builder().id(2L).lastName(LAST_NAME).build();
       when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
       Owner smith = ownerSDJpaService.findByLastName(LAST_NAME);

       assertEquals(LAST_NAME, smith.getLastName());

       verify(ownerRepository, times(1)).findByLastName(any());
    }

    @Test
    void findAllByLastNameLike() {

    }

    @Test
    void findAll() {
        Owner secondOwner = new Owner();
        ownerRepository.save(secondOwner);
        Set<Owner> owners = new HashSet<>();
        owners.add(secondOwner);
        owners.add(returnOwner);
        when(ownerRepository.findAll()).thenReturn(owners);
        Set<Owner> owners2 =  ownerSDJpaService.findAll();

        assertNotNull(owners2);
        assertEquals(owners.size(), owners2.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner = ownerSDJpaService.findById(3L);
        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = ownerSDJpaService.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        when(ownerRepository.save(anyObject())).thenReturn(returnOwner);
        Owner secondOwner = ownerSDJpaService.save(new Owner());
        assertNotNull(secondOwner);
        assertEquals(returnOwner, secondOwner);
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(anyObject());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository, times(1)).deleteById(any());
    }
}