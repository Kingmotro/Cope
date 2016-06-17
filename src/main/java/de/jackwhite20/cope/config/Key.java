/*
 * Copyright (c) 2016 "JackWhite20"
 *
 * This file is part of Cope.
 *
 * Cope is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jackwhite20.cope.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JackWhite20 on 18.06.2016.
 */
public class Key {

    /**
     * The name of the key.
     */
    private String name;

    /**
     * The value of the key.
     */
    private List<Value> values = new ArrayList<>();

    /**
     * Creates a new key with the given name.
     *
     * @param name The name of the key.
     */
    public Key(String name) {

        this.name = name;
    }

    /**
     * Adds a value to this key.
     *
     * @param value The value.
     */
    public void addValue(Value value) {

        values.add(value);
    }

    /**
     * Returns the name of the key.
     *
     * @return The name of the key.
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the value at the given index.
     *
     * @param index The index of the value.
     * @return The value at the index.
     */
    public Value getValue(int index) {

        return values.get(index);
    }

    /**
     * Returns the values as an unmodifiable list.
     *
     * @return The values as an unmodifiable list.
     */
    public List<Value> getValues() {

        return Collections.unmodifiableList(values);
    }
}
