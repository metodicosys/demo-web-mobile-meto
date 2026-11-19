package com.demo.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Localizadores del Login
    private By btnAbrirLogin = By.id("login2");
    private By inputUsuario = By.id("loginusername");
    private By inputPassword = By.id("loginpassword");
    private By btnConfirmarLogin = By.cssSelector("button[onclick='logIn()']");
    private By lblUsuarioLogueado = By.id("nameofuser");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void iniciarSesion(String usuario, String password) {
        // 1. Abrir el modal de Log in
        wait.until(ExpectedConditions.elementToBeClickable(btnAbrirLogin)).click();

        // 2. Escribir usuario y contraseña
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsuario)).sendKeys(usuario);
        driver.findElement(inputPassword).sendKeys(password);

        // 3. Hacer clic en "Iniciar sesión"
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirmarLogin)).click();

        // 4. Esperar el saludo o el mensaje de error que presenta el sitio en una alerta
        String resultado;
        try {
            resultado = wait.until(driver -> {
                try {
                    Alert alert = driver.switchTo().alert();
                    return "ALERTA:" + alert.getText();
                } catch (NoAlertPresentException ignored) {
                    return driver.findElement(lblUsuarioLogueado).isDisplayed() ? "OK" : null;
                }
            });
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    "No se confirmó el inicio de sesión. Verifica las credenciales y que Demoblaze haya terminado de cargar.",
                    e);
        }

        if (resultado.startsWith("ALERTA:")) {
            String mensaje = resultado.substring("ALERTA:".length());
            driver.switchTo().alert().accept();
            throw new IllegalStateException("Demoblaze rechazó el inicio de sesión: " + mensaje);
        }
    }
}
