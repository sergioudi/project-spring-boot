package com.github.sergioudi.infrastructure.video;

import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.video.VideoID;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;
import com.github.sergioudi.infrastructure.video.persistence.VideoJpaEntity;
import com.github.sergioudi.infrastructure.video.persistence.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.github.sergioudi.infrastructure.utils.SpecificationUtils.like;

@Component
public class VideoMySQLGateway implements VideoGateway {

    private final VideoRepository repository;

    public VideoMySQLGateway(final VideoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final String anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Video> findById(final VideoID anId) {
        return this.repository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    public Video update(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public Pagination<Video> findAll(final SearchQuery aQuery) {
        // Paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(VideoJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<VideoID> existsByIds(final Iterable<VideoID> videoIDs) {
        final var ids = StreamSupport.stream(videoIDs.spliterator(), false)
                .map(VideoID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(VideoID::from)
                .toList();
    }

    private Video save(final Video aVideo) {
        return this.repository.save(VideoJpaEntity.from(aVideo)).toAggregate();
    }

    private Specification<VideoJpaEntity> assembleSpecification(final String str) {
        final Specification<VideoJpaEntity> nameLike = like("name", str);
        final Specification<VideoJpaEntity> descriptionLike = like("description", str);
        return nameLike.or(descriptionLike);
    }
}
