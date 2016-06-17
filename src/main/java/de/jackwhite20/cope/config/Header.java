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

import java.util.*;

/**
 * Created by JackWhite20 on 18.06.2016.
 */
public class Header {

    /**
     * The name of the header.
     */
    private String name;

    /**
     * The keys of the header.
     */
    private Map<String, Key> keys = new HashMap<>();

    /**
     * Creates a new header with the given name.
     *
     * @param name The name of the header.
     */
    public Header(String name) {

        this.name = name;
    }

    /**
     * Adds a key to this header.
     *
     * @param key The key.
     */
    public void addKey(Key key) {

        keys.put(key.getName(), key);
    }

    /**
     * Returns whether the given key exists.
     *
     * @param key The key to check.
     * @return True or false whether the given check exists or not.
     */
    public boolean hasKey(String key) {

        return keys.containsKey(key);
    }

    /**
     * Returns the key with the given name.
     *
     * @param key The name of the key.
     * @return The key object.
     */
    public Key getKey(String key) {

        return keys.get(key);
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
     * Returns the keys as a list.
     *
     * @return The keys as a list.
     */
    public List<Key> getKeys() {

        return Collections.unmodifiableList(new ArrayList<>(keys.values()));
    }
}
