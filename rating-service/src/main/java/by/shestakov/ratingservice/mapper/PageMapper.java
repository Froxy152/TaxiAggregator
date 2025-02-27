package by.shestakov.ratingservice.mapper;

import by.shestakov.ratingservice.dto.response.PageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PageMapper {
    default <T> PageResponse<T> toDto(Page<T> page) {
        Pageable pageable = page.getPageable();

        return PageResponse.<T>builder()
            .offset(pageable.getPageNumber())
            .limit(pageable.getPageSize())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .values(page.getContent())
            .build();
    }
}
