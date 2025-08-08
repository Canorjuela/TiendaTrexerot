package com.trexerot.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    @Column(precision = 10, scale = 3)
    private BigDecimal precio;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    private CategoriaProducto categoria;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;

    private String imagenUrl;

    private boolean destacado;

    private boolean enOferta;

    @PositiveOrZero(message = "El descuento no puede ser negativo")
    @Column(precision = 5, scale = 2)
    private BigDecimal descuento; // % descuento

    @PositiveOrZero(message = "Las ventas no pueden ser negativas")
    private long ventasTotales = 0; // Para los más vendidos

    // Metodo calculado (no se guarda en BD)
    @Transient
    public BigDecimal getPrecioFinal() {
        if (enOferta && descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) {
            return precio.subtract(
                    precio.multiply(descuento).divide(BigDecimal.valueOf(100))
            );
        }
        return precio;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public CategoriaProducto getCategoria() { return categoria; }
    public void setCategoria(CategoriaProducto categoria) { this.categoria = categoria; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public boolean isDestacado() { return destacado; }
    public void setDestacado(boolean destacado) { this.destacado = destacado; }
    public boolean isEnOferta() { return enOferta; }
    public void setEnOferta(boolean enOferta) { this.enOferta = enOferta; }
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public Long getVentasTotales() { return ventasTotales; }
    public void setVentasTotales(Long ventasTotales) { this.ventasTotales = ventasTotales; }
}
