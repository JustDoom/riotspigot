package de.dytanic.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Tareko on 19.01.2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<T, TT> {

    private T first;

    private TT second;

}