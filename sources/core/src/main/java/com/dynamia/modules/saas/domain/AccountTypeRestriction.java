/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dynamia.modules.saas.domain;

import com.dynamia.tools.domain.SimpleEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author mario
 */
@Entity
@Table(name = "saas_restrictions")
public class AccountTypeRestriction extends SimpleEntity {

    @ManyToOne
    private AccountType tipoCuenta;

    private String descripcion;
    private String nombre;
    private String clave;
    private String valores;
    private boolean activa;

    public AccountType getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(AccountType tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValores() {
        return valores;
    }

    public void setValores(String valores) {
        this.valores = valores;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
