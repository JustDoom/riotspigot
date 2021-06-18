package de.dytanic.document;

/**
 * Created by Tareko on 20.12.2017.
 */
public interface IDocPropertyable {

    <E> IDocPropertyable setProperty(DocProperty<E> docProperty, E val);

    <E> E getProperty(DocProperty<E> docProperty);

    <E> IDocPropertyable removeProperty(DocProperty<E> docProperty);

    default <E> boolean hasProperty(DocProperty<E> docProperty)
    {
        return getProperty(docProperty) != null;
    }

}