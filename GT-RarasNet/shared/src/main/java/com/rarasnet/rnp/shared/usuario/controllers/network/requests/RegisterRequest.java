package com.rarasnet.rnp.shared.usuario.controllers.network.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farina on 12/8/2015.
 */
public class RegisterRequest {

    @SerializedName("nm") private String nome;
    @SerializedName("sn") private String sobrenome;
    @SerializedName("em") private String email;
    @SerializedName("lg") private String login;
    @SerializedName("pw") private String senha;
    @SerializedName("tp") private String tipo;
    @SerializedName("sx") private String sexo;

    public RegisterRequest(String email, String login, String senha) {
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
