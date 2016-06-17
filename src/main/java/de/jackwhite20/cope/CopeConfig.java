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

import de.jackwhite20.cope.config.Header;
import de.jackwhite20.cope.config.Key;
import de.jackwhite20.cope.config.Value;
import de.jackwhite20.cope.exception.CopeEmptyConfigException;
import de.jackwhite20.cope.exception.CopeException;
import de.jackwhite20.cope.exception.CopeInvalidConfigException;
import de.jackwhite20.cope.exception.CopeLoadConfigException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JackWhite20 on 18.06.2016.
 */
public class CopeConfig {

    /**
     * The config file line by line as a list.
     */
    private List<String> config = new ArrayList<>();

    /**
     * All headers from the config.
     */
    private Map<String, Header> headers = new HashMap<>();

    protected CopeConfig(File file) throws CopeException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    this.config.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new CopeLoadConfigException("could not load config file '" + file.getName() + "'");
        }

        if (this.config.size() == 0) {
            throw new CopeEmptyConfigException("config file " + file.getName() + " is empty");
        }

        parse();
    }

    /**
     * Parses the config lines.
     *
     * @throws CopeException If something went wrong during the parsing.
     */
    private void parse() throws CopeException {

        Header currentHeader = null;

        for (int i = 0; i < config.size(); i++) {
            String line = config.get(i);

            if (line.endsWith(":")) {
                if (currentHeader != null) {
                    headers.put(currentHeader.getName(), currentHeader);
                }

                currentHeader = new Header(line.substring(0, line.length() - 1));
            } else {
                String[] info = line.split(" ");
                if (info.length > 0) {
                    String key = info[0];
                    String[] options = new String[info.length - 1];
                    System.arraycopy(info, 1, options, 0, options.length);

                    Key keyData = new Key(key);
                    for (String option : options) {
                        keyData.addValue(new Value(option));
                    }

                    if (currentHeader != null) {
                        currentHeader.addKey(keyData);
                    } else {
                        throw new CopeInvalidConfigException("at least one header at the top is needed");
                    }
                }
            }

            // Add the last header
            if (i == config.size() - 1) {
                if (currentHeader != null) {
                    headers.put(currentHeader.getName(), currentHeader);
                }
            }
        }
    }

    /**
     * Returns whether the given header exists or not.
     *
     * @param header The header to check.
     * @return True or false whether the given header exists or not.
     */
    public boolean hasHeader(String header) {

        return headers.containsKey(header);
    }

    /**
     * Returns whether the given header and key exists or not.
     *
     * @param header The header to check.
     * @param key    The key to check.
     * @return True or false whether the given header and key exists or not.
     */
    public boolean hasHeaderAndKey(String header, String key) {

        return headers.containsKey(header) && headers.get(header).hasKey(key);
    }

    /**
     * Returns the header if it exists otherwise it returns null.
     *
     * @param header The header name.
     * @return The header object instance.
     */
    public Header getHeader(String header) {

        return headers.get(header);
    }
}
