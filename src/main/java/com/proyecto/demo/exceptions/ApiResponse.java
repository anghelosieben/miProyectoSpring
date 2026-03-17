package com.proyecto.demo.exceptions;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;

    // 1. Constructor necesario para que los métodos estáticos funcionen
    public ApiResponse(boolean success, String message, T data, String path) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now(); // Se genera automáticamente al crear la respuesta
        this.path = path;
    }

    // --- Métodos Estáticos (Helper) ---
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>(true, message, data, path);
    }

    public static <T> ApiResponse<T> error(String message, String path) {
        return new ApiResponse<>(false, message, null, path);
    }

    // 2. GETTERS (Indispensables para que Spring genere el JSON)
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }

    // Setters (Opcionales, pero recomendados si no usas Records)
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setPath(String path) { this.path = path; }
}