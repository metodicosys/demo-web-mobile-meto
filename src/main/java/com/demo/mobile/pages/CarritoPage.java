package com.demo.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    // Localizadores oficiales de Demoblaze
    private By btnMenuHamburguesa = By.cssSelector("button.navbar-toggler");
    private By linkCarrito = By.id("cartur");
    private By filasTablaCarrito = By.cssSelector("#tbodyid tr");
    private By titulosProductos = By.cssSelector("#tbodyid tr td:nth-child(2)");

    public CarritoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrirCarrito() {
        // 1. En pantalla móvil, abrir el menú hamburguesa si el enlace está oculto
        WebElement carrito = driver.findElement(linkCarrito);
        if (!carrito.isDisplayed()) {
            wait.until(ExpectedConditions.elementToBeClickable(btnMenuHamburguesa)).click();
        }

        // 2. Hacer clic en el enlace del Carrito
        wait.until(ExpectedConditions.elementToBeClickable(linkCarrito)).click();
    }

    // Devuelve la lista con los nombres de los productos en el carrito
    public List<String> obtenerTituloProductos() {
        // Esperar a que cargue al menos una fila en la tabla del carrito
        wait.until(ExpectedConditions.visibilityOfElementLocated(filasTablaCarrito));

        List<WebElement> celdas = driver.findElements(titulosProductos);
        return celdas.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
