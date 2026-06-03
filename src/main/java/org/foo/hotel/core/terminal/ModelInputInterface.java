package org.foo.hotel.core.terminal;

import java.util.Optional;

public interface ModelInputInterface <T> {
    public Optional<T> get(String abordSelection);
}
