package mk.ukim.finki.wp.lab.service.specifications;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SongSpecification {
    public static <T> Specification<T> filterAlbum(Class<T> clazz, String field, Long value) {
        if (value == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(fieldToPath(field,root), value));
    }

    public static <T, V extends Comparable<V>> Specification<T> filterDate(Class clazz, String field, V value) {
        if (value == null) {
            return null;
        }
        return (((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(fieldToPath(field, (Root<V>) root), value)));
    }

    private static <T> Path<T> fieldToPath(String field, Root<T> root) {
        String[] parts = field.split("\\.");
        Path<T> res = root;
        for (String p : parts) {
            res = res.get(p);
        }
        return res;
    }

}
