package com.epam.prejap.tetris;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test(groups = "JarFile")
public class JarFileTest {

    private static final String jarfilePath = ".\\target\\tetris-0.1.jar";
    private static final String expectedMainClassName = Tetris.class.getCanonicalName();

    @Test(dataProvider = "manifestForCheckingJar")
    public void shallContainsSameMainClassName(Manifest manifest) {
        if (manifest != null) {
            //given
            Attributes mainAttributes = manifest.getMainAttributes();

            //when
            String actualJarMainClassName = mainAttributes.getValue("Main-Class");

            //then
            assertEquals(actualJarMainClassName, expectedMainClassName,
                    String.format("Manifest file shall contains %s as main class name, but did not",
                            expectedMainClassName));
        }
        assertNotNull(manifest, "Manifest file should not be null");
    }

    @DataProvider
    public static Object[][] manifestForCheckingJar() {
        JarFile jarFile = null;
        Manifest manifest = null;

        try {
            jarFile = new JarFile(jarfilePath);
        } catch (IOException e) {
            System.err.println("Jar file " + e.getMessage() + " doesn't exist");
        }

        if (jarFile != null) {
            try {
                manifest = jarFile.getManifest();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return new Object[][]{{manifest}};
    }

}
