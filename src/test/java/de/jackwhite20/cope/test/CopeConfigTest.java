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

package de.jackwhite20.cope.test;

import de.jackwhite20.cope.Cope;
import de.jackwhite20.cope.CopeConfig;
import de.jackwhite20.cope.config.Header;
import de.jackwhite20.cope.config.Key;
import de.jackwhite20.cope.config.Value;
import de.jackwhite20.cope.exception.CopeEmptyConfigException;
import de.jackwhite20.cope.exception.CopeException;
import de.jackwhite20.cope.exception.CopeLoadConfigException;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

/**
 * Created by JackWhite20 on 18.06.2016.
 */
public class CopeConfigTest {

    @Test(expected = CopeLoadConfigException.class)
    public void testUnableToLoadConfig() throws Exception {

        Cope.from("stuff").build();
    }

    @Test
    public void testCopeConfig() throws URISyntaxException, CopeException {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example.cp").toURI()).toPath()).build();
        Header testHeader;
        Header serverHeader;
        Key keyKey;
        Key bindKey;

        assertTrue(copeConfig.hasHeader("test"));
        assertTrue(copeConfig.hasHeader("server"));

        testHeader = copeConfig.getHeader("test");
        serverHeader = copeConfig.getHeader("server");

        assertNotNull(testHeader);
        assertNotNull(serverHeader);

        //System.out.println(copeConfig.getHeader("test").getKeys().get(0).getValue(0).asString());
        assertTrue(copeConfig.hasHeaderAndKey("test", "key"));
        assertTrue(copeConfig.hasHeaderAndKey("server", "bind"));

        assertEquals(1, testHeader.getKeys().size());
        assertEquals(1, serverHeader.getKeys().size());

        keyKey = testHeader.getKey("key");
        bindKey = serverHeader.getKey("bind");

        assertNotNull(keyKey);
        assertNotNull(bindKey);

        assertEquals(2, keyKey.getValues().size());
        assertTrue(keyKey.hasValues());
        assertTrue(bindKey.hasValues());

        assertEquals("value1", keyKey.getValue(0).asString());
        assertEquals("value2", keyKey.getValue(1).asString());

        assertEquals("value1", keyKey.getValue(0).toString());

        assertEquals("0.0.0.0", bindKey.getValue(0).asString());
        assertEquals(80, bindKey.getValue(1).asInt());

        assertEquals(80D, bindKey.getValue(1).asDouble(), 0D);
        assertEquals(80, bindKey.getValue(1).asLong());
    }

    @Test
    public void testCopeBuilderNoHeader() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example-builder.cp").toURI()))
                .def(new Header("server"), new Key("bind"), new Value("0.0.0.0"), new Value("80"))
                .build();

        assertTrue(copeConfig.hasHeaderAndKey("server", "bind"));
        assertTrue(copeConfig.getHeader("server").getKey("bind").hasValues());
        assertEquals("0.0.0.0", copeConfig.getHeader("server").getKey("bind").getValue(0).asString());
        assertEquals(80, copeConfig.getHeader("server").getKey("bind").getValue(1).asInt());
    }

    @Test
    public void testCopeBuilderNoValues() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example-builder.cp").toURI()))
                .def(new Header("test"), new Key("key"), new Value("value1"), new Value("value2"))
                .build();

        assertTrue(copeConfig.hasHeaderAndKey("test", "key"));
        assertTrue(copeConfig.getHeader("test").getKey("key").hasValues());
        assertEquals("value1", copeConfig.getHeader("test").getKey("key").getValue(0).asString());
        assertEquals("value2", copeConfig.getHeader("test").getKey("key").getValue(1).asString());
    }

    @Test
    public void testCopeBuilderNoKey() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example-builder.cp").toURI()))
                .def(new Header("no-key"), new Key("key"), new Value("value1"), new Value("value2"))
                .build();

        assertTrue(copeConfig.hasHeader("no-key"));
        assertTrue(copeConfig.getHeader("no-key").getKey("key").hasValues());
        assertEquals("value1", copeConfig.getHeader("no-key").getKey("key").getValue(0).asString());
        assertEquals("value2", copeConfig.getHeader("no-key").getKey("key").getValue(1).asString());
    }

    @Test
    public void testCopeBuilderMultipleKeys() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example-builder.cp").toURI()))
                .def(new Header("no-key"), new Key("key"), new Value("value1"), new Value("value2"))
                .def(new Header("no-key"), new Key("key2"), new Value("value1"), new Value("value2"))
                .build();

        assertTrue(copeConfig.hasHeader("no-key"));
        assertTrue(copeConfig.getHeader("no-key").getKey("key").hasValues());
        assertTrue(copeConfig.getHeader("no-key").getKey("key2").hasValues());
        assertEquals("value1", copeConfig.getHeader("no-key").getKey("key").getValue(0).asString());
        assertEquals("value2", copeConfig.getHeader("no-key").getKey("key").getValue(1).asString());
        assertEquals("value1", copeConfig.getHeader("no-key").getKey("key2").getValue(0).asString());
        assertEquals("value2", copeConfig.getHeader("no-key").getKey("key2").getValue(1).asString());
    }

    @Test
    public void testCopeBuilderKeyNoValues() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example-builder.cp").toURI()))
                .def(new Header("no-key"), new Key("key"))
                .def(new Header("no-key"), new Key("key"), new Value("value1"), new Value("value2"))
                .build();

        assertTrue(copeConfig.hasHeader("no-key"));
        assertTrue(copeConfig.getHeader("no-key").getKey("key").hasValues());
        assertEquals("value1", copeConfig.getHeader("no-key").getKey("key").getValue(0).asString());
        assertEquals("value2", copeConfig.getHeader("no-key").getKey("key").getValue(1).asString());
    }

    @Test
    public void testWrongHeader() throws Exception {

        CopeConfig copeConfig = Cope.from(new File(ClassLoader.getSystemResource("example.cp").toURI())).build();
        assertNull(copeConfig.getHeader("random"));
    }

    @Test(expected = CopeEmptyConfigException.class)
    public void testEmptyConfig() throws Exception {

        Cope.from(ClassLoader.getSystemResource("example-empty.cp").toURI()).build();
    }

    @Test(expected = CopeException.class)
    public void testWrongConfigFile() throws Exception {

        Cope.from(ClassLoader.getSystemResource("example-wrong.cp").toURI()).build();
    }

    @Test(expected = InvocationTargetException.class)
    public void testNewInstance() throws Exception {

        Constructor<Cope> copeConstructor = Cope.class.getDeclaredConstructor();
        copeConstructor.setAccessible(true);
        copeConstructor.newInstance();
    }
}
