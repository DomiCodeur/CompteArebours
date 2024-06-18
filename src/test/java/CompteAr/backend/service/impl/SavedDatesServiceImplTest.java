package CompteAr.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import CompteAr.backend.repository.SavedDatesRepository;
import CompteAr.backend.resources.SavedDatesResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import CompteAr.backend.entity.SavedDatesEntity;

/**
 * Tests unitaires pour la classe SavedDatesServiceImpl.
 */
@RunWith(MockitoJUnitRunner.class)
public class SavedDatesServiceImplTest {

    // On mock la dépendance SavedDatesRepository
    @Mock
    private SavedDatesRepository savedDatesRepository;

    // On injecte le mock SavedDatesRepository dans l'implémentation SavedDatesServiceImpl
    @InjectMocks
    private SavedDatesServiceImpl savedDatesService;

    @Test
    public void testFindAllCasNominal() {
        // given
        List<SavedDatesEntity> datesEntity = Arrays.asList(
                SavedDatesEntity.builder().id(1).userId(1).name("name1").date(new Date()).build(),
                SavedDatesEntity.builder().id(2).userId(1).name("name2").date(new Date()).build(),
                SavedDatesEntity.builder().id(3).userId(2).name("name3").date(new Date()).build(),
                SavedDatesEntity.builder().id(4).userId(2).name("name4").date(new Date()).build());

        // On mock le retour du repository lorsqu'on appelle la méthode findAll()
        when(savedDatesRepository.findAll()).thenReturn(datesEntity);

        // when
        List<SavedDatesResource> datesResource = savedDatesService.findAll();

        // then
        assertThat(datesResource).isNotNull().isNotEmpty().hasSize(4);
        assertThat(datesResource).element(0).isInstanceOfSatisfying(SavedDatesResource.class, dateResource -> {
            assertThat(dateResource).extracting("id", "userId", "name", "date")
                    .containsExactly(1, 1, "name1", dateResource.getDate());
            assertThat(dateResource.getDate()).isCloseTo(new Date(), 1000);
        });
        assertThat(datesResource).element(1).isInstanceOfSatisfying(SavedDatesResource.class, dateResource -> {
            assertThat(dateResource).extracting("id", "userId", "name", "date")
                    .containsExactly(2, 1, "name2", dateResource.getDate());
            assertThat(dateResource.getDate()).isCloseTo(new Date(), 1000);
        });
        assertThat(datesResource).element(2).isInstanceOfSatisfying(SavedDatesResource.class, dateResource -> {
            assertThat(dateResource).extracting("id", "userId", "name", "date")
                    .containsExactly(3, 2, "name3", dateResource.getDate());
            assertThat(dateResource.getDate()).isCloseTo(new Date(), 1000);
        });
        assertThat(datesResource).element(3).isInstanceOfSatisfying(SavedDatesResource.class, dateResource -> {
            assertThat(dateResource).extracting("id", "userId", "name", "date")
                    .containsExactly(4, 2, "name4", dateResource.getDate());
            assertThat(dateResource.getDate()).isCloseTo(new Date(), 1000);
        });
    }


    @Test
    public void testFindByUserIdCasNominal() {
        // given
        Integer userId = 1;
        Date now = new Date();
        List<SavedDatesEntity> savedDatesEntity = Arrays.asList(
                SavedDatesEntity.builder().id(1).userId(1).date(now)
                        .name("birthday").build(),
                SavedDatesEntity.builder().id(2).userId(1).date(now)
                        .name("anniversary").build());

        when(savedDatesRepository.findByUserId(userId)).thenReturn(savedDatesEntity);

        // when
        List<SavedDatesResource> savedDatesResource = savedDatesService.findByUserId(userId);

        // then
        assertThat(savedDatesResource).isNotNull().isNotEmpty().hasSize(2);
        assertThat(savedDatesResource).element(0).isInstanceOfSatisfying(SavedDatesResource.class,
                savedDates -> assertThat(savedDates).extracting("id", "userId", "date", "name").containsExactly(1, 1,
                        now, "birthday"));
        assertThat(savedDatesResource).element(1).isInstanceOfSatisfying(SavedDatesResource.class,
                savedDates -> assertThat(savedDates).extracting("id", "userId", "date", "name").containsExactly(2, 1,
                        now, "anniversary"));
    }

    

    @Test
    public void testSaveDateCasNominal() {
        // Given
        // On crée une SavedDatesResource pour simuler la requête de l'utilisateur
        SavedDatesResource savedDateResource = SavedDatesResource.builder()
                .userId(1)
                .date(new Date(122, 0, 1))
                .name("Nouvel an")
                .build();

        // On crée une SavedDatesEntity correspondante pour la comparer avec celle retournée par le service
        SavedDatesEntity savedDateEntity = SavedDatesEntity.builder()
                .id(1)
                .userId(1)
                .date(new Date(122, 0, 1))
                .name("Nouvel an")
                .build();

        // Le repository doit renvoyer la même entité avec l'ID auto-généré
        when(savedDatesRepository.save(any(SavedDatesEntity.class))).thenReturn(savedDateEntity);

        // When
        Optional<SavedDatesResource> createdSavedDateResource = savedDatesService.saveDate(savedDateResource);

        // Then
        // On vérifie que la ressource retournée par le service correspond à ce que l'utilisateur a envoyé
        assertThat(createdSavedDateResource).isPresent();
        assertThat(createdSavedDateResource.get().getId()).isEqualTo(1);
        assertThat(createdSavedDateResource.get().getUserId()).isEqualTo(savedDateResource.getUserId());
        assertThat(createdSavedDateResource.get().getDate()).isEqualTo(savedDateResource.getDate());
        assertThat(createdSavedDateResource.get().getName()).isEqualTo(savedDateResource.getName());
    }


    @Test
    public void testDeleteDateCasNominal() {
        // Given
        Integer userId = 1;
        Integer dateId = 1;

        // Le repository doit renvoyer une entité si l'utilisateur et l'ID existent
        SavedDatesEntity savedDateEntity = SavedDatesEntity.builder().id(dateId).userId(userId).date(new Date()).name("event").build();
        when(savedDatesRepository.findByUserIdAndId(userId, dateId)).thenReturn(Optional.of(savedDateEntity)).thenReturn(Optional.empty());

        // When
        boolean deleted = savedDatesService.deleteDate(userId, dateId);

        // Then
        assertThat(deleted).isTrue();
        verify(savedDatesRepository).deleteById(dateId);

        deleted = savedDatesService.deleteDate(userId, dateId);
        assertThat(deleted).isFalse();
        verify(savedDatesRepository, times(1)).deleteById(dateId);
    }

    @Test
    public void testDeleteDateNotFound() {
        // Given
        Integer userId = 1;
        Integer dateId = 1;

        // Le repository ne trouve pas de date avec cet ID et cet userId, donc il renvoie un Optional vide
        when(savedDatesRepository.findByUserIdAndId(userId, dateId)).thenReturn(Optional.empty());

        // When
        boolean deleted = savedDatesService.deleteDate(userId, dateId);

        // Then
        assertThat(deleted).isFalse();
        verify(savedDatesRepository, never()).deleteById(dateId);
    }

}

