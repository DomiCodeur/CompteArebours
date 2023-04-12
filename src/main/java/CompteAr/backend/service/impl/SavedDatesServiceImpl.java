package compteAr.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import compteAr.backend.repository.SavedDatesRepository;
import compteAr.backend.resources.SavedDatesResource;
import compteAr.backend.service.SavedDatesService;

/**
 * Implementation du service {@link SavedDatesService}.
 *
 */
@Service
public class SavedDatesServiceImpl implements SavedDatesService {
	
	@Autowired
    private SavedDatesRepository savedDatesRepository;

	@Override
	public List<SavedDatesResource> findAll() {
		return savedDatesRepository.findAll().stream()
				.map(savedDatesEntity -> SavedDatesResource.builder()
						.id(savedDatesEntity.getId())
						.userId(savedDatesEntity.getUserId())
						.date(savedDatesEntity.getDate())
						.name(savedDatesEntity.getName())
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<SavedDatesResource> findByUserId(Integer userId) {
		return savedDatesRepository.findByUserId(userId).stream()
				.map(savedDatesEntity -> SavedDatesResource.builder()
						.id(savedDatesEntity.getId())
						.userId(savedDatesEntity.getUserId())
						.date(savedDatesEntity.getDate())
						.name(savedDatesEntity.getName())
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public Optional<SavedDatesResource> saveDate(SavedDatesResource savedDates) {
		try {
			return Optional.ofNullable(savedDatesRepository.save(savedDates.toEntity()).toResource());
		} catch (Exception e) {
			return Optional.empty();
		}
		
	}

	@Override
	public boolean deleteDate(Integer dateId) {
		if(!savedDatesRepository.existsById(dateId)) {
			return false;
		}
		savedDatesRepository.deleteById(dateId);
		return true;
	}
	
	
}
