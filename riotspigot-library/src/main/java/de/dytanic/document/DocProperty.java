package de.dytanic.document;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Tareko on 20.12.2017.
 */
@Getter
@AllArgsConstructor
public class DocProperty<E> {

    protected final BiConsumer<E, Document> appender;

    protected final Function<Document, E> resolver;

    protected final Consumer<Document> remover;

}