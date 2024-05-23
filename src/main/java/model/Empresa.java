package model;

public class Empresa {
    private int id_empresa;
    private String codigo_empresa;
    private String cif;
    private String nombre_empresa;
    private String direccion;
    private String cp;
    private String localidad;
    private String tipo_jornada;
    private String modalidad;
    private String mail;
    private String dni_responsable;
    private String nombre_responsable;
    private String apellidos_responsable;
    private String dni_tutor_laboral;
    private String nombre_tutor_laboral;
    private String apellidos_tutor_laboral;
    private String telefono_tutor_laboral;

    public Empresa() {
    }

    public Empresa(int id_empresa, String codigo_empresa, String cif, String direccion, String cp, String localidad, String tipo_jornada, String modalidad, String mail, String dni_responsable, String nombre_responsable, String apellidos_responsable, String dni_tutor_laboral, String nombre_tutor_laboral, String apellidos_tutor_laboral, String telefono_tutor_laboral) {
        this.id_empresa = id_empresa;
        this.codigo_empresa = codigo_empresa;
        this.cif = cif;
        this.direccion = direccion;
        this.cp = cp;
        this.localidad = localidad;
        this.tipo_jornada = tipo_jornada;
        this.modalidad = modalidad;
        this.mail = mail;
        this.dni_responsable = dni_responsable;
        this.nombre_responsable = nombre_responsable;
        this.apellidos_responsable = apellidos_responsable;
        this.dni_tutor_laboral = dni_tutor_laboral;
        this.nombre_tutor_laboral = nombre_tutor_laboral;
        this.apellidos_tutor_laboral = apellidos_tutor_laboral;
        this.telefono_tutor_laboral = telefono_tutor_laboral;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getCodigo_empresa() {
        return codigo_empresa;
    }

    public void setCodigo_empresa(String codigo_empresa) {
        this.codigo_empresa = codigo_empresa;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTipo_jornada() {
        return tipo_jornada;
    }

    public void setTipo_jornada(String tipo_jornada) {
        this.tipo_jornada = tipo_jornada;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni_responsable() {
        return dni_responsable;
    }

    public void setDni_responsable(String dni_responsable) {
        this.dni_responsable = dni_responsable;
    }

    public String getNombre_responsable() {
        return nombre_responsable;
    }

    public void setNombre_responsable(String nombre_responsable) {
        this.nombre_responsable = nombre_responsable;
    }

    public String getApellidos_responsable() {
        return apellidos_responsable;
    }

    public void setApellidos_responsable(String apellidos_responsable) {
        this.apellidos_responsable = apellidos_responsable;
    }

    public String getDni_tutor_laboral() {
        return dni_tutor_laboral;
    }

    public void setDni_tutor_laboral(String dni_tutor_laboral) {
        this.dni_tutor_laboral = dni_tutor_laboral;
    }

    public String getNombre_tutor_laboral() {
        return nombre_tutor_laboral;
    }

    public void setNombre_tutor_laboral(String nombre_tutor_laboral) {
        this.nombre_tutor_laboral = nombre_tutor_laboral;
    }

    public String getApellidos_tutor_laboral() {
        return apellidos_tutor_laboral;
    }

    public void setApellidos_tutor_laboral(String apellidos_tutor_laboral) {
        this.apellidos_tutor_laboral = apellidos_tutor_laboral;
    }

    public String getTelefono_tutor_laboral() {
        return telefono_tutor_laboral;
    }

    public void setTelefono_tutor_laboral(String telefono_tutor_laboral) {
        this.telefono_tutor_laboral = telefono_tutor_laboral;
    }


}
