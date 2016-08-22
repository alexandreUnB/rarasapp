package com.rarasnet.rnp.shared.models;

/**
 * Created by Ronnyery Barbosa on 12/08/2015.
 */
public class Center {
//    String id;
    String name;
    String abbreviation;
    String address;
    String number;
    String complement;
    String postal_code;
    String neighborhood;
    String city;
    String uf;
//    String cep;
    String contact1;
    String contact2;
//    String ddd;
    String phone_number;
    String general_number;
    String extension;
    //    String email;
//    String site;
//    String latitude;
    String longitude;
    //    String cnes;
    String open24;


    public String getOpen24() {
        return open24;
    }

    public void setOpen24(String open24) {
        this.open24 = open24;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGeneral_number() {
        return general_number;
    }

    public void setGeneral_number(String general_number) {
        this.general_number = general_number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    // CODIGO LEGADO
    String id;
    String nome;
    String endereco;
    String cidade;
    String estado;
    String cep;
    String cnes;
    String tipo_unidade;
    String especialidades;
    String ddd;
    String email;
    String telefone1;
    String longetude;
    String latitude;

    String horario_atendimento_inicial;
    String horario_atendimento_final;
    String tipo = "center";
    String site;

    public Center() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_unidade() {
        return tipo_unidade;
    }

    public void setTipo_unidade(String tipo_unidade) {
        this.tipo_unidade = tipo_unidade;
    }


    public String getLongetude() {
        return longetude;
    }

    public void setLongetude(String longitude) {
        this.longetude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone() {
        return telefone1;
    }

    public void setTelefone(String telefone) {
        this.telefone1 = telefone;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public String getHorario_atendimento_inicial() {
        return horario_atendimento_inicial;
    }

    public void setHorario_atendimento_inicial(String horario_atendimento_inicial) {
        this.horario_atendimento_inicial = horario_atendimento_inicial;
    }

    public String getHorario_atendimento_final() {
        return horario_atendimento_final;
    }

    public void setHorario_atendimento_final(String horario_atendimento_final) {
        this.horario_atendimento_final = horario_atendimento_final;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
