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

package de.jackwhite20.cope;

import de.jackwhite20.cope.exception.CopeException;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Created by JackWhite20 on 17.06.2016.
 */
public final class Cope {

    private Cope() {

        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new cope config from the given file name or file path.
     *
     * @param file The file name or path.
     * @return A new cope config.
     * @throws CopeException If something went wrong during loading or parsing.
     */
    public static CopeConfig from(String file) throws CopeException {

        return from(new File(file));
    }

    /**
     * Creates a new cope config from the given path.
     *
     * @param path The path.
     * @return A new cope config.
     * @throws CopeException If something went wrong during loading or parsing.
     */
    public static CopeConfig from(Path path) throws CopeException {

        return from(path.toFile());
    }

    /**
     * Creates a new cope config from the given uri.
     *
     * Useful if you want to load a file in the resources.
     *
     * @param uri The uri.
     * @return A new cope config.
     * @throws CopeException If something went wrong during loading or parsing.
     */
    public static CopeConfig from(URI uri) throws CopeException {

        return from(new File(uri));
    }

    /**
     * Creates a new cope config from the given file.
     *
     * @param file The file.
     * @return A new cope config.
     * @throws CopeException If something went wrong during loading or parsing.
     */
    public static CopeConfig from(File file) throws CopeException {

        return new CopeConfig(file);
    }
}
