package com.demo.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PortatilesPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    private By linkCategoriaPortatiles = By.cssSelector("a[onclick=\"byCat('notebook')\"]");
    private By btnAgregarCarrito = By.cssSelector("a[onclick^='addToCart']");

    public PortatilesPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrirCategoriaPortatiles() {
        WebElement categoria = wait.until(ExpectedConditions.elementToBeClickable(linkCategoriaPortatiles));
        categoria.click();

        // Esperar a que la lista anterior (teléfonos) desaparezca del DOM antes de continuar
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[contains(., 'Samsung galaxy s6')]")));
    }

    public void seleccionarPortatil(String nombrePortatil) {
        By locatorProducto = By.xpath("//a[contains(., '" + nombrePortatil + "')]");

        // Reintenta automáticamente si el DOM se actualiza justo antes del clic
        wait.ignoring(StaleElementReferenceException.class).until(d -> {
            WebElement producto = d.findElement(locatorProducto);
            producto.click();
            return true;
        });
    }

    public void agregarAlCarrito() {
        // 1. Hacer clic en el botón verde "Añadir al carrito"
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnAgregarCarrito));
        boton.click();

        // 2. Esperar a que aparezca la ventana emergente (alerta de JavaScript)
        Alert alerta = wait.until(ExpectedConditions.alertIsPresent());

        // Opcional: Imprimir o validar el mensaje "Product added."
        System.out.println("Mensaje de alerta: " + alerta.getText());

        // 3. Hacer clic en "Aceptar" para cerrar la ventana emergente
        alerta.accept();
    }
}
