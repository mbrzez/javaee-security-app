package pl.brzezins.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface DtoMapper<T, V> {
    T convert(V object);

    // Default method implementation
    default List<T> convertList(List<V> objects) {
        return objects.stream().map(this::convert).collect(Collectors.toList());
    }
}
