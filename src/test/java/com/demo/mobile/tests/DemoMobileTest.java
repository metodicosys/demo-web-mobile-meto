package com.demo.mobile.tests;

import com.demo.mobile.pages.CarritoPage;
import com.demo.mobile.pages.LoginPage;
import com.demo.mobile.pages.PortatilesPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoMobileTest {
    private AndroidDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");
        options.withBrowserName("Chrome");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.get("https://demoblaze.com");
    }

    @Test
    void debeIniciarSesionAgregarPortatilYValidarCarrito() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.iniciarSesion("metodio", "demo1");

        PortatilesPage portatilesPage = new PortatilesPage(driver);
        portatilesPage.abrirCategoriaPortatiles();
        portatilesPage.seleccionarPortatil("Sony vaio i5");
        portatilesPage.agregarAlCarrito();

        CarritoPage carritoPage = new CarritoPage(driver);
        carritoPage.abrirCarrito();
        assertTrue(carritoPage.obtenerTituloProductos().contains("Sony vaio i5"),
                "El carrito debe contener el portátil Sony vaio i5");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
